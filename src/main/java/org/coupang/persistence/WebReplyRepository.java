package org.coupang.persistence;

import java.util.List;

import org.coupang.domain.WebBoard;
import org.coupang.domain.WebReply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WebReplyRepository extends CrudRepository<WebReply, Long> {
   /**
    * 댓글 리스트를 처리하기 위한 메소드
    * */
	@Query("Select r from WebReply r where r.board=?1 And r.rno > 0 order by r.rno ASC")
	List<WebReply> getRepliesOfBoard(WebBoard board);
}
