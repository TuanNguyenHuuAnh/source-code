package vn.com.unit.cms.core.module.agent.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class AgentSASearchDto extends CommonSearchWithPagingDto{
	private String agentCode;
	private String agentGroup;
	private Integer idRegion;
	private String idAreaData;
	
	private String nameBdth;
	private String nameBdah;
	private String nameBdrh;
	private String nameBdoh;
	private String officeName;
	private String headOfDepartmentName;
	private String managerName;
	private String tvtcName;
	private String idCard;
	private String phoneNum;
	private Date startDate;
	private String totalcontractRelease;
	private String totalMonthNonActive;
	private String address;
}
