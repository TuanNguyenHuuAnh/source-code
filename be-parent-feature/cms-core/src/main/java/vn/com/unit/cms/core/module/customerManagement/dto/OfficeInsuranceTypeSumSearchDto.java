package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

import java.util.Date;

@Getter
@Setter
public class OfficeInsuranceTypeSumSearchDto extends CommonSearchWithPagingDto{

	private String agentName;
	private String agentCode;
	private String agentType;
	private String agentGroup;

	private String managerAgentType;
	private String managerAgentCode;
	private String managerAgentName;

	private String officeName;
	private String officeCode;

	private String type;

	private String policyKey;					//so HD
	private String proposalCode;				//so HSYCBH
	private String insuranceBuyer;				// ben mua bao hiem
	private String insuredPerson;				// nguoi dc bao hiem
	private String divisionRatio;				// ti le chia
	private String recurringFeePayment;			// dinh ky dong phi
	private Date effectiveDate;				    // ngay hieu luc
	private String planName;					// ten sp
	private String polMpremAmt;					// so tien bh

	private Date cancelDate;					// ngay huy
	private String cancelReason;				// ly do huy
	private String apeCancel;					// phi huy
	private Object manager;
	private Object agent;
	private String orgId;

	private String orgName;
	private String dateKey;


}
