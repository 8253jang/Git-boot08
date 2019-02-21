package org.coupang.persistence;

import java.util.List;

import org.coupang.domain.QWebBoard;
import org.coupang.domain.WebBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long> , QuerydslPredicateExecutor<WebBoard> {//QuerydslPredicateExecutor는 검색을 위해서..

	//1.8Version interface default 추가
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QWebBoard board = QWebBoard.webBoard;
		
	
		//bno > 0 
		builder.and(board.bno.gt(0)); //where bno > 0
		
		//type if else
		if(type==null) {
			return builder;
		}
		
		switch (type) {
		case "t":
			 builder.and( board.title.like("%"+keyword+"%"));
			break;
		case "c":
			 builder.and( board.content.like("%"+keyword+"%"));
			break;
		case "w":
			 builder.and( board.writer.like("%"+keyword+"%"));
		}
		
		return  builder;
	}
	
	
	///////////////////////////////////////////////////
	/**
	 * 376page : list.html문서의 제목 옆에 댓글 개수 출력 의 문제점 확인하기
	 * */
	@Query("Select b.bno, b.title, b.writer , b.regdate, count(r) from WebBoard b Left outer join b.replies r where b.bno > 0 group by b.bno, b.title, b.writer , b.regdate")
	List<Object[]> getListWithQuery(Pageable page);
}
