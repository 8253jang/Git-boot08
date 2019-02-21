package org.coupang.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="tbl_webboards")
@EqualsAndHashCode(of="bno")
@ToString(exclude="replies")
public class WebBoard {
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE , generator="board_bno")
  @SequenceGenerator(sequenceName = "board_bno", allocationSize = 1 , name="board_bno")
  private Long bno;
  private String title;
  private String writer;
  private String content;
  
  @CreationTimestamp
  private Timestamp regdate;
  
  @UpdateTimestamp
  private Timestamp updatedate;
  
  @OneToMany(mappedBy="board" , fetch=FetchType.LAZY)//지연로딩
  private List<WebReply> replies;
  
}
