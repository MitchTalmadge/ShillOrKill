package com.mitchtalmadge.shillorkill.service;

import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import com.mitchtalmadge.shillorkill.domain.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

@Service
public class TweetDiscoveryService {

    private final LogService logService;
    private final TwitterService twitterService;
    private final TweetRepository tweetRepository;

    @Autowired
    public TweetDiscoveryService(LogService logService,
                                 TwitterService twitterService,
                                 TweetRepository tweetRepository) {
        this.logService = logService;
        this.twitterService = twitterService;
        this.tweetRepository = tweetRepository;
    }

    /**
     * Searches for Tweets (statuses) relating to coins that are suitable for rating.
     */
    @Scheduled(fixedRate = 60000)
    @Async
    public void discoverTweets() {
        logService.logDebug(getClass(), "Searching for new Tweets...");

        try {

            QueryResult venResults = twitterService.getTwitter().search(new Query("$VEN").count(100).lang("en"));
            venResults.getTweets().forEach(status -> {

                // Consolidate re-tweets to prevent duplicates.
                if (status.isRetweet())
                    status = status.getRetweetedStatus();

                addStatusToRepository(status);
            });

        } catch (TwitterException e) {
            logService.logException(getClass(), e, "Unable to search for tweets");
        }
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
        Tweet tweet = new Tweet(status);
        tweetRepository.save(tweet);
        logService.logDebug(getClass(), "New Tweet Added: " + tweet);
    }

}
