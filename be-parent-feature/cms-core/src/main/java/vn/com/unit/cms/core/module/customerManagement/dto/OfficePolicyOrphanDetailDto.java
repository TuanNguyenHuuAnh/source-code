package vn.com.unit.cms.core.module.customerManagement.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficePolicyOrphanDetailDto {
	private String policyNo;
	private String insuranceBuyer;
	private String insuredPerson;
	private String address;
	private String phoneNumber;
	private Date paymentPeriod;//dinh ky dong phi
	private Date terminatedDate;//ngay cham dut
	private String servicingAgentCode;//tvtc phuc vu
	private String servicingAgentName;
	private String servicingAgentType;
	private String newServicingAgentCode;//tvtc phuc vu moi
	private String newServicingAgentName;
	private String no;
}
