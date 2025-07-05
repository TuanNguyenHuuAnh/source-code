package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentDto;

import java.util.Date;

@Getter
@Setter
public class OfficeInsuranceDetailDto {
	private Integer no;
	private String docno;				    // so HSYCBH
	private String policyno;				// so HD
	private String insurancebuyer;			// ben mua BH
	private String insuredperson;			// ben duoc nhan BH
	private Date signdate;
	private Date docreceiveddate;
	private String status;
	private String ip;
	private String fyp;
	private String reason;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String lv2Agentcode;
	private String lv2Agentname;
	private String lv2Agenttype;
	private String manager;						// QUAN LY
	private String lv3Agentcode;
	private String lv3Agentname;
	private String lv3Agenttype;
	private String agent;						// TVTC
	private Integer totalProposal;				//SO HS

	private String planId;						// name product
	private String cvgBasicPremAmt;          // PHI BH THEO KY
	private String cvgFaceAmt;			    // Số tiền BH
	private Date cvgIssEffDt;				// Ngày hiệu lực
	private Date cvgMatXpryDt;				// Ngày đáo hạn
	private String orgCode;
	private String orgId;

}
