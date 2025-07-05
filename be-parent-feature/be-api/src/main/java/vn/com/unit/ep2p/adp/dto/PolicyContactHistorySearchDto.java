package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyContactHistorySearchDto extends CommonSearchWithPagingDto{

	private Object policyNo;			//	so HD
	private Object docNo;				//	so HSYCBH
	private String type;
	
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
	private String contactResult;
	private String contactFromDate;
	private String contactToDate;
	private String contactGroup;
}
