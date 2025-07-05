package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PersonalInsuranceDocumentSearchDto extends CommonSearchWithPagingDto{

	private String agentName;			//	ten TVTC
	private String agentCode;			//	agentCode
	private String agentType;			//	chuc vu
	private String docNoString;
	private String fyp;					//	phi BH nam nhat
	private String policyOwner;			//	chu HDBH
	private String ip;					//	phi BH ki dau tien
	private String status;				//	trang thai
	private Object docReceivedDate;		//	ngay nhan HSYCBH
	private Date signDate;				//	ngay ki HSYCBH
	private String insuredPerson;		//	nguoi duoc BH chinh
	private Object insuranceBuyer;		//	ben mua BH
	private Object policyNo;			//	so HD
	private Object docNo;				//	so HSYCBH
	private Object ackStatus;			//	trang thai ACK
	private Date ackSendDate;			//	ngay nop ACK
	private String reason;				//	li do
	private String type;				// 1: da nop, 2: bo sung, 3: da phat hanh, 4: bi tu choi
	private String manager;
	private String agent;
	private String orgName;
	private String agentGroup;
	private Date mtdDate;
	private String countMode;
}
