package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;

@Getter
@Setter
public class OrderEditDto  extends CmsCommonEditDto {
	 	
	private Long orderId;
    private String agentCode;
    private String agentName;
    private String officeCode;
    private String phone;
    private String statusOrder;
    private Date cancelDate;
    private String cancelBy;
    private String invoiceCompanyName;
    private String invoiceTaxCode;
    private String invoiceCompanyAddress;
    private Integer totalAmount;
    private String attachmentPhysicalImg;
    private String notes;
}