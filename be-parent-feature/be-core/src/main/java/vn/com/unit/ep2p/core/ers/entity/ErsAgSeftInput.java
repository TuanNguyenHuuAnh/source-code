package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name="ERS_AG_SEFT_INPUT")
public class ErsAgSeftInput extends AbstractTracking {
	
	@Id
	@Column(name="ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_AG_SEFT_INPUT")
	private Long id;
	
	@Column(name="CHANNEL")
	private String channel;
	
	@Column(name="APPLY_FOR_POSITION")
	private String applyForPosition; 
	
	@Column(name= "POSITION_NAME")
	private String positionName;
	
	@Column(name="CANDIDATE_NAME")
	private String candidateName;

	@Column(name="GENDER")
	private String gender;

	@Column(name="GENDER_NAME")
	private String genderName; 

	@Column(name="DOB")
	private Date dob;

	@Column(name="ID_NO")
	private String idNo;
	
	@Column(name="EMAIL")
	private String email;

	@Column(name="MOBILE")
	private String mobile;

	@Column(name="CURRENT_ADDRESS")
	private String currentAddress;

	@Column(name="CURRENT_NEST")
	private String currentNest;
	
	@Column(name="CURRENT_PROVINCE")
	private String currentProvince;
	
	@Column(name="CURRENT_PROVINCE_NAME")
	private String currentProvinceName;
	
	@Column(name="CURRENT_DISTRICT")
	private String currentDistrict;
	
	@Column(name="CURRENT_DISTRICT_NAME")
	private String currentDistrictName;
	
	@Column(name="CURRENT_WARD")
	private String currentWard; 

	@Column(name="CURRENT_WARD_NAME")
	private String currentWardName;
	
	@Column(name="AD_CODE")
	private Long adCode;
	
	@Column(name="AD_NAME")
	private String adName;
	
	@Column(name="ALLOCATION_AD_DATE")
	private Date allocationAdDate; 
	
	@Column(name="RECRUITER_CODE")
	private Long recruiterCode; 
	
	@Column(name="RECRUITER_NAME")
	private String recruiterName;
	
	@Column(name="ALLOCATION_RECRUITER_DATE")
	private Date allocationRecruiterDate;
	
	@Column(name="AR_ALLOCATION_NAME")
	private String arAllocationName;
	
	@Column(name="STATUS_FORM")
	private String statusForm;
	
	@Transient
	private Integer no;
	
	@Transient 
	private Date createdDate;
	
	@Transient
	private String currentFullAddress;
}
