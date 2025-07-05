package vn.com.unit.ep2p.admin.entity;
import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Table(name="M_CONFIRM_DECREE")
public class ConfirmDecree {
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONFIRM_DECREE")
	private Long id;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "AGENT_CODE")
	private Long agentCode;
	
	@Column(name = "AGENT_NAME")
	private String agentName;
	
	@Column(name = "AGENT_TYPE")
	private String agentType;
	
	@Column(name = "CONFIRM_TIME")
	private Date confirmTime;
	
	@Column(name = "FUNCTION_TYPE")
	private String functionType;
	
	@Column(name = "NEW_AGENT_NAME")
	private String newAgentName;
	
	@Column(name = "NEW_ID_NUMBER")
	private String newIdNumber;
	
	@Column(name = "NEW_DATE_OF_ISSUE")
	private Date newDateOfIssue;

	@Column(name = "NEW_PLACE_OF_ISSUE")
	private String newPlaceOfIssue;

	@Column(name = "NEW_DATE_OF_BIRTH")
	private Date newDateOfBirth;
	
	@Column(name = "NEW_MOBILE_PHONE")
	private String newMobilePhone;
	
	@Column(name = "NEW_EMAIL")
	private String newEmail;

	@Column(name = "NEW_ADDRESS")
	private String newAddress;
	
	@Column(name = "NEW_ZIPCODE")
	private String newZipCode;

	@Column(name = "NEW_TAX_CODE")
	private String newTaxCode;
	
	@Column(name = "NEW_BUSINESS_HOUSEHOLDS")
	private String newBussinessHouseHolds;
	
	@Column(name = "NEW_TAX_CODE_INB")
	private String newTaxCodeInb;
}

