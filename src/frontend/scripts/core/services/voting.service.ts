import {Injectable} from "@angular/core";
import {APIService} from "./api.service";
import {SKTweet} from "../../model/tweet.model";
import {SKVote} from "../../model/vote.model";

@Injectable()
export class VotingService {

    constructor(private apiService: APIService) {
    }

    /**
     * Gets a Tweet to vote upon.
     * @returns {Promise<SKTweet>} The Tweet to vote upon. May be 404 if there is nothing to vote on.
     */
    public getTweetForVoting(): Promise<SKTweet> {
        return this.apiService.get("/tweet");
    }

    /**
     * Casts a vote for a given Tweet.
     * @param {SKVote} vote The vote to cast. Should contain exactly one true value.
     * @param {number} tweetId The ID of the Tweet to vote on.
     * @returns {Promise<void>} No content if the vote was successful.
     */
    public castVote(vote: SKVote, tweetId: number): Promise<void> {
        return this.apiService.post(`/tweet/${tweetId}/votes`, vote);
    }

}