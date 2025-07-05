package vn.com.unit.cms.core.module.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	private Long orderId;
    private String agentCode;
    private String agentName;
    private String managerCode;
    private String managerName;
    private String officeCode;
    private String phone;
    private String deliveryAgentCode;
    private String deliveryAgentName;
    private String deliveryOfficeCode;
    private String deliveryProvince;
    private String invoiceOfficeCode;
    private String invoiceCompanyName;
    private String invoiceTaxCode;
    private String invoiceCompanyAddress;
    private int totalAmount;
    private String attachmentImg;
    private String attachmentPhysicalImg;
    private String statusOrder;
    private String productCodeJson;
    private String channel; 
    private String officeName;
    private boolean checkBill;
}	
