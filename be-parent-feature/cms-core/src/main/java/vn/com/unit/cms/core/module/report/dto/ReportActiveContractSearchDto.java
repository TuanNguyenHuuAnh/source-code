package vn.com.unit.cms.core.module.report.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ReportActiveContractSearchDto extends CommonSearchWithPagingDto{
	private String groupTarget;
	private String target;
	private String agentCode;
	private String yyyyMM;
	private String month;
	private String year;
	private Integer page;
	private Integer pageSize;
		
	
	private Object policyKey;												//Số HĐBH
	private Object liName;													//Bên mua bảo hiểm
	private Object receivedDate;											//Ngày nộp
	private Object polIssueEff;												//Ngày phát hành
	private Object polAgtShrPct;											//Tỷ lệ share
	
	private String keyType;									
}
