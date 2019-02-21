package org.coupang.persistence;

import java.util.ArrayList;
import java.util.List;

import org.coupang.domain.QWebBoard;
import org.coupang.domain.QWebReply;
import org.coupang.domain.WebBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.java.Log;


@Log
public class CustomerCrudRepositoryImpl extends QuerydslRepositorySupport implements CustomerBoard {
  public CustomerCrudRepositoryImpl() {
	  super(WebBoard.class);
  }
  
	@Override
	public Page<Object[]> getCustomerPage(String type, String keyword, Pageable page) {
		log.info("=======================");
		log.info("type : " + type);
		log.info("keyword : " + keyword);
		log.info("page : " + page);
		
		log.info("=======================");
		
		QWebBoard b = QWebBoard.webBoard;
		QWebReply r = QWebReply.webReply; //조인을 위한 준비
		
		JPQLQuery<WebBoard> query = from(b);
				
				
		JPQLQuery<Tuple> tuple = query.select(b.bno, b.title, b.writer, b.regdate, r.count());//select절 컬럼선택
		
		
		
		tuple.leftJoin(r);//조인종류
		tuple.on(b.bno.eq(r.board.bno)); //조인조건
		tuple.where(b.bno.gt(0L));
		
		
		
		if(type !=null) {
			switch (type.toLowerCase()) {
			case "t":
				 tuple.where(b.title.like("%" +keyword +"%"));
				break;
			case "c":
				 tuple.where(b.content.like("%" +keyword +"%"));
				break;
			case "w":
				 tuple.where(b.writer.like("%" +keyword +"%"));
				break;
			}
		}
		
		
		tuple.groupBy(b.bno, b.title, b.writer, b.regdate);//b.bno, b.title, b.writer, b.regdate
		tuple.orderBy(b.bno.desc());//정렬
		
		tuple.offset(page.getOffset()); //시작 
		tuple.limit(page.getPageSize());//개수
		
		//log.info("tuple.fetch() : " + tuple.fetch());//전체 레코드 
		
		//나온 결과를 Page<Object[]> 리턴하기 위한 준비
		 List<Tuple> list = tuple.fetch();
		 
		 List<Object[]> resultList = new ArrayList<>();
		 
		 list.forEach(t ->{
			 resultList.add(t.toArray());
		 });
		 
		 
		 //long total = tuple.fetchCount();
		 
		 
		 JPQLQuery<WebBoard> query2 = from(b);
		 JPQLQuery<Long> tupleCount = query2.select(b.bno);
		 if(type !=null) {
				switch (type.toLowerCase()) {
				case "t":
					tupleCount.where(b.title.like("%" +keyword +"%"));
					break;
				case "c":
					tupleCount.where(b.content.like("%" +keyword +"%"));
					break;
				case "w":
					tupleCount.where(b.writer.like("%" +keyword +"%"));
					break;
				}
			}
		 
		 
		 long total = tupleCount.fetchCount();
		 
		 
		
		
		
		return new PageImpl<>(resultList, page,total);
	}

}
