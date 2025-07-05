package vn.com.unit.cms.core.module.customer.dto;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyInformationDto {
	private Long id;
	private Integer no;
	private String insuranceContract;//so hdbh
	private String customerName;//ben mua BH
	private String customerNo; // Ma khach hang
	private String insuranceOfCustomerMain;//nguoi duoc bh chinh
	private Long phone;	//sdt
	private String address; //dia chi lien lac
//	private String divisionRatio; // ty le chia 
	private String polAgtShrPct; // ty le chia 
	private String periodicFeePayment;//dinh ky dong phi 
	private Date effectiveDate;//ngay hieu luc
	private Date dateDue;//ngay dao han
	private String assignmentForm;//hinh thuc phan cong 
	private String assignmentFormName;//hinh thuc phan cong 
	private Date assignmentDate;//ngay phan cong 
	private Date expirationDate;//ngay mat hieu luc
	private BigDecimal estimatedRecurringFee; //phi du tinh dinh ky
	private BigDecimal recurringBasicFee; //phi cb dinh ky
	private BigDecimal hangingFee; //phi con treo 
	private String productMainName;//ten sp chinh
	private String policyStatus;//trang thai hd
	private String policyType;
	private String polCsStatCd;
	private String autoDebit;
	private String proposalNo;
	private BigDecimal totalPremiumPaid; // Tổng phí bảo hiểm đã đóng
}
