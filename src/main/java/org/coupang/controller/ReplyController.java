package org.coupang.controller;

import java.util.List;

import org.coupang.domain.WebBoard;
import org.coupang.domain.WebReply;
import org.coupang.persistence.WebReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController // ajax처리를 위한 JSON형태 응답
@RequestMapping("/replies/*")
@Log
public class ReplyController {

	 @Autowired
	 private WebReplyRepository replyRepo;
	 
	 @PostMapping("/{bno}")
	 @Transactional
	 public ResponseEntity<List<WebReply>> addRepy(@PathVariable("bno") Long bno , @RequestBody WebReply reply){
		  
		 log.info("addReply......");
		 log.info("bno : " + bno );
		 log.info("reply : " + reply );
		 
		 WebBoard board = new WebBoard();
		 board.setBno(bno);

		 reply.setBoard(board);
		 replyRepo.save(reply);
		 
		 return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);//상태코드 201번
	 }
	 
	 private List<WebReply> getListByBoard(WebBoard board) throws RuntimeException{
		 log.info("getListByBoard : " + board );
		 
		 return replyRepo.getRepliesOfBoard(board);
	 }
	 
	 /**
	  * 댓글 삭제
	  * */
	  @Transactional
	  @DeleteMapping("/{bno}/{rno}")
	 public ResponseEntity<List<WebReply>> remove(@PathVariable("bno") Long bno , @PathVariable("rno") Long rno){
		  log.info("delete reply : rno " + rno +" , " + bno  );
		  
		  replyRepo.deleteById(rno);
		  
		  WebBoard board = new WebBoard();
		  board.setBno(bno);
		  
		 return new ResponseEntity<List<WebReply>>(getListByBoard(board), HttpStatus.OK);
	 }
	  
	  
	     /**
		  * 댓글 수정
		  * */
		  /*@Transactional
		  @PutMapping("/{bno}")
		 public ResponseEntity<List<WebReply>> modify(@PathVariable("bno") Long bno , @RequestBody WebReply reply){
			  log.info("modify reply :  " + reply );
			  
			  replyRepo.findById(reply.getRno()).ifPresent(origin->{
				  origin.setReplyText(reply.getReplyText());
				  
				  replyRepo.save(origin);
			  });
			  
			  WebBoard board = new WebBoard();
			  board.setBno(bno);
			  
			 return new ResponseEntity<List<WebReply>>(getListByBoard(board), HttpStatus.CREATED);
		 }*/
	  
	  @Transactional
	  @PutMapping("/{bno}")
	  @ResponseBody
	 public List<WebReply> modify(@PathVariable("bno") Long bno , @RequestBody WebReply reply){
		  log.info("modify reply :  " + reply );
		  
		  replyRepo.findById(reply.getRno()).ifPresent(origin->{
			  origin.setReplyText(reply.getReplyText());
			  origin.setReplyer(reply.getReplyer());
			  
			  replyRepo.save(origin);
		  });
		  
		  WebBoard board = new WebBoard();
		  board.setBno(bno);
		  
		 return getListByBoard(board);
	 }
	  
	  /**
	   * 댓글 목록
	   * */
	  @GetMapping("/{bno}")
	  public ResponseEntity<List<WebReply>> getReplies(@PathVariable("bno") Long bno){
		  log.info("getAll getReplies :  " + bno );
		  
		  WebBoard board = new WebBoard();
		  board.setBno(bno);
		  
		  return new ResponseEntity<List<WebReply>>(getListByBoard(board) , HttpStatus.OK);
	  }
	  
}











