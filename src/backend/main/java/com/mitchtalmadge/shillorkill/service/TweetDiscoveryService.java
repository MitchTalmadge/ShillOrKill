package com.mitchtalmadge.shillorkill.service;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TweetDiscoveryService {

    private final LogService logService;
    private final TwitterService twitterService;
    private final TweetRepository tweetRepository;
    private final SpringProfileService springProfileService;

    /**
     * Matches coin tags, like "$BTC" inside a string.
     * Used for finding tags in status texts.
     */
    private static final Pattern COIN_TAG_PATTERN = Pattern.compile("\\$[a-zA-Z]+");

    @Autowired
    public TweetDiscoveryService(LogService logService,
                                 TwitterService twitterService,
                                 TweetRepository tweetRepository,
                                 SpringProfileService springProfileService) {
        this.logService = logService;
        this.twitterService = twitterService;
        this.tweetRepository = tweetRepository;
        this.springProfileService = springProfileService;
    }

    /**
     * Searches for Tweets (statuses) relating to coins that are suitable for rating.
     */
    @Scheduled(fixedRate = 60000)
    @Async
    public void discoverTweets() {
        // Disable discovery during unit testing.
        if (springProfileService.isProfileActive(SpringProfileService.Profile.TESTING))
            return;

        logService.logDebug(getClass(), "Searching for new Tweets...");

        try {

            QueryResult venResults = twitterService.getTwitter().search(new Query("$VEN").count(100).lang("en"));
            venResults.getTweets().forEach(status -> {

                // Consolidate re-tweets to prevent duplicates.
                if (status.isRetweet())
                    status = status.getRetweetedStatus();

                // Attempt to prune out wrong coin tweets.
                if (!isFirstCoinMentioned(status.getText(), "$VEN"))
                    return;

                addStatusToRepository(status);
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
     * @param status The status to add.
     */
    private void addStatusToRepository(Status status) {
        // Make sure the status is not already in the database.
        if (tweetRepository.findByStatusId(status.getId()) != null) {
            return;
        }

        // Add the new status.
        Tweet tweet = new Tweet(status, "$VEN");
        tweetRepository.save(tweet);
        logService.logInfo(getClass(), "New Tweet Added: " + tweet);
    }

}
