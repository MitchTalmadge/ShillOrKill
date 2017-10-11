package com.mitchtalmadge.shillorkill.web.api.validators;

import com.mitchtalmadge.shillorkill.domain.model.VoteDTO;
import com.mitchtalmadge.shillorkill.web.api.APIResponse;
import org.springframework.stereotype.Component;

@Component
public class VoteValidator extends APIRequestValidator {

    /**
     * Ensures that a vote is parsable.
     *
     * @param voteDTO The vote from the client.
     */
    public void validateVote(VoteDTO voteDTO) {
        // Ensures one option is selected, no more and no less.
        if (!(voteDTO.shill ^ voteDTO.neutral ^ voteDTO.kill ^ voteDTO.unrelated))
            throw new ValidationException(APIResponse.statusBadRequestNotParsable("You must cast a vote for exactly one option."));
    }

}
