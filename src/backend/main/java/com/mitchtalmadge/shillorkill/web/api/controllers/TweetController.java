package com.mitchtalmadge.shillorkill.web.api.controllers;

import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import com.mitchtalmadge.shillorkill.domain.model.TweetDTO;
import com.mitchtalmadge.shillorkill.domain.model.VoteDTO;
import com.mitchtalmadge.shillorkill.domain.repository.TweetRepository;
import com.mitchtalmadge.shillorkill.domain.service.TweetService;
import com.mitchtalmadge.shillorkill.web.api.APIControllerAbstract;
import com.mitchtalmadge.shillorkill.web.api.APIResponse;
import com.mitchtalmadge.shillorkill.web.api.annotations.APIController;
import com.mitchtalmadge.shillorkill.web.api.validators.VoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@APIController
public class TweetController extends APIControllerAbstract {

    private final TweetService tweetService;
    private final TweetRepository tweetRepository;
    private final VoteValidator voteValidator;

    @Autowired
    public TweetController(TweetService tweetService,
                           TweetRepository tweetRepository,
                           VoteValidator voteValidator) {
        this.tweetService = tweetService;
        this.tweetRepository = tweetRepository;
        this.voteValidator = voteValidator;
    }

    /**
     * Determines which Tweet should be voted on and returns it.
     *
     * @return The most suitable Tweet for voting.
     */
    @RequestMapping(method = RequestMethod.GET, path = "tweet")
    public APIResponse getTweetForVoting() {
        // Find a tweet to vote for.
        Tweet tweet = tweetService.getTweetForVoting();

        // No tweet to vote for.
        if (tweet == null)
            return APIResponse.statusNotFound("There are no Tweets that can be voted on at the moment.");

        return APIResponse.statusOk(modelMapper.map(tweet, TweetDTO.class));
    }

    @RequestMapping(method = RequestMethod.POST, path = "tweet/{id}/votes")
    public APIResponse castVote(@PathVariable("id") long id, @RequestBody VoteDTO voteDTO) {
        // Check that the ID exists.
        if (!tweetRepository.exists(id))
            return APIResponse.statusNotFound("No Tweet exists with the given ID.");

        // Validate the VoteDTO
        voteValidator.validateVote(voteDTO);

        // Cast the vote.
        tweetService.castVote(id, voteDTO.shill, voteDTO.kill, voteDTO.wrong);

        return APIResponse.statusNoContent();
    }

}
