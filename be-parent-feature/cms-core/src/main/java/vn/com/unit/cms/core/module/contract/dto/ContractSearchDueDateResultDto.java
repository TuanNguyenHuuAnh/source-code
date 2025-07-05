package vn.com.unit.cms.core.module.contract.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractSearchDueDateResultDto {
	private Integer no;						// STT
	private String insuranceContract;		// So HDBH
	private String customerName;			// Ben mua BH
	private String customerNo;				// Ben mua BH
	private String mainInsurancePerson;		// Nguoi duoc BH chinh
	private String amountOfMoney;			// So tien
	private Date payfeesDate;				// Ky dong phi
	private String chargeStatus;			// Tinh trang thu phi
	private String periodicFeePayment;		// Dinh ky dong phi
	private Date effectiveDate;				// Ngay hieu luc
	private BigDecimal recurringBasicFee;		// Phi co ban dinh ki
	private String waitingFee;				// Phi cho ghi nhan
	private String predebtPeriodFee;		// Phi cac ki truoc chua dong
	private String debts;					// Khoan no (Vay dong phi tu dong + lai)
	private Date expirationDate;			// Ngay se mat hieu luc

	private String address;//địa chỉ
	private String phoneNumber;//so dien thoai di dong của tvtc
	private String phoneNumberNr; //sdt NR
	private String phoneNumberOffice;//sdt van phong
	private String estimatedRecurringFee; //phi du tinh dinh ki
	private String totalFeePaid;//tong phi da dong
	private String basicFee;//phi co ban
	private String extraFee;//phi dong them

	//new properties
	private String policyNo; //hdbh
	private String insuranceBuyer; //bmbh
	private String bdbh; //nguoi duoc Bh chinh
	private Date paymentPeriodDate; //ky dong phi
	private BigDecimal feeExpected; //so tien
	private String tinhTrangThuPhi; // tinh trang thu phi

	// claim
	private String link; //Bổ sung chứng từ nhận quyền lợi thanh toán
	private String note; // Lý do từ chối
	
	private boolean check;
	
	private String recurringFeePayment;      //dINH KY DONG PHI due
	private Date ngayHieuLuc;		        //Ngày hiệu lực
	private BigDecimal phiDuTinhDinhKy;	    //Phí dự tính định kỳ	
	private BigDecimal phiCoBanDinhKy;		//Phí cơ bản định kỳ
	private BigDecimal phiChoGhiNhan;		//Phí chờ ghi nhận
	private BigDecimal feeMustReceive;		//Phí các kỳ trước chưa đóng
	private BigDecimal noApl;				//Nợ APL
	private BigDecimal tongPhiDaDung;		 // Tổng phí đã đóng
	private BigDecimal phiCoBan;				//Phí cơ bản
	private BigDecimal phiDinhKy;			  //Phí đóng thêm
	private Date paymentPeriodRecent;   //--KY DONG PHI GAN NHAT
	private Date expiredDate;			  	//-NGAY_SE_MAT_HIEU_LUC	
	private String agentKey;
	private Integer loaihopdong;			//1: hd truyen thong, 2: hd lien ket chung, 3: hd lien ket don vi
	private Integer isShare;
	private Date polLstPaidToDt; //ky dong du phi gan nhat
	private BigDecimal phiDongTruoc;			  //Phí đóng thêm
}
