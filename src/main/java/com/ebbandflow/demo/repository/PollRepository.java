package com.ebbandflow.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebbandflow.demo.model.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {

}
