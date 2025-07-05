package vn.com.unit.cms.core.module.customer.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInteractionHistoryDto {

	private Long id;
	private Integer no;						// STT
	private String customerType;			// Nhom KH
	private String insuredBuyer;			// Bên mua bảo hiểm
	private String insuredName;				// Nguoi duoc BH
	private String gender;					// Gioi tinh
	private String productName; 			// Ten san pham BH
	private Date createdDt;					// Ngay tao thong tin
	private String proposalNo;				// So HSYCBH
	private String policyNo;				// So HDBH
	private String status;					// Tinh trang
	private String planName; 				// Chuong trinh
	private BigDecimal insuredSumAssured;	// So tien BH
	private String paymentFreq;				// Dinh ky dong phi
	private BigDecimal premiumAmount;		// Phí định kỳ/cơ bản định kỳ
	private String policyTerm;				// Thoi gian bao ve(nam)
	private String lastVisitedStep;			// Buoc dung chan cua KH
	private String polInfStatus;			// Trang thai HĐ
	private Date polInfcDt;					// Ngay hieu luc
	private Date polStatChngDt;				// Ngay tu choi
	private Date polLapseStartDt;			// Ngày kết thúc thời gian gia hạn đóng phí
	private Date polCeasDt;					// Ngay dao han
	private BigDecimal polFeeNextPeriod;	// Phí đóng trước cho kỳ tới
	private BigDecimal totalPremiumPaid;	// Tổng phí bảo hiểm đã đóng
	private String address;					// Địa chỉ
	private String cellPhone;				// Số điện thoại di động
}
