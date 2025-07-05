package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PolicyInfoDto {
	private Long polId;						// ma hop dong
	private String polBillModeCd;			// dinh ky dong phi
	private String polStatusTxt;			// tinh trang hop dong
	private String polAppFormId;			// so HSYCBH
	private String polBusClasCd;			// xac dinh HD thuoc san pham nao
	private String catOfficialName;			// hinh thuc phan cong

	private BigDecimal polMpremAmt;				// phi dinh ky
	private BigDecimal polSndryAmt;				// phi du tinh ki
	private BigDecimal suspendAmount;			// phi treo
	private BigDecimal totalDeposit;			// tong phi bao hiem da dong
	private BigDecimal unpaidPremium;			// phi bao hiem  co ban
	private BigDecimal excessPremium;			// phi bao hiem dong them

	private BigDecimal polBaseFaceAmt;		// so tien bao hiem
	private BigDecimal zprxUnpaidPremAmt;   // Phí cơ bản các kỳ trước chưa đóng
	private BigDecimal polPremSuspAmt;
	private BigDecimal polMiscSuspAmt;
	private BigDecimal polCount;			// polPremSuspAmt-polMiscSuspAmt = Phí đóng trước cho kỳ tới
	private Date polLapseStartDt;           // ngay se mat hieu luc
	private Date polCeasDt;					// ngay dao han/mat hieu luc
	private Date polBillToDtNum;
	private Date polPdToDtNum;
	private Date polIssEffDt;				// ngay hieu luc
	private Date servAgtAsignDt; 			// ngay phan cong
	private Date lastPtdate;				// ky phi dong du gan nhat
	private BigDecimal tempPremium;			// Phí bảo hiểm cần đóng tạm tính
}
