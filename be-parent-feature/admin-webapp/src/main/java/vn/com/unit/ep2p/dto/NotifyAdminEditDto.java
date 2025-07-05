package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotifyAdminEditDto {
	
	private Long id;
	
	private String notifyCode;
	
	private String notifyTitle;
	
	private String linkNotify;
	
	private String contents;
	
	private Date sendDate;
	
	private boolean isSendImmediately;
	
	private boolean isActive;
	
	private String applicableObject;
	
	private boolean isFc;

	private String territory;
	private String region;
	private String area;
	private String type;
	private String reporter;
	private String office;
	private Boolean isError;
	private String sessionKey;
	private String createBy;
	private Date createDate;

}
