package com.mitchtalmadge.shillorkill.domain.model;

/**
 * A vote response from the client.
 * Only one type of vote may be set to true.
 */
public class VoteDTO {

    /**
     * If this vote is shill.
     */
    public boolean shill;

    /**
     * If this vote is neutral.
     */
    public boolean neutral;

    /**
     * If this vote is kill.
     */
    public boolean kill;

    /**
     * If this vote is to mark the tweet as unrelated to the coin.
     */
    public boolean unrelated;

}
