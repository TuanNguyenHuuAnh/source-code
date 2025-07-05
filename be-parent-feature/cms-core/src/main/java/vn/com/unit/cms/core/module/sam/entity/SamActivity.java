package vn.com.unit.cms.core.module.sam.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;

@Table(name = CmsTableConstant.TABLE_SAM_ACTIVITY)
@Getter
@Setter
public class SamActivity {

	@Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_SAM_ACTIVITY)
    private Long id;
	
	@Column(name = "ACT_CODE")
	private String actCode;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "FORM")
	private String form;
	
	@Column(name = "PARTICIPANTS")
	private String participants;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "PARTNER")
	private String partner;
	
	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "APPROVED_DATE")
	private Date approvedDate;

	@Column(name = "REPORTED_DATE")
	private Date reportedDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "CANCEL_REASON")
	private String cancelReason;
	
	@Column(name = "CANCEL_DATE")
	private Date cancelDate;
}
