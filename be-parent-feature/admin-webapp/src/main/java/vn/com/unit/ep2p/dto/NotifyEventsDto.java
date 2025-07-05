package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotifyEventsDto {
	
	private Long eventId;
	private String eventCode;
	private String eventTitle;
	private Date eventDate;
	private String contents;
	private String linkNotify;
	private String type;	//1:nhắc lần 1, 2:nhắc lần 2
}
