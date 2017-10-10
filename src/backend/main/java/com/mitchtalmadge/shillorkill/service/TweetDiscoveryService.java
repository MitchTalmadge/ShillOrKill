package com.mitchtalmadge.shillorkill.service;

import com.mitchtalmadge.shillorkill.domain.model.CMCTickerDTO;
import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import com.mitchtalmadge.shillorkill.domain.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TweetDiscoveryService {

    private final LogService logService;
    private final TwitterService twitterService;
    private final TweetRepository tweetRepository;
    private final CoinMarketCapService coinMarketCapService;
    private final SpringProfileService springProfileService;

    /**
     * Matches coin tags, like "$BTC" inside a string.
     * Used for finding tags in status texts.
     */
    private static final Pattern COIN_TAG_PATTERN = Pattern.compile("\\$[a-zA-Z]+");

    /**
     * RNG used for picking a coin when discovering tweets.
     */
    private final Random random = new Random();

    @Autowired
    public TweetDiscoveryService(LogService logService,
                                 TwitterService twitterService,
                                 TweetRepository tweetRepository,
                                 CoinMarketCapService coinMarketCapService,
                                 SpringProfileService springProfileService) {
        this.logService = logService;
        this.twitterService = twitterService;
        this.tweetRepository = tweetRepository;
        this.coinMarketCapService = coinMarketCapService;
        this.springProfileService = springProfileService;
    }

    /**
     * Searches for Tweets (statuses) relating to a random coin from CoinMarketCap every minute.
     */
    @Scheduled(fixedRate = 60_000, initialDelay = 30_000)
    @Async
    protected void discoverTweets() {
        // Cap off the number of saved tweets to 8,000
        if (tweetRepository.count() >= 8_000)
            return;

        // Disable discovery during unit testing.
        if (springProfileService.isProfileActive(SpringProfileService.Profile.TESTING))
            return;

        // Get the tickers from Coin Market Cap.
        CMCTickerDTO[] tickers = coinMarketCapService.getTickers();
        if (tickers.length == 0)
            return;

        // Select a random ticker and form a search query.
        // Tickers are picked on an absolute normal curve so that higher ranked tickers appear more often.
        double multiplier = Math.abs(random.nextGaussian() * 0.33);
        CMCTickerDTO ticker = tickers[(int) (multiplier >= 1 ? tickers.length - 1 : multiplier * tickers.length)];
        String searchQuery = "$" + ticker.symbol;

        logService.logDebug(getClass(), "Searching for tweets about " + searchQuery);

        try {
            QueryResult results = twitterService.getTwitter().search(new Query(searchQuery).count(10).lang("en"));
            results.getTweets().forEach(status -> {

                // Consolidate re-tweets to prevent duplicates.
                if (status.isRetweet())
                    status = status.getRetweetedStatus();

                // Attempt to prune out unrelated tweets.
                if (!isFirstCoinMentioned(status.getText(), searchQuery))
                    return;

                // Add the status.
                addStatusToRepository(status, searchQuery);
            });

        } catch (TwitterException e) {
            logService.logException(getClass(), e, "Unable to search for tweets");
        }
    }

    /**
     * Determines if the provided coin (like "$BTC") is the first one mentioned in the text.
     *
     * @param text The text to check.
     * @param coin The coin to look for. "$BTC", "$LTC", etc.
     * @return True if the first coin mentioned in the status matches the provided coin.
     */
    private static boolean isFirstCoinMentioned(String text, String coin) {
        Matcher matcher = COIN_TAG_PATTERN.matcher(text);

        // Check that any coins were found.
        if (!matcher.find())
            return false;

        // Check the first group against the coin.
        return matcher.group().equalsIgnoreCase(coin);
    }

    /**
     * Adds a status to the Tweet repository if it is not already added.
     *
     * @param status      The status to add.
     * @param searchQuery The query used when searching for the provided Status.
     */
    private void addStatusToRepository(Status status, String searchQuery) {
        // Make sure the status is not already in the database.
        if (tweetRepository.findByStatusId(status.getId()) != null) {
            return;
        }

        // Add the new status.
        Tweet tweet = new Tweet(status, searchQuery);
        tweetRepository.save(tweet);
        logService.logInfo(getClass(), "New Tweet Added: " + tweet);
    }

}
