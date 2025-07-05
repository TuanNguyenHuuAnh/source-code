package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsAgencyStructureDto {

	private Long id;

	private String bpCode;

	private String agentCode;

	private String agentName;

	private String agentType;

	private Date appointedDate;

	private Date effectedDate;

	private Date terminatedDate;

	private String agentStatus;  

	private String introducerCode;
	
	private String introducerName;

	private String introducerType;

	private String directReportingCode;

	private String directReportingName;

	private String indirectRptCodeLv1;
	
	private String indirectRptNameLv1;

	private String indirectRptCodeLv2;

	private String indirectRptNameLv2;

	private String indirectRptCodeLv3;

	private String indirectRptNameLv3;

	private String indirectRptCodeLv4;

	private String indirectRptNameLv4;

	private String indirectRptCodeLv5;

	private String indirectRptNameLv5;

	private String smCode;

	private String smName;

	private String ddCode;

	private String ddName;

	private String directAdCode;

	private String directAdName;

	private String directAreaCode;

	private String indirectAdCodeLv1;

	private String indirectAdNameLv1;

	private String indirectAreaCodeLv1;

	private String indirectAdCodeLv2;

	private String indirectAdNameLv2;

	private String indirectAreaCodeLv2;

	private String indirectAdCodeLv3;

	private String indirectAdNameLv3;

	private String indirectAreaCodeLv3;

	private String directRdCode;

	private String directRdName;

	private String directRegionRdCode;

	private String indirectRdCodeLv1;

	private String indirectRdNameLv1;

	private String indirectRegionCodeLv1;

	private String indirectRdCodeLv2;

	private String indirectRdNameLv2;

	private String indirectRegionCodeLv2;

	private String indirectRdCodeLv3;

	private String indirectRdNameLv3;

	private String indirectRegionCodeLv3;

	private String areaManagerCode;

	private String areaManagerName;

	private String bpOfficeCode;
	
	private String bpOfficeName;

	private String bpFocalCode;

	private String bpFocalName;

	private String doaName;

	private String regionCode;

	private String oldIdNumber;

	private String idNumber;

	private String telephone;
	
	private String email;

	private String emailCompany;

}
