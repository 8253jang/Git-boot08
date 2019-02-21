package org.coupang.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageVO {
   private static final int DEFALUT_SIZE=10;
   private static final int DEFALUT_MAX_SIZE=50;
   
   private int page;
   private int size;
   
   private String keyword;
   private String type;
   
   
   public PageVO() {
	   this.page=1;
	   this.size=DEFALUT_SIZE;
   }

    
	public String getKeyword() {
		return keyword;
	}
	
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	public String getType() {
		return type;
	}
	
	
	public void setType(String type) {
		this.type = type;
	}


	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page < 0 ? 1 : page;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size < DEFALUT_SIZE || size > DEFALUT_MAX_SIZE ? DEFALUT_SIZE : size;
	}
   
    public Pageable makePageable(int direction , String ... props) {
    	Sort.Direction dir = direction==0 ? Sort.Direction.DESC : Sort.Direction.ASC;
    	
    	return PageRequest.of(this.page-1, this.size , dir, props);
    }
}
