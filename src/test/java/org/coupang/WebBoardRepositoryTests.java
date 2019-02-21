package org.coupang;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.coupang.domain.WebBoard;
import org.coupang.persistence.WebBoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebBoardRepositoryTests {

	@Autowired
	WebBoardRepository repo;
	
	/**
	 * 데이터 추가(dummy)
	 * */
	@Test
	public void insertBoardDummies() {
		IntStream.range(0,300).forEach(i->{
			WebBoard board = new WebBoard();
			
			board.setTitle("Sample Board Title "+ i);
			board.setContent("Content Sample .." + i+" of Board");
			board.setWriter("user0" + (i%10));
			
			repo.save(board);
			
		});// @formatter:off
		 
		// @formatter:on

	}
	
	@Test
	public void testList1() {
		Pageable pageable = PageRequest.of(0,20,Direction.DESC, "bno");
		
		Page<WebBoard> result = repo.findAll(repo.makePredicate(null, null), pageable);
		
		log.info("PAGE : " + result.getPageable());
		
		log.info("------------------");
		result.getContent().forEach(board-> log.info(""+board));
	}
	
	@Test
	public void testList2() {
		Pageable pageable = PageRequest.of(0,20,Direction.DESC, "bno");
		
		Page<WebBoard> result = repo.findAll(repo.makePredicate("t", "10"), pageable);
		
		log.info("PAGE : " + result.getPageable());
		
		log.info("------------------");
		result.getContent().forEach(board-> log.info(""+board));
	}
	
	
	//376page test
	@Test
	public void testList3() {
	  Pageable pageable = PageRequest.of(0, 10, Direction.DESC , "bno");
	  
	  List<Object[]> list = repo.getListWithQuery(pageable);
	  
	  list.forEach(arr-> log.info(Arrays.toString(arr)));
	
	}
}
