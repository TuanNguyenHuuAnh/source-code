package vn.com.unit.ep2p.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErsSyncBpDto {
	private Long id;
	private BigDecimal bpNumber;
	private String bpTitleEn;
	private String bpTitleVi;
	private String firstName;
	private String middleName;
	private String lastName;
	private String fullName;
	private Date dateOfBirth;
	private String birthPlace;
	private int genderKey;
	private String genderExplanatory;
	private String building;
	private String roomNumber;
	private String floorInBuilding;
	private String houseNumber;
	private String street;
	private String street2;
	private String street3;
	private String street4;
	private String ward;
	private String district;
	private String differentCity;
	private BigDecimal cityPostalCode;
	private String city;
	private String region;
	private String countryKey;
	private String timeZone;
	private String telephoneNo;
	private String telephoneExtension;
	private String mobileNo;
	private String faxNumber;
	private String faxExtention;
	private String emailAddress;
	private int maritalStatus;
	private String occupationalClass;
	private String nationality;
	private String annualIncome;
	private String monthlyAvgIncome;
	private String annualIncomeCurrencyKey;
	private String identificationType;
	private String identificationNumber;
	private String bankDetailsId;
	private String bankCountryKey;
	private String bankKey;
	private String bankAccountNumber;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String deletedBy;
	private Date deletedDate;
	
}
