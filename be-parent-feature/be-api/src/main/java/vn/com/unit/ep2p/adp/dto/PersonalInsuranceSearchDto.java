package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PersonalInsuranceSearchDto extends CommonSearchWithPagingDto{

	private String agentName;			//	ten TVTC
	private String agentCode;			//	agentCode
	private String status;				//	trang thai
	private Object docReceivedDate;		//	ngay nhan HSYCBH
	private Object insuranceBuyer;		//	ben mua BH
	private Object policyNo;			//	so HD
	private Object docNo;				//	so HSYCBH
	private Object effectiveDate;		//  ngay hieu luc
	private String type;				// 1: da nop, 2: bo sung, 3: da phat hanh, 4: bi tu choi
	private String typeEffect;
	
	private String partner;
	private String regionCode;
	private String zoneCode;
	private String uoCode;
	private String areaCodeDLVN;
	private String regionCodeDLVN;
	private String zoneCodeDLVN;
	private String ilCode;
	private String isCode;
	private String smCode;
	private String fromDate;
	private String toDate;
}
