package com.ebbandflow.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebbandflow.demo.model.Poll;
import com.ebbandflow.demo.model.Vote;
import com.ebbandflow.demo.repository.PollRepository;
import com.ebbandflow.demo.repository.VoteRepository;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class VoteServiceImpl implements VoteService {
	 	@Autowired
	    private PollRepository pollRepository;

	    @Autowired
	    private VoteRepository voteRepository;
	    
	    @PersistenceContext
		private Session session;

		public Session getSession() {
			return this.session;
		}
		
	    @Override
		public boolean existById(Long id) {
	    	if(id!=null) {
	    		return pollRepository.existsById(id);	
	    	}
	    	return false;
	    }
	    
//	    取得投票項目 好像用不到
	    @Override
		public Poll getPollById(Long id) {
	    	if(id !=null) {
	    		Optional<Poll> optional=pollRepository.findById(id);
	    		if(optional.isPresent()) {
	    			return optional.get();
	    		}
	    	
	    	}
	    	return null;
	    	
	    }

//	    新增投票選項
	    @Override
	    public Poll createPoll(JSONObject json) {

			String title = json.isNull("title") ? null : json.getString("title");
			
			Poll poll=new Poll();
			poll.setTitle(title);
			return pollRepository.save(poll);
	    
	    }
	    
	    
	    
//	    修改投票項目
	    @Override
		public Poll updatePoll(JSONObject json) {
			Long id = json.isNull("pollId") ? null : json.getLong("pollId");

			String title = json.isNull("title") ? null : json.getString("title");
			
			if(id!=null) {
				Optional<Poll> optional =pollRepository.findById(id);
				if(optional.isPresent()) {
					Poll poll = optional.get();
					poll.setTitle(title);
					return pollRepository.save(poll);
				}
			}
			return null;
	    }
	    
	    
//	    刪除投票項目
	    @Override
		public boolean deletePollById(Long id) {
	    	if(id !=null) {
	    		Optional<Poll> optional = pollRepository.findById(id);
	    		if(optional.isPresent()) {
	    			pollRepository.deleteById(id);
	    			return true;
	    		}
	    	}
	    	return false;
	    }
//投票
//	   船投票人名跟選項ID 自動票數+1
	    @Override
	    public Vote vote(JSONObject json) {
	    	
			String voter = json.isNull("voter") ? null : json.getString("voter");
			Long pollId = json.isNull("pollId") ? null : json.getLong("pollId");
			
			//多投票怎麼做
			Vote vote=new Vote();
			vote.setVoter(voter);
			
	    	if(pollId !=null && pollId != 0) {
	    		
	    		
	    		Poll poll = getPollById(pollId);
	    		poll.setVoteCount(poll.getVoteCount()+1L);
	    		vote.setPoll(poll);
	    		
	    	}
	    	
	    	
	    	return voteRepository.save(vote);
	    	
	    }

//	    列出全部投票項目
	    @Override
	    public List<Poll> findAllPoll(JSONObject json) {
//			查詢條件 投票項目名
	    	String title = json.isNull("title") ? null : json.getString("title");

	    	//分頁排序
			
			String order = json.isNull("order") ? "id" : json.getString("order");
			boolean dir = json.isNull("dir") ? true : json.getBoolean("dir");
			
	    	
	    	
	    	
	    	CriteriaBuilder criteriaBuilder=this.session.getCriteriaBuilder();
			CriteriaQuery<Poll> criteriaQuery = criteriaBuilder.createQuery(Poll.class);
			
			Root<Poll> table = criteriaQuery.from(Poll.class);

			List<Predicate> predicates = new ArrayList<>();

			if(title!=null && title.length()!=0) {
				predicates.add(criteriaBuilder.like(table.get("title"),"%"+title+"%"));	
			}
			if (predicates != null && !predicates.isEmpty()) {
				Predicate[] array = predicates.toArray(new Predicate[0]);
				criteriaQuery = criteriaQuery.where(array);
			}
	    	
//			Order By
			if(dir) {
				criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(order)));
			} else {
				criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get(order)));
			}
			
			TypedQuery<Poll> typedQuery = this.session.createQuery(criteriaQuery);
			List<Poll> result = typedQuery.getResultList();
			if(result!=null && !result.isEmpty()) {
				return result;
			} else {
				return null;
			}
			
			
	    }
}
