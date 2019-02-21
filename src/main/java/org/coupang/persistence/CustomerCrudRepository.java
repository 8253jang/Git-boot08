package org.coupang.persistence;

import org.coupang.domain.WebBoard;
import org.springframework.data.repository.CrudRepository;

public interface CustomerCrudRepository extends CrudRepository<WebBoard, Long>, CustomerBoard {

}
