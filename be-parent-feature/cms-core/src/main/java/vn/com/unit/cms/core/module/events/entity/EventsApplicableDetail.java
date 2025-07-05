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

@Table(name = CmsTableConstant.TABLE_EVENTS_APPLICABLE_DETAIL)
@Getter
@Setter
public class EventsApplicableDetail {

	@Id
    @Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_TABLE_EVENTS_APPLICABLE_DETAIL)
    private Long id;
	
	@Column(name = "EVENT_ID")
	private Long eventId;
	
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
	
	@Column(name = "AGENT_CODE")
	private Long agentCode;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "TEL")
	private String tel;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "REFER_CODE")
	private Long referCode;
	
	@Column(name = "GUEST_TYPE")
	private String guestType;
	
	@Column(name = "ATTENDANCE_TIME")
	private Date attendanceTime;
}
