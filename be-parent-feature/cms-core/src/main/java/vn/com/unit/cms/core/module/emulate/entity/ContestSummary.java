package vn.com.unit.cms.core.module.emulate.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Getter
@Setter
@Table(name = "M_CONTEST_SUMMARY")
public class ContestSummary {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTEST_SUMMARY")
	private Long id;

	private String memoNo;

	private String code;

	private String contestName;

	private String keywordsSeo;

	private String keywords;

	private String keywordsDesc;

	private String description;

	private String contestType;

	private String contestPhysicalImt;

	private String contestPhysicalFile;

	private boolean isHot;

	private boolean isChallenge;

	private boolean enabled;

	private boolean isOds;

	private Date startDate;

	private Date endDate;

	private Date effectiveDate;

	private Date expiredDate;

	private String applicableObject;

	private String territory;

	private String area;

	private String region;

	private String office;

	private String note;

	private String reportingtoCode;

	private String position;

	private Date createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;

	private Date deletedDate;

	private String deletedBy;
	
	private Boolean saveDetail;
	
	private String agentCode;


}
