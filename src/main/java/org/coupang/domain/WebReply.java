package org.coupang.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 댓글처리(양방향 설정)
 * 
 * */

@Getter
@Setter
@Entity
@Table(name="tbl_webreplies")
@EqualsAndHashCode(of="rno")
@ToString(exclude="board")
public class WebReply {
	 @Id
	  @GeneratedValue(strategy=GenerationType.SEQUENCE , generator="reply_rno")
	  @SequenceGenerator(sequenceName = "reply_rno", allocationSize = 1 , name="reply_rno")
	  private Long rno;
	  private String replyText;
	  private String replyer;
	  
	  @CreationTimestamp
	  private Timestamp regdate;
	  
	  @CreationTimestamp
	  private Timestamp updatedate;
	  
	  
	  @JsonIgnore // 338page참고 (JSON변환은 현재 객체를 JSON으로 반환하는 것 외에도 객체가 참조하고 있는 내부객체역시 JSON으로 변환한다. 양방향인경우 문제가 될수 있으므로 특정한 속성은 JSON으로 변화하지 못하도록 설정 필요
	  @ManyToOne(fetch=FetchType.LAZY)
	   private WebBoard board;
}
