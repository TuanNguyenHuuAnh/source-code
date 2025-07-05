package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractBusinessHandleDto {
	private Integer no;
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String policyNo;//so hd
	private String totalContract;//tong so hd
	private String insuranceBuyer;//ben mua bao hiem
	private String requestAdjustment;// yeu cau dieu chinh
	private String status;// trang thai
	private String waitingAddInformation;//thong tin cho bo sung
	private Date requestDate;// ngay yeu cau
	private Date startDate;// ngay thuc hien
	private Date addExpirationDate;// ngay het han bo sung
	private String parentAll;
	private String agentAll;
	private String keyGroup;

	private String lv1Agentcode;
	private String lv1Agenttype;
	private String lv1Agentname;

	private String lv2Agentcode;
	private String lv2Agenttype;
	private String lv2Agentname;

	private String lv3Agentcode;
	private String lv3Agenttype;
	private String lv3Agentname;
}
