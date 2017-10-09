package com.mitchtalmadge.shillorkill.domain.model;

/**
 * A vote response from the client.
 * Only one of shill/kill/wrong may be set to true.
 */
public class VoteDTO {

    /**
     * If this vote is for a shill.
     */
    public boolean shill;

    /**
     * If this vote is for a kill.
     */
    public boolean kill;

    /**
     * If this vote is for a wrong coin.
     */
    public boolean wrong;

}
