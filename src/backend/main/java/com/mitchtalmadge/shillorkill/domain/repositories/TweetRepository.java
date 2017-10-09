package com.mitchtalmadge.shillorkill.domain.repositories;

import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Tweet findByStatusId(long statusId);

}
