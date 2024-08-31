package com.ebbandflow.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.ebbandflow.demo.model.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {
	@Procedure(name = "InsertPoll")
    Long insertPoll(@Param("Title") String title, @Param("VoteCount") Long voteCount);
}
