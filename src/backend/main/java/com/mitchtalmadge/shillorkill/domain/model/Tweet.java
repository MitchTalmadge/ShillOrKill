package com.mitchtalmadge.shillorkill.domain.model;

import twitter4j.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Represents a trimmed-down Twitter tweet, including the number of recorded shill/kill votes.
 */
@Entity
public class Tweet implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private long statusId;

    private Date createdAt;

    private String text;

    private String searchQuery;

    private String authorName;

    private String authorScreenName;

    private String authorImageUrl;

    private int shillVotes;

    private int neutralVotes;

    private int killVotes;

    private int unrelatedVotes;

    /**
     * Required 0-arg constructor for Spring Framework.
     */
    private Tweet() {

    }

    /**
     * Constructs a Tweet entity from the given status.
     *
     * @param status The status to extract data from.
     */
    public Tweet(Status status, String searchQuery) {
        this(status.getId(), status.getCreatedAt(), status.getText(), status.getUser().getName(), status.getUser().getScreenName(), status.getUser().getProfileImageURLHttps(), searchQuery);
    }

    /**
     * Constructs a Tweet entity from the given data.
     * Alternatively, use {@link Tweet#Tweet(twitter4j.Status, String)} to do this automatically given a Status.
     *
     * @param statusId       The ID of the Status associated with this Tweet.
     * @param createdAt      When the Status was created on Twitter.
     * @param text           The text of the Status.
     * @param authorName     The name of the author of the Status.
     * @param authorImageUrl The URL (HTTPS) to the author's profile picture.
     */
    public Tweet(long statusId, Date createdAt, String text, String authorName, String authorScreenName, String authorImageUrl, String searchQuery) {
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.text = text;
        this.searchQuery = searchQuery;
        this.authorName = authorName;
        this.authorScreenName = authorScreenName;
        this.authorImageUrl = authorImageUrl;
    }

    /**
     * @return The unique ID of this entity.
     */
    public long getId() {
        return id;
    }

    /**
     * @return Twitter's own ID for the status associated with this tweet.
     */
    public long getStatusId() {
        return statusId;
    }

    /**
     * @return When the status associated with this tweet was created on Twitter.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return The full text of this tweet, including URLs.
     */
    public String getText() {
        return text;
    }

    /**
     * @return The search query used when finding the tweet.
     */
    public String getSearchQuery() {
        return searchQuery;
    }

    /**
     * @return The display name of the author of this tweet.
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @return The screen name of the author of this tweet (@someone).
     */
    public String getAuthorScreenName() {
        return authorScreenName;
    }

    /**
     * @return The HTTPS URL to the author's profile picture.
     */
    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    /**
     * @return The number of recorded shill votes for this tweet.
     */
    public int getShillVotes() {
        return shillVotes;
    }

    /**
     * @return The number of recorded neutral votes for this tweet.
     */
    public int getNeutralVotes() {
        return neutralVotes;
    }

    /**
     * @return The number of recorded kill votes for this tweet.
     */
    public int getKillVotes() {
        return killVotes;
    }

    /**
     * @return The number of recorded times that the tweet was voted as unrelated to the coin.
     */
    public int getUnrelatedVotes() {
        return unrelatedVotes;
    }

    /**
     * Adds a vote to the shill counter.
     */
    public void voteShill() {
        shillVotes++;
    }

     /**
     * Adds a vote to the neutral counter.
     */
    public void voteNeutral() {
        neutralVotes++;
    }


    /**
     * Adds a vote to the kill counter.
     */
    public void voteKill() {
        killVotes++;
    }

    /**
     * Adds a vote to the unrelated counter.
     */
    public void voteUnrelated() {
        unrelatedVotes++;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", statusId=" + statusId +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", searchQuery='" + searchQuery + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorScreenName='" + authorScreenName + '\'' +
                ", shillVotes=" + shillVotes +
                ", neutralVotes =" + neutralVotes +
                ", killVotes=" + killVotes +
                ", unrelatedVotes=" + unrelatedVotes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (statusId != tweet.statusId) return false;
        if (shillVotes != tweet.shillVotes) return false;
        if (neutralVotes != tweet.neutralVotes) return false;
        if (killVotes != tweet.killVotes) return false;
        if (unrelatedVotes != tweet.unrelatedVotes) return false;
        if (createdAt != null ? !createdAt.equals(tweet.createdAt) : tweet.createdAt != null) return false;
        if (text != null ? !text.equals(tweet.text) : tweet.text != null) return false;
        if (searchQuery != null ? !searchQuery.equals(tweet.searchQuery) : tweet.searchQuery != null) return false;
        if (authorName != null ? !authorName.equals(tweet.authorName) : tweet.authorName != null) return false;
        if (authorScreenName != null ? !authorScreenName.equals(tweet.authorScreenName) : tweet.authorScreenName != null)
            return false;
        return authorImageUrl != null ? authorImageUrl.equals(tweet.authorImageUrl) : tweet.authorImageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (statusId ^ (statusId >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (searchQuery != null ? searchQuery.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorScreenName != null ? authorScreenName.hashCode() : 0);
        result = 31 * result + (authorImageUrl != null ? authorImageUrl.hashCode() : 0);
        result = 31 * result + shillVotes;
        result = 31 * result + neutralVotes;
        result = 31 * result + killVotes;
        result = 31 * result + unrelatedVotes;
        return result;
    }
}
