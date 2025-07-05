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

@Table(name = CmsTableConstant.TABLE_SAM_APPROVAL_HISTORY)
@Getter
@Setter
public class SamApprovalHistory {

	@Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_SAM_APPROVAL_HISTORY)
    private Long id;
	
	@Column(name = "ACTIVITY_ID")
	private Long activityId;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;
	
	@Column(name = "OLD_STATUS_ID")
	private Long oldStatusId;
	
	@Column(name = "OLD_STATUS")
	private String oldStatus;

	@Column(name = "NEW_STATUS_ID")
	private Long newStatusId;
	
	@Column(name = "NEW_STATUS")
	private String newStatus;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

}
