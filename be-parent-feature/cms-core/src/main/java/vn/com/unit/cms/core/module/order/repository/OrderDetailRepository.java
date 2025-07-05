package vn.com.unit.cms.core.module.order.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.order.dto.OrderProductReviewDto;
import vn.com.unit.cms.core.module.order.entity.OrderDetail;
import vn.com.unit.db.repository.DbRepository;

public interface OrderDetailRepository extends DbRepository<OrderDetail, Long> {
	public List<OrderProductReviewDto>  getOrderProductReview(@Param("orderId")  Long orderId); 
}
