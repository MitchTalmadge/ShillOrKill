package com.mitchtalmadge.shillorkill.web.api.controllers;

import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import com.mitchtalmadge.shillorkill.domain.repository.TweetRepository;
import com.mitchtalmadge.shillorkill.web.api.APITestAbstract;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TweetControllerTest extends APITestAbstract {

    @Autowired
    private TweetRepository tweetRepository;

    /**
     * Makes sure that when there are no tweets in the database, a 404 is returned.
     */
    @Test
    public void TestGetTweetToVoteForWhenEmpty() throws Exception {
        mockMvc.perform(get("/api/tweet").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Makes sure that the selected tweet to vote for is the right one.
     */
    @Test
    public void TestGetTweetToVoteFor() throws Exception {

        Tweet correctTweet = new Tweet(0, new Date(), "correct", "", "", "", "");
        correctTweet.voteShill();

        Tweet incorrectTweet1 = new Tweet(1, new Date(), "incorrect1", "", "", "", "");
        incorrectTweet1.voteWrong();
        incorrectTweet1.voteWrong();

        Tweet incorrectTweet2 = new Tweet(1, new Date(), "incorrect2", "", "", "", "");
        incorrectTweet2.voteShill();
        incorrectTweet2.voteKill();
        incorrectTweet2.voteWrong();

        tweetRepository.save(correctTweet);
        tweetRepository.save(incorrectTweet1);
        tweetRepository.save(incorrectTweet2);

        mockMvc.perform(get("/api/tweet").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\": {\"text\": \"correct\"}}", false));
    }

    /**
     * Tests that a shill vote can be cast correctly.
     */
    @Test
    public void TestCastVoteShill() throws Exception {
        Tweet tweet = new Tweet(0, new Date(), "", "", "", "", "");
        tweet = tweetRepository.save(tweet);

        mockMvc.perform(
                post("/api/tweet/" + tweet.getId() + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shill\": true}"))
                .andDo(print())
                .andExpect(status().isOk());

        tweet = tweetRepository.findOne(tweet.getId());

        Assert.assertEquals(1, tweet.getShills());
        Assert.assertEquals(0, tweet.getKills());
        Assert.assertEquals(0, tweet.getWrongs());
    }

    /**
     * Tests that a kill vote can be cast correctly.
     */
    @Test
    public void TestCastVoteKill() throws Exception {
        Tweet tweet = new Tweet(0, new Date(), "", "", "", "", "");
        tweet = tweetRepository.save(tweet);

        mockMvc.perform(
                post("/api/tweet/" + tweet.getId() + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"kill\": true}"))
                .andDo(print())
                .andExpect(status().isOk());

        tweet = tweetRepository.findOne(tweet.getId());

        Assert.assertEquals(0, tweet.getShills());
        Assert.assertEquals(1, tweet.getKills());
        Assert.assertEquals(0, tweet.getWrongs());
    }

    /**
     * Tests that a wrong coin vote can be cast correctly.
     */
    @Test
    public void TestCastVoteWrongCoin() throws Exception {
        Tweet tweet = new Tweet(0, new Date(), "", "", "", "", "");
        tweet = tweetRepository.save(tweet);

        mockMvc.perform(
                post("/api/tweet/" + tweet.getId() + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wrong\": true}"))
                .andDo(print())
                .andExpect(status().isOk());

        tweet = tweetRepository.findOne(tweet.getId());

        Assert.assertEquals(0, tweet.getShills());
        Assert.assertEquals(0, tweet.getKills());
        Assert.assertEquals(1, tweet.getWrongs());
    }

    /**
     * Tests that a vote for multiple selections is not allowed.
     */
    @Test
    public void TestCastVoteMultipleSelections() throws Exception {
        Tweet tweet = new Tweet(0, new Date(), "", "", "", "", "");
        tweet = tweetRepository.save(tweet);

        mockMvc.perform(
                post("/api/tweet/" + tweet.getId() + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shill\": true, \"wrong\": true}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        tweet = tweetRepository.findOne(tweet.getId());

        Assert.assertEquals(0, tweet.getShills());
        Assert.assertEquals(0, tweet.getKills());
        Assert.assertEquals(0, tweet.getWrongs());
    }

}