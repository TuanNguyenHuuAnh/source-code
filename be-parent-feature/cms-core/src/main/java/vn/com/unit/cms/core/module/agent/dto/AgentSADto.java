package vn.com.unit.cms.core.module.agent.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentSADto {
	private Integer no;
	private String agentCodeBdth;
	private String nameBdth;
	private String typeBdth;
	private String agentCodeBdah;
	private String nameBdah;
	private String agentCodeBdrh ;
	private String nameBdrh;
	private String agentCodeBdoh;
	private String nameBdoh;
	private String managerType;//lv2
	private String managerCode;
	private String lv2Agenttype;
	private String managerName;
	private String managerPosition;
	private String headOfDepartmentCode;//lv1
	private String headOfDepartmentName;
	private String headOfDepartmentType;
	private String headOfDepartmentPosition;
	private Date reportToDate ;
	private String territoryCode ;
	private String territoryName;
	private String areaCode;
	private String areaName;
	private String regionCode;
	private String regionName;
	private String officeCode;
	private String officeName;
	private String tvtcType;//lv3
	private String tvtcCode;
	private String tvtcName;
	private String tvtcPosition;
	private String idCard;
	private String phoneNum;
	private Date startDate;
	private String address;
	private BigDecimal contractNumber;
	private BigDecimal monthInactiveNumber;
	private String gradeName;
	private String gad;
	private String temporaryAddress;
	private String permanentAddress;
	//export
	private String bdth;
	private String bdah;
	private String bdrh;
	private String office;
	private String bdoh;
	private String headOfDepartment;
	private String manager;
	private String tvtc;
	private String gadType;
	private String gadCode;
	private String gadName;
}
