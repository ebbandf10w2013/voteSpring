package com.ebbandflow.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebbandflow.demo.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
