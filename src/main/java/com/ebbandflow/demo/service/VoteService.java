package com.ebbandflow.demo.service;

import java.util.List;

import org.json.JSONObject;

import com.ebbandflow.demo.model.Poll;
import com.ebbandflow.demo.model.Vote;

public interface VoteService {

	boolean existById(Long id);

	//	    取得投票項目 好像用不到
	Poll getPollById(Long id);

	//	    新增投票選項
	Poll createPoll(JSONObject json);

	//	    修改投票項目
	Poll updatePoll(JSONObject json);

	//	    刪除投票項目
	boolean deletePollById(Long id);

	//投票
	//	   船投票人名跟選項ID 自動票數+1
	Vote vote(JSONObject json);

	//	    列出全部投票項目
	List<Poll> findAllPoll(JSONObject json);

}