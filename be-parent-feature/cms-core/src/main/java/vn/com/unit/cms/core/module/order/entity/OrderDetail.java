package vn.com.unit.cms.core.module.order.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Table(name = "M_ORDER_DETAIL")
@Getter
@Setter
public class OrderDetail {
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_ORDER_DETAIL")
    private Long id;
	
	@Column(name = "ORDER_ID")
    private Long orderId;
	
	@Column(name = "PRODUCT_CODE")
    private String productCode;
	
	@Column(name = "QUANTITY")
    private Long quantity;
}
