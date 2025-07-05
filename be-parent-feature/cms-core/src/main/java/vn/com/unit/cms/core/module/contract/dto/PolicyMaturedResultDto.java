package vn.com.unit.cms.core.module.contract.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyMaturedResultDto {
	private Integer no;							// STT
	private String policyKey;					// So HDBH
	private String poName;						// Ben mua BH
	private Date polIssueEff;					// Ngay hieu luc
	private Date polCeasDt;						// Ngay dao han
	private String servicingAgentKey;			// 
	private String primaryAgentKey;				// 
	private String maturedStatus; 				// Trang thai đáo hạn
	private String liName; 						// NĐBH chính
	private String planName; 					// SP BH chính
	private BigDecimal polBaseFaceAmt;			// Số tiền BH
	private BigDecimal amount;					// Số tiền tạm tính của quyền lợi bảo hiểm đáo hạn
	private String customerNo;					// Mã khách hàng
	private String homeAddress;					// Địa chỉ
	private String homePhone;					// Điện thoại bàn
	private String workPhone;					// Điện thoại cơ quan
	private String cellPhone;					// Số di động
	
	// Survey
	private String surveyResult;				// SURVEY_RESULT  => 0: KHÔNG ĐỒNG Y; 1: ĐỒNG Ý; 2:PHÂN VÂN; -1/NULL: CHƯA CÓ THÔNG TIN
	private String surveyResultDetail;			// SURVEY_RESULT_DETAIL => CHI TIẾT KHẢO SÁT TỪ CÁC HỆ THỐNG
	private Integer newProposalNum;				// NEW_PROPOSAL_NUM => SỐ LƯỢNG PROPOSAL MỚI (SO VỚI NGÀY ĐÁO HẠN -120 NGÀY -> +60 NGÀY)
	private Date newProposalDt;
	private Date lastSurveyDate;
	
	@Getter(AccessLevel.NONE)
	private String surveyResultDisplay;
	
	// Get value for survey result to display on UI
	public String getSurveyResultDisplay() {
		return getValueDisplay();
	}
	
	public String getValueDisplay() {
		String surveyResultDisplay = "";
		if ((this.newProposalNum != null && this.newProposalNum == 1) && "1".equals(this.surveyResult)) {
			if (newProposalDt != null && lastSurveyDate != null) {
				if (newProposalDt.compareTo(lastSurveyDate) >= 0) {
					surveyResultDisplay = "Đã nộp hồ sơ mua mới";
				} else {
					surveyResultDisplay = "Có nhu cầu mua mới";
				}
			} else {
				if (newProposalDt != null) {
					surveyResultDisplay = "Đã nộp hồ sơ mua mới";
				} else {
					surveyResultDisplay = "Có nhu cầu mua mới";
				}
			}
			
		} else if (this.newProposalNum != null && this.newProposalNum == 1) {
			surveyResultDisplay = "Đã nộp hồ sơ mua mới";
		} else if (this.surveyResult != null && "1".equals(this.surveyResult)) {
			surveyResultDisplay = "Có nhu cầu mua mới";
		}
		return surveyResultDisplay;
	}

	public void setSurveyDataToDisplay() {
		this.surveyResultDisplay = getValueDisplay();
	}
}