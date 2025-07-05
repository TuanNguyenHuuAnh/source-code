package vn.com.unit.cms.core.module.contactHistory.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "M_CONTACT_HISTORY")
@Getter
@Setter
public class ContactHistory {

	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTACT_HISTORY")
    private Long id;
	
	@Column(name = "POLICY_NO")
	private String policyNo;
	
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	@Column(name = "CONTACT_STAGE")
	private String contactStage;
	
	@Column(name = "CONTACT_STAGE_DAYS")
	private Integer contactStageDays;
	
	@Column(name = "CONTACT_METHOD")
	private String contactMethod;
	
	@Column(name = "CONTACT_RESULT")
	private String contactResult;
	
	@Column(name = "CONTACT_DATE")
	private Date contactDate;
	
	@Column(name = "NOTES")
	private String notes;

	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
}
