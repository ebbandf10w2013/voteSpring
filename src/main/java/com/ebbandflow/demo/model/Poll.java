package com.ebbandflow.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "polls")
public class Poll {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    
    @JsonIgnore
    @OneToMany(mappedBy = "poll", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY ,orphanRemoval = true)
    private List<Vote> votes;
    
    @Column(nullable = false)
    private Long voteCount = 0L;  // 累积票数，初始为0

	@Override
	public String toString() {
		return "Poll [id=" + id + ", title=" + title + ", voteCount=" + voteCount + "]";
	}

	

	

    
}
