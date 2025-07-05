package vn.com.unit.cms.core.module.notify.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Getter
@Setter
public class NotifyDto{
	
	private int no;

    private Long id;
	
	private String notifyCode;
	
    private String notifyTitle;
    
    private String contents;
    
    private String linkNotify;
    
    private boolean isSendImmediately;
    
    private boolean isActive;
    
    private String applicableObject;
    
    private Date sendDate;
    
    private boolean isSend;
    
    private String territorry;
    
    private String area;
    
    private String region;
    
    private String office;
    
    private String position;
    
    private boolean isFc;
    private int notifyType;
    private int isReadAlready;
    private int isLike;
}
