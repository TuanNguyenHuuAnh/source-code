package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.cms.core.module.order.dto.OrderDto;
import vn.com.unit.cms.core.module.order.dto.OrderInformationDto;
import vn.com.unit.cms.core.module.order.dto.OrderReviewDto;

public interface ApiOrderService {
	public List<OrderInformationDto> getListOrderByAgent (String agentCode);
	public OrderDto saveOrder(OrderDto orderDto); 
	public OrderReviewDto getOrderReview(Long orderId, String userName) throws IOException;
	public OrderDto updateOrderInformation(OrderDto orderDto);
	public OrderDto submit(List<MultipartFile> document, OrderDto orderDto);
}
