package com.ebbandflow.demo.test;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ebbandflow.demo.model.Poll;
import com.ebbandflow.demo.model.Vote;
import com.ebbandflow.demo.service.VoteService;

@SpringBootTest
public class Tests {
	@Autowired
	private VoteService vsi;
	
//	@Test
	public void testCreatePoll() {
		JSONObject json = new JSONObject()
				.put("title", "滑鼠");

		Poll poll =vsi.createPoll(json);
		System.out.println("poll="+poll);
	}
	
//	@Test
	public void testUpdatePoll() {
		JSONObject json = new JSONObject()
				.put("title", "電腦1")
				.put("pollId", 1);

		Poll poll =vsi.updatePoll(json);
		System.out.println("poll="+poll);
	}
	
//	@Test
	public void testDeletePoll() {
		
		Long a =1L;
		boolean result=vsi.deletePollById(a);
		System.out.println("結果="+result);
	}
//	@Test
	public void testFindAllPoll() {
		JSONObject json=new JSONObject();	
		
		List<Poll> results =vsi.findAllPoll(json);
		for(Poll result :results) {
			System.out.println("結果="+result);
		}
				
				
	}
//	@Test
	public void testVote() {
		JSONObject json = new JSONObject()
				.put("voter", "B")
				.put("pollId", 2);
		Vote vote = vsi.vote(json);
		System.out.println("result"+vote);		
	}
	
}
