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

    private String authorName;

    private String authorImageUrl;

    private int shills;

    private int kills;

    private int wrongs;

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
    public Tweet(Status status) {
        this(status.getId(), status.getCreatedAt(), status.getText(), status.getUser().getName(), status.getUser().getProfileImageURLHttps());
    }

    /**
     * Constructs a Tweet entity from the given data.
     * Alternatively, use {@link Tweet#Tweet(twitter4j.Status)} to do this automatically given a Status.
     *
     * @param statusId       The ID of the Status associated with this Tweet.
     * @param createdAt      When the Status was created on Twitter.
     * @param text           The text of the Status.
     * @param authorName     The name of the author of the Status.
     * @param authorImageUrl The URL (HTTPS) to the author's profile picture.
     */
    public Tweet(long statusId, Date createdAt, String text, String authorName, String authorImageUrl) {
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.text = text;
        this.authorName = authorName;
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
     * @return The display name of the author of this tweet.
     */
    public String getAuthorName() {
        return authorName;
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
    public int getShills() {
        return shills;
    }

    /**
     * @return The number of recorded kill votes for this tweet.
     */
    public int getKills() {
        return kills;
    }

    /**
     * @return The number of recorded times that the tweet was voted as being about the wrong coin.
     */
    public int getWrongs() {
        return wrongs;
    }

    /**
     * Adds a vote to the shill counter.
     */
    public void voteShill() {
        shills++;
    }

    /**
     * Adds a vote to the kill counter.
     */
    public void voteKill() {
        kills++;
    }

    /**
     * Adds a vote to the wrong coin counter.
     */
    public void voteWrong() {
        wrongs++;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "text='" + text + '\'' +
                ", authorName='" + authorName + '\'' +
                ", shills=" + shills +
                ", kills=" + kills +
                ", wrongs=" + wrongs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (statusId != tweet.statusId) return false;
        if (shills != tweet.shills) return false;
        if (kills != tweet.kills) return false;
        if (wrongs != tweet.wrongs) return false;
        if (createdAt != null ? !createdAt.equals(tweet.createdAt) : tweet.createdAt != null) return false;
        if (text != null ? !text.equals(tweet.text) : tweet.text != null) return false;
        if (authorName != null ? !authorName.equals(tweet.authorName) : tweet.authorName != null) return false;
        return authorImageUrl != null ? authorImageUrl.equals(tweet.authorImageUrl) : tweet.authorImageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (statusId ^ (statusId >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorImageUrl != null ? authorImageUrl.hashCode() : 0);
        result = 31 * result + shills;
        result = 31 * result + kills;
        result = 31 * result + wrongs;
        return result;
    }
}
