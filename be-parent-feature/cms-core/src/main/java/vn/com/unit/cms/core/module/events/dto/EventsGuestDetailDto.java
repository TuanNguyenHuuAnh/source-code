package vn.com.unit.cms.core.module.events.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsGuestDetailDto {
	private Long eventId;
	private String eventCode;
	private String eventType;
	private String idNumber;
	private String name;
	private String gender;
	private String tel;
	private String email;
	private String address;
	private String agentCode;
	private String referCode;
	private String guestType;
	private Date attendanceTime;
    private String territorry;
    private String area;
    private String region;
    private String office;
    private String position;
    private Integer no;
    private String gad;
    private String bdth;
    private String bdah;
    private String bdrh;
    private String bdoh;
}
