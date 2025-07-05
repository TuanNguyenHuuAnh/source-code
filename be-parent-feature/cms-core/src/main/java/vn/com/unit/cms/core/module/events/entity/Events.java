package vn.com.unit.cms.core.module.events.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;

@Table(name = CmsTableConstant.TABLE_EVENTS)
@Getter
@Setter
public class Events {

	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_TABLE_EVENTS)
    private Long id;
	
	@Column(name = "EVENT_CODE")
	private String eventCode;
	
	@Column(name = "EVENT_TITLE")
	private String eventTitle;
	
	@Column(name = "CONTENTS")
	private String contents;
	
	@Column(name = "LINK_NOTIFY")
	private String linkNotify;
	
	@Column(name = "EVENT_DATE")
	private Date eventDate;
	
	@Column(name = "EVENT_LOCATION")
	private String eventLocation;
	
	@Column(name = "NOTES")
	private String notes;
	
	@Column(name = "APPLICABLE_OBJECT")
	private String applicableObject;
	
	@Column(name = "TERRITORRY")
	private String territorry;
	
	@Column(name = "AREA")
	private String area;
	
	@Column(name = "REGION")
	private String region;
	
	@Column(name = "OFFICE")
	private String office;
	
	@Column(name = "POSITION")
	private String position;

	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@Column(name = "UPDATE_BY")
	private String updateBy;
	
	@Column(name = "DELETE_DATE")
	private Date deleteDate;
	
	@Column(name = "DELETE_BY")
	private String deleteBy;

	@Column(name = "SAVE_DETAIL")
	private boolean saveDetail;
	
	@Column(name = "EVENT_TYPE")
	private String eventType;
	
	@Column(name = "GROUP_EVENT_CODE")
	private String groupEventCode;
	
	@Column(name = "ACTIVITY_EVENT_CODE")
	private String activityEventCode;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "QR_CODE")
	private String qrCode;
	
	@Column(name = "QR_ID")
	private String qrId;
	
	@Column(name = "DEL_FLG")
	private boolean delFlg;
	
	@Column(name = "PROCESSING_FLG")
	private boolean processingFlg;
}
