package vn.com.unit.cms.core.module.order.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

@Getter
@Setter
public class OrderLanguageSearchDto extends CmsCommonSearchResultFilterDto {

	private Long orderId;
	private String code;
    private String agentCode;
    private String agentName;
    private String channel;
    private String officeCode;
    private String officeName;
    private String phone;
    private String statusOrder;
    private Date cancelDate;
    private String cancelBy;
    private String invoiceCompanyName;
    private String invoiceTaxCode;
    private String invoiceCompanyAddress;
    private BigDecimal totalAmount;
    private String productCode;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private BigDecimal amount;
    
}
