package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name="ERS_AGENCY_STRUCTURE")
public class ErsAgencyStructure {
	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_AGENCY_STRUCTURE")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "BP_CODE")
	private String bpCode;
	
	@Column(name = "AGENT_CODE")
	private String agentCode;
	
	@Column(name = "AGENT_NAME")
	private String agentName;
	
	@Column(name = "AGENT_TYPE")
	private String agentType;
	
	@Column(name = "APPOINTED_DATE")
	private Date appointedDate;
	
	@Column(name = "EFFECTED_DATE")
	private Date effectedDate;
	
	@Column(name = "TERMINATED_DATE")
	private Date terminatedDate;
	
	@Column(name = "AGENT_STATUS")
	private String agentStatus;  
	
	@Column(name = "INTRODUCER_CODE")
	private String introducerCode;
	
	@Column(name = "INTRODUCER_NAME")
	private String introducerName;
	
	@Column(name = "INTRODUCER_TYPE")
	private String introducerType;
	
	@Column(name = "DIRECT_REPORTING_CODE")
	private String directReportingCode;
	
	@Column(name = "DIRECT_REPORTING_NAME")
	private String directReportingName;
	private String directReportingPosition;
	
	@Column(name = "INDIRECT_RPT_CODE_LV1")
	private String indirectRptCodeLv1;
	
	@Column(name = "INDIRECT_RPT_NAME_LV1")
	private String indirectRptNameLv1;
	
	@Transient
	private String indirectRptPositionLv1;
	
	@Column(name = "INDIRECT_RPT_CODE_LV2")
	private String indirectRptCodeLv2;
	
	@Column(name = "INDIRECT_RPT_NAME_LV2")
	private String indirectRptNameLv2;
	
	@Transient
	private String indirectRptPositionLv2;
	
	@Column(name = "INDIRECT_RPT_CODE_LV3")
	private String indirectRptCodeLv3;
	
	@Column(name = "INDIRECT_RPT_NAME_LV3")
	private String indirectRptNameLv3;
	
	@Transient
	private String indirectRptPositionLv3;
	
	@Column(name = "INDIRECT_RPT_CODE_LV4")
	private String indirectRptCodeLv4;
	
	@Column(name = "INDIRECT_RPT_NAME_LV4")
	private String indirectRptNameLv4;
	
	@Transient
	private String indirectRptPositionLv4;
	
	@Column(name = "INDIRECT_RPT_CODE_LV5")
	private String indirectRptCodeLv5;
	
	@Column(name = "INDIRECT_RPT_NAME_LV5")
	private String indirectRptNameLv5;
	
	@Transient
	private String indirectRptPositionLv5;
	
	@Column(name = "SM_CODE")
	private String smCode;
	
	@Column(name = "SM_NAME")
	private String smName;
	
	@Column(name = "DD_CODE")
	private String ddCode;
	
	@Column(name = "DD_NAME")
	private String ddName;
	
	@Column(name = "DIRECT_AD_CODE")
	private String directAdCode;
	
	@Column(name = "DIRECT_AD_NAME")
	private String directAdName;
	
	@Transient
	private String directAdPosition;
	
	@Column(name = "DIRECT_AREA_CODE")
	private String directAreaCode;
	
	@Column(name = "INDIRECT_AD_CODE_LV1")
	private String indirectAdCodeLv1;
	
	@Column(name = "INDIRECT_AD_NAME_LV1")
	private String indirectAdNameLv1;
	
	@Column(name = "INDIRECT_AREA_CODE_LV1")
	private String indirectAreaCodeLv1;
	
	@Column(name = "INDIRECT_AD_CODE_LV2")
	private String indirectAdCodeLv2;
	
	@Column(name = "INDIRECT_AD_NAME_LV2")
	private String indirectAdNameLv2;
	
	@Column(name = "INDIRECT_AREA_CODE_LV2")
	private String indirectAreaCodeLv2;
	
	@Column(name = "INDIRECT_AD_CODE_LV3")
	private String indirectAdCodeLv3;
	
	@Column(name = "INDIRECT_AD_NAME_LV3")
	private String indirectAdNameLv3;
	
	@Column(name = "INDIRECT_AREA_CODE_LV3")
	private String indirectAreaCodeLv3;
	
	@Column(name = "DIRECT_RD_CODE")
	private String directRdCode;
	
	@Column(name = "DIRECT_RD_NAME")
	private String directRdName;
	
	@Transient
	private String directRdPosition;
	
	@Column(name = "DIRECT_REGION_RD_CODE")
	private String directRegionRdCode;
	
	@Column(name = "INDIRECT_RD_CODE_LV1")
	private String indirectRdCodeLv1;
	
	@Column(name = "INDIRECT_RD_NAME_LV1")
	private String indirectRdNameLv1;
	
	@Column(name = "INDIRECT_REGION_CODE_LV1")
	private String indirectRegionCodeLv1;
	
	@Column(name = "INDIRECT_RD_CODE_LV2")
	private String indirectRdCodeLv2;
	
	@Column(name = "INDIRECT_RD_NAME_LV2")
	private String indirectRdNameLv2;
	
	@Column(name = "INDIRECT_REGION_CODE_LV2")
	private String indirectRegionCodeLv2;
	
	@Column(name = "INDIRECT_RD_CODE_LV3")
	private String indirectRdCodeLv3;
	
	@Column(name = "INDIRECT_RD_NAME_LV3")
	private String indirectRdNameLv3;
	
	@Column(name = "INDIRECT_REGION_CODE_LV3")
	private String indirectRegionCodeLv3;
	
	@Column(name = "AREA_MANAGER_CODE")
	private String areaManagerCode;
	
	@Column(name = "AREA_MANAGER_NAME")
	private String areaManagerName;
	
	@Column(name = "BP_OFFICE_CODE")
	private String bpOfficeCode;
	
	@Column(name = "BP_OFFICE_NAME")
	private String bpOfficeName;
	
	@Column(name = "BP_FOCAL_CODE")
	private String bpFocalCode;
	
	@Column(name = "BP_FOCAL_NAME")
	private String bpFocalName;
	
	@Column(name = "DOA_NAME")
	private String doaName;
	
	@Column(name = "REGION_CODE")
	private String regionCode;
	
	@Column(name = "OLD_ID_NUMBER")
	private String oldIdNumber;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "TELEPHONE")
	private String telephone;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "EMAIL_COMPANY")
	private String emailCompany;
	
	@Column(name = "NOTE")
	private String note;
	
	@Transient
	private Long userId;
	
	@Transient
	private String areaManagerPosition;
	
	@Transient
	private String adPlusCode;
	
	@Transient
	private String adPlusName;
	
	@Transient
	private String adPlusPosition;
	
	
}
