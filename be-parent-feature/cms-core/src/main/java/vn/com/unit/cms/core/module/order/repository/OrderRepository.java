package vn.com.unit.cms.core.module.order.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.order.dto.OrderInformationDto;
import vn.com.unit.cms.core.module.order.dto.OrderLanguageSearchDto;
import vn.com.unit.cms.core.module.order.dto.OrderSearchDto;
import vn.com.unit.cms.core.module.order.entity.Order;
import vn.com.unit.db.repository.DbRepository;

public interface OrderRepository extends DbRepository<Order, Long> {
	 public List<OrderInformationDto> getListOrderByAgentCode(@Param("agentCode")  String agentCode) ;
	 public OrderInformationDto getOrderInfomation(@Param("orderId")  Long orderId, @Param("userName") String userName);
	 @Modifying
	 public void deleteOldProductInOrder(@Param("orderId")  Long orderId);
	 
	 public int countList(@Param("searchDto") OrderSearchDto searchDto);
	 
	 public Page<OrderLanguageSearchDto> findListSearch(@Param("searchDto") OrderSearchDto searchDto,
	            Pageable pageable);
	 public String getMaxOrderCode(@Param("prefix") String prefixCodeNot);
	 public int countForExportGeneral();
	 public int countForExportDetail();
	 public List<OrderLanguageSearchDto> getListForExportGeneral();
	 public List<OrderLanguageSearchDto> getListForExportDetail();

}
