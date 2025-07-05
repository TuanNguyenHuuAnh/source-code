package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "ERS_COP")
public class CopManagement {

	@Id
	@PrimaryKey(generationType =GenerationType.SEQUENCE, generator = "SEQ_ERS_COP")
	@Column(name = "ID")
	private Long id;

	@Column(name="STUDY_DATE")
	private Date studyDate;

	@Column(name="CANDIDATE_NAME")
	private String candidateName;

	@Column(name="ID_NO")
	private String idNo;

	@Column(name="MOBILE")
	private String mobile;

	@Column(name="RECRUITER_CODE_ID")
	private String recruiterCodeId;

	@Column(name="RECRUITER_NAME")
	private String recruiterName;

	@Column(name="MANAGER_CODE_ID")
	private String managerCodeId;

	@Column(name="MANAGER_NAME")
	private String managerName;

	@Column(name="AD_NAME")
	private String adName;

	@Column(name="RD_NAME")
	private String rdName;

	@Column(name="REGION_NAME")
	private String regionName;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	@Column(name="DELETED_BY")
	private String deletedBy;

	@Column(name="DELETED_DATE")
	private Date deletedDate;
	
	@Transient
	private Byte[] versionData;
}
