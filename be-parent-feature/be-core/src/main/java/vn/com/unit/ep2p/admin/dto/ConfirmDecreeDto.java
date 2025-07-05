package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmDecreeDto {
	private String userName;
	private String idNumber;
	private Long agentCode; 
	private String agentName; 
	private String agentType;
	private Date confirmTime;
	private String function;
	private String newAgentName;
	private String newIdNumber;
	private Date newDateOfIssue;
	private String newPlaceOfIssue;
	private Date newDateOfBirth;
	private String newMobilePhone;
	private String newEmail;
	private String newAddress;
	private String newZipCode;
	private String newTaxCode;
	private String newBussinessHouseHolds;
	private String newTaxCodeInb; 

}
