package vn.com.unit.cms.core.module.order.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderInformationDto {
	private Long id; 
	private String code;
	private String agentCode;
    private String agentName;
    private String managerCode;
    private String managerName;
    private String officeCode;
    private String officeName;
    private String phone;
    private String deliveryAgentCode;
    private String deliveryAgentName;
    private String deliveryOfficeCode;
    private String deliveryProvince;
    private String invoiceOfficeCode;
    private String invoiceCompanyName;
    private String invoiceTaxCode;
    private String invoiceCompanyAddress;
    private Long totalAmount;
    private Date createDate;
    private String statusOrder;
    private String statusName;
}
