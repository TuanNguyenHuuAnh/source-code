package vn.com.unit.cms.core.module.contract.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyMaturedSearchDto extends CommonSearchWithPagingDto {
	private String agentCode;					// Ma TVTC
	private Object policyNo;					// So HDBH
	private Object poName;						// Ben mua BH
	private Object polCeasDt;					// Ngay dao han
	private Object matureStatus; 				// Trang thai đáo hạn
	private String agentName;
	private String agentType;
	private String maturedType;
	private String agentGroupType;
	// Survey
	private Object surveyResult;				// Thông tin khảo sát: 0: null, 1: Có nhu cầu mua mới, 2: Đã nộp hồ sơ mua mới
}
