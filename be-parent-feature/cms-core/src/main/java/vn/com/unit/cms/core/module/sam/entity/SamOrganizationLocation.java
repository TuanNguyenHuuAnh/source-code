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

@Table(name = CmsTableConstant.TABLE_SAM_ORGANIZATION_LOCATION)
@Getter
@Setter
public class SamOrganizationLocation {

	@Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_SAM_ORGANIZATION_LOCATION)
    private Long id;
	
	@Column(name = "ZONE")
	private String zone;
	
	@Column(name = "REGIONAL")
	private String regional;
	
	@Column(name = "AREA")
	private String area;
	
	@Column(name = "BU")
	private String bu;

	@Column(name = "BU_CODE")
	private String buCode;

	@Column(name = "BU_COUNT")
	private Integer buCount;

	@Column(name = "ACTIVITY_ID")
	private Long activityId;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "ZONE_CODE")
	private String zoneCode;
}
