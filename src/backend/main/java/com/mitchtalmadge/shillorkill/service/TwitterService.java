package com.mitchtalmadge.shillorkill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.PostConstruct;

@Service
public class TwitterService {

    private static final String CONSUMER_KEY = System.getenv("TWITTER_CONSUMER_KEY");
    private static final String CONSUMER_SECRET = System.getenv("TWITTER_CONSUMER_SECRET");
    private static final String ACCESS_TOKEN = System.getenv("TWITTER_ACCESS_TOKEN");
    private static final String ACCESS_TOKEN_SECRET = System.getenv("TWITTER_ACCESS_TOKEN_SECRET");

    private final SpringProfileService springProfileService;

    /**
     * A pre-configured Twitter API instance.
     */
    private Twitter twitter;

    @Autowired
    public TwitterService(SpringProfileService springProfileService) {
        this.springProfileService = springProfileService;
    }

    @PostConstruct
    private void init() {
        // Create Twitter instance with configuration.
        TwitterFactory twitterFactory = new TwitterFactory(
                new ConfigurationBuilder()
                        .setDebugEnabled(springProfileService.isProfileActive(SpringProfileService.Profile.DEV))
                        .setOAuthConsumerKey(CONSUMER_KEY)
                        .setOAuthConsumerSecret(CONSUMER_SECRET)
                        .setOAuthAccessToken(ACCESS_TOKEN)
                        .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET)
                        .setTweetModeExtended(true)
                        .build()
        );

        // Store Twitter instance.
        twitter = twitterFactory.getInstance();
    }

    /**
     * @return The Twitter API instance for this application.
     */
    public Twitter getTwitter() {
        return twitter;
    }
}
