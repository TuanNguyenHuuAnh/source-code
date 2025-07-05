package vn.com.unit.cms.core.module.contract.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractDueDateSearchDto extends CommonSearchWithPagingDto {
	
	private String agentCode;
	private String type;
	private String agentGroup;
	private String policyNo;
	private Object policyKey;
	private Object insuranceBuyer;
	private Object feeExpected;
	private Object paymentPeriodDate;
	private Object tinhTrangThuPhi;
	private String agentName;
	private String agentType;
}
