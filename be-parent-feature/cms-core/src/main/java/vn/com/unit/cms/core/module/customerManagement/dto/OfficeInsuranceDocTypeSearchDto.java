package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

import java.util.Date;

@Getter
@Setter
public class OfficeInsuranceDocTypeSearchDto extends CommonSearchWithPagingDto{

	private String agentName;
	private String agentCode;
	private String agentType;
	private String managerAgentType;
	private String managerAgentCode;
	private String managerAgentName;
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
	private String ape;							// phi phat hang


}
