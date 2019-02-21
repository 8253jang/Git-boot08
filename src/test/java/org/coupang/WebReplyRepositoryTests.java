package org.coupang;

import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.coupang.domain.WebBoard;
import org.coupang.domain.WebReply;
import org.coupang.persistence.WebBoardRepository;
import org.coupang.persistence.WebReplyRepository;
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
public class WebReplyRepositoryTests {

	@Autowired
	WebReplyRepository repo;
	
	/**
	 * 데이터 추가(dummy)
	 * */
	@Test
	public void testInsertReplies() {
		 Long [] arr = {303L, 300L, 296L, 290L};
		Arrays.stream(arr).forEach(num->{
			WebBoard board = new WebBoard();
			board.setBno(num);
			
			IntStream.range(0, 10).forEach(i->{
				WebReply reply = new WebReply();
				reply.setReplyText("Reply..." + i);
				reply.setReplyer("replyer" +i);
				reply.setBoard(board);
				
				repo.save(reply);
			});
		});

	}
	
	
}
