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

@Getter
@Setter
@Table(name = "M_CONTEST_SUMMARY_IMPORT")
public class ContestSummaryImport {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTEST_SUMMARY_IMPORT")
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
    
    private String isHot;
    
    private String isChallenge;
    
    private String enabled;
    
    private String isOds;
    
    private Date startDate;
    
    private Date endDate;
    
    private Date effectiveDate;
    
    private Date expiredDate;
    
    private String applicableObject;
        
    private String note;
    
    private Date createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;
    
    private String agentName;
    
    private String area;
    
    private String territory;
    
    private String region;
    
    private String office;
    
    private String position;
    
    private String isPerson;
    
    private String reportingtoName;

}
