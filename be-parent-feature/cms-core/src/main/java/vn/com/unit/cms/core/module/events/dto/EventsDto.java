package vn.com.unit.cms.core.module.events.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsDto {
	private String no;
	private Long id;
	
	private String eventCode;
	
	private String eventTitle;
	
	private String contents;
	
	private String linkNotify;
	
	private Date eventDate;
	private Date eventDateTime;
	
	private String eventLocation;
	
	private String note;
	
	private String applicableObject;
	
	private String territorry;
	
	private String area;
	
	private String region;
	
	private String office;
	
	private String position;
	
	private Integer isFc;
	
	private String sessionKey;
	private String createBy;
	
	private String eventType;
	private String groupEventCode;
	private String groupEventName;
	private String activityEventCode;
	private String activityEventName;
	private Date endDate;
	private String qrCode;
	private String qrId;
	private String updateBy;
	private String status;
	private BigDecimal quantity;
	private boolean ableDelete;
	private BigDecimal attendanceQuantity;
	private Double attendanceRatio;
	private String createName;
	private Date createDate;
	private Date updateDate;
	private String actionType;
	private String qrCodeData;
}
