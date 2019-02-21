package org.coupang.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerBoard {
   Page<Object[]> getCustomerPage(String type, String keyword, Pageable page);//검색 + 페이징처리
}
