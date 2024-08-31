package com.ebbandflow.demo.contorller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebbandflow.demo.model.Poll;
import com.ebbandflow.demo.model.Vote;
import com.ebbandflow.demo.service.VoteService;





@RestController
@CrossOrigin
@RequestMapping("/demo")
public class VoteController {
	
	
	@Autowired
	private VoteService vsi;
	
//	測試畫面
	@GetMapping("/")
	public String test1() {
		return "jjjjj";
	}
	
	//新增
    @PostMapping("/insert")
    public ResponseEntity<?> insertPoll(@RequestBody String  request) {
    	JSONObject json = new JSONObject(request);
    	Poll poll = vsi.createPoll(json);
    	
    	if(poll != null) {   		
    		String uri = "http://localhost:8080/demo/insert/"+poll.getId();
    		return ResponseEntity.created(URI.create(uri))
            		.contentType(MediaType.APPLICATION_JSON)
                    .body(poll);
    	}
    	return ResponseEntity.noContent().build();    	
    }
  //修改
    @PutMapping("/update/{pk}")
    public ResponseEntity<?> update(@PathVariable(name = "pk") Long id, @RequestBody String req) {
    	
    	JSONObject json = new JSONObject(req);
    	json.put("pollId", id);
    	
    	
    	Poll poll = vsi.updatePoll(json);
    	if(poll!=null) {
    		return ResponseEntity.ok(poll);
    	}
 		return ResponseEntity.notFound().build();   	
    }
    
  //刪除
    @DeleteMapping("/delete/{pk}")
    public ResponseEntity<Void> remove(@PathVariable(name = "pk") Long id) {
    	if(id!=null && id!=0) {
    		boolean exists = vsi.existById(id);
    		if(exists) {
    			if(vsi.deletePollById(id)) {
    				return ResponseEntity.noContent().build();
    			}
    		}
    	}
 		return ResponseEntity.notFound().build();
    }
	
//	投票
    @PostMapping("/vote")
    public ResponseEntity<?> vote(@RequestBody String  req) {
    	JSONObject json = new JSONObject(req);
    	 Vote vote = vsi.vote(json);
    	
    	if(vote != null) {   		
    		String uri = "http://localhost:8080/demo/vote/"+vote.getId();
    		return ResponseEntity.created(URI.create(uri))
            		.contentType(MediaType.APPLICATION_JSON)
                    .body(vote);
    	}
    	return ResponseEntity.noContent().build();    	
    }
    
//    顯示選項
    @GetMapping("/poll")
    public ResponseEntity<?> find(@RequestParam  Map<String, String> search) {    	
    	JSONObject json = new JSONObject(search);
        List<Poll> poll = vsi.findAllPoll(json);
        return ResponseEntity.ok(poll);
   
    }
	
}
