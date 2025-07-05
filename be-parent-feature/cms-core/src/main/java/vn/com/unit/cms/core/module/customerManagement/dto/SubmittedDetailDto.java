package vn.com.unit.cms.core.module.customerManagement.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SubmittedDetailDto {
	private String planId; // Tên sản phẩm
	private String polInsrdName; // NĐBH
	private BigDecimal cvgBasicPremAmt; // Phí bảo hiểm theo kỳ
	private String cvgFaceAmt; // Số tiền BH
	private Date cvgIssEffDt; // Ngày hiệu lực
	private Date cvgMatXpryDt; // Ngày đáo hạn
	private String mainProduct; //san pham chinh
	private String cvgPlanId;// loai san pham
	private String cvgCstatCd;// tình trạng
    private String cvgStatusCd; // CVG_STATUS_CD
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPolInsrdName() {
		return polInsrdName;
	}
	public void setPolInsrdName(String polInsrdName) {
		this.polInsrdName = polInsrdName;
	}
	public BigDecimal getCvgBasicPremAmt() {
		return cvgBasicPremAmt;
	}
	public void setCvgBasicPremAmt(BigDecimal cvgBasicPremAmt) {
		this.cvgBasicPremAmt = cvgBasicPremAmt;
	}
	public String getCvgFaceAmt() {
		return cvgFaceAmt;
	}
	public void setCvgFaceAmt(String cvgFaceAmt) {
		this.cvgFaceAmt = cvgFaceAmt;
	}
	public Date getCvgIssEffDt() {
		return cvgIssEffDt;
	}
	public void setCvgIssEffDt(Date cvgIssEffDt) {
		this.cvgIssEffDt = cvgIssEffDt;
	}
	public Date getCvgMatXpryDt() {
		return cvgMatXpryDt;
	}
	public void setCvgMatXpryDt(Date cvgMatXpryDt) {
		this.cvgMatXpryDt = cvgMatXpryDt;
	}
	public String getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
	public String getCvgPlanId() {
		return cvgPlanId;
	}
	public void setCvgPlanId(String cvgPlanId) {
		this.cvgPlanId = cvgPlanId;
	}
	public String getCvgCstatCd() {
		return cvgCstatCd;
	}
	public void setCvgCstatCd(String cvgCstatCd) {
		this.cvgCstatCd = cvgCstatCd;
	}
	public String getCvgStatusCd() {
		return cvgStatusCd;
	}
	public void setCvgStatusCd(String cvgStatusCd) {
		this.cvgStatusCd = cvgStatusCd;
	}
    
    
}