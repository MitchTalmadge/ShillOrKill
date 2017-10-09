package com.mitchtalmadge.shillorkill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterException;

@Service
public class TweetFinderService {

    private final LogService logService;
    private final TwitterService twitterService;

    @Autowired
    public TweetFinderService(LogService logService,
                              TwitterService twitterService) {
        this.logService = logService;
        this.twitterService = twitterService;
    }

    /**
     * Finds Tweets relating to coins that are suitable for rating.
     */
    @Scheduled(fixedRate = 60000)
    @Async
    public void findTweets() {
        logService.logDebug(getClass(), "Finding Tweets...");

        try {
            QueryResult venResults = twitterService.getTwitter().search(new Query("$VEN"));
            venResults.getTweets().forEach(tweet -> {
                logService.logInfo(getClass(), tweet.getUser().getName() + ": " + tweet.getText().replaceAll("\n", ""));
            });
        } catch (TwitterException e) {
            logService.logException(getClass(), e, "Could not search for tweets.");
        }
    }

}
