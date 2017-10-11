package com.mitchtalmadge.shillorkill.domain.service;

import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import com.mitchtalmadge.shillorkill.domain.model.VoteDTO;
import com.mitchtalmadge.shillorkill.domain.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    /**
     * Finds a Tweet that is suitable for voting upon.
     *
     * @return The Tweet that should be voted on, or null if no Tweets exist in the database.
     */
    public Tweet getTweetForVoting() {
        List<Object[]> results = tweetRepository.findAllTweetsUnderFiveVotes();
        if (results.size() == 0)
            return null;

        return (Tweet) results.get(0)[0];
    }

    /**
     * Casts a vote for the given Tweet.
     *
     * @param tweetId The ID of the Tweet entity to vote for.
     * @param voteDTO The vote.
     */
    public void castVote(long tweetId, VoteDTO voteDTO) {
        // Find tweet.
        Tweet tweet = tweetRepository.findOne(tweetId);

        // Update tweet.
        if (voteDTO.shill)
            tweet.voteShill();
        else if (voteDTO.neutral)
            tweet.voteNeutral();
        else if (voteDTO.kill)
            tweet.voteKill();
        else if (voteDTO.unrelated)
            tweet.voteUnrelated();

        // Save tweet.
        tweetRepository.save(tweet);
    }
}
