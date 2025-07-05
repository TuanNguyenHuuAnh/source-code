package vn.com.unit.cms.core.module.order.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import vn.com.unit.cms.core.entity.AbstractTracking;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Table(name = "M_ORDER")
@Getter
@Setter
public class Order extends AbstractTracking {
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_ORDER")
    private Long id;
	
	@Column(name = "CODE")
    private String code;
	
	@Column(name = "AGENT_CODE")
    private String agentCode;
	
	@Column(name = "AGENT_NAME")
    private String agentName;
	
	@Column(name = "MANAGER_CODE")
    private String managerCode;
	
	@Column(name = "MANAGER_NAME")
    private String managerName;

	@Column(name = "OFFICE_CODE")
    private String officeCode;
	
	@Column(name = "PHONE")
    private String phone;
	
	@Column(name = "DELIVERY_AGENT_CODE")
    private String deliveryAgentCode;
	
	@Column(name = "DELIVERY_AGENT_NAME")
    private String deliveryAgentName;
	
	@Column(name = "DELIVERY_OFFICE_CODE")
    private String deliveryOfficeCode;
	
	@Column(name = "DELIVERY_PROVINCE")
    private String deliveryProvince;
	
	@Column(name = "INVOICE_OFFICE_CODE")
    private String invoiceOfficeCode;
	
	@Column(name = "INVOICE_COMPANY_NAME")
	private String invoiceCompanyName;
	
	@Column(name = "INVOICE_TAX_CODE")
    private String invoiceTaxCode;	
	
	@Column(name = "INVOICE_COMPANY_ADDRESS")
    private String invoiceCompanyAddress;
	
	@Column(name = "TOTAL_AMOUNT")
    private int totalAmount;
	
	@Column(name = "ATTACHMENT_IMG")
    private String attachmentImg;
	
	@Column(name = "ATTACHMENT_PHYSICAL_IMG")
    private String attachmentPhysicalImg;

	@Column(name = "STATUS")
    private String statusOrder;
	
	@Column(name = "NOTES")
    private String notes;

	@Column(name = "CHANNEL")
    private String channel;
	
	@Column(name = "OFFICE_NAME")
    private String officeName;
	
	@Column(name = "CANCEL_DATE")
    private Date cancelDate;
    
    @Column(name = "CANCEL_BY")
    private String cancelBy;
}
