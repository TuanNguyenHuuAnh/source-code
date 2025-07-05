package vn.com.unit.cms.core.module.customer.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyInformationSearchDto extends CommonSearchWithPagingDto{
	private String agentCode;
	private String policyNo;
	private String policytype;
	private String insuranceContract;//so hdbh
	private String customerName;//ben mua BH
	private String customerNo; // Ma khach hang
	private String insuranceOfCustomerMain;//nguoi duoc bh chinh
	private Long phone;	//sdt
	private String address; //dia chi lien lac
//	private String divisionRatio; // ty le chia 
	private String polAgtShrPct; // ty le chia 
	private String periodicfeepayment;//dinh ky dong phi 
	private Date effectiveDate;//ngay hieu luc
	private Date dateDue;//ngay dao han
	private String assignmentForm;//hinh thuc phan cong 
	private Date assignmentDate;//ngay phan cong 
	private Date expirationDate;//ngay mat hieu luc
	private String estimatedrecurringfee; //phi du tinh dinh ky 
	private String recurringbasicfee; //phi cb dinh ky 
	private String hangingFee; //phi con treo 
	private String productMainName;//ten sp chinh
	private String policyStatus;//trang thai hd
}
