package vn.com.unit.cms.core.module.events.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsDetailDto {
	private String id;
	private String eventCode;
	private String eventTitle;
	private String eventLocation;
	private Date eventDate;
	private Date endDate;
	private String status;
	private BigDecimal quantity;
	private boolean ableDelete;
	private BigDecimal attendanceQuantity;
	private Double attendanceRatio;
	private Integer no;
	private String groupEventName;
	private String activityEventName;
	private String linkNotify;
	private String notes;
	private Date createDate;
	private Date updateDate;
}
