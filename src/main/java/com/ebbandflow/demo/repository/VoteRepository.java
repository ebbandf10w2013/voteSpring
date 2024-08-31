package com.ebbandflow.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.ebbandflow.demo.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	@Procedure(name = "InsertVote")
    Long insertVote(@Param("PollId") Long pollId, @Param("Voter") String voter);
}
