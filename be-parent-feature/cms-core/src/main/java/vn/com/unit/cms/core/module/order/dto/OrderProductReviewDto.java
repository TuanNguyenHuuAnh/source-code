package vn.com.unit.cms.core.module.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductReviewDto {
	private String productCode;
	private String productName;
	private String productType;
	private Long unitPrice;
	private String productImg;
	private String productPhysicalImg;
	private Long quantity;
	private boolean flag;
}
