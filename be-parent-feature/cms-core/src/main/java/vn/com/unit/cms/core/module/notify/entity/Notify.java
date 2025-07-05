package vn.com.unit.cms.core.module.notify.entity;

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
@Table(name = "M_NOTIFYS")
public class Notify extends AbstractTracking{
	@Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_NOTIFYS")
    private Long id;
	
	 /** Column: NOTIFY_CODE type VARCHAR(20) */
	@Column(name = "NOTIFY_CODE")
	private String notifyCode;
	
	/** Column: NOTIFY_TITLE type VARCHAR(256) */
    @Column(name = "NOTIFY_TITLE")
    private String notifyTitle;
    
    /** Column: CONTENTS type NVARCHAR(1024) */
    @Column(name = "CONTENTS")
    private String contents;
    
    /** Column: LINK_NOTIFY type NVARCHAR(256) */
    @Column(name = "LINK_NOTIFY")
    private String linkNotify;
    
    /** Column: IS_SEND_IMMEDIATELY type BIT */
    @Column(name = "IS_SEND_IMMEDIATELY")
    private boolean isSendImmediately;
    
    /** Column: IS_ACTIVE type BIT */
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    
    /** Column: APPLICABLE_OBJECT type CHAR(3) */
    @Column(name = "APPLICABLE_OBJECT")
    private String applicableObject;
    
    /** Column: SEND_DATE type DATE */
    @Column(name = "SEND_DATE")
    private Date sendDate;
    
    /** Column: IS_SEND type BIT */
    @Column(name = "IS_SEND")
    private boolean isSend;
    
    /** Column: TERRITORRY type NVARCHAR(1024) */
    @Column(name = "TERRITORRY")
    private String territorry;
    
    /** Column: TERRITORRY type NVARCHAR(1024) */
    @Column(name = "AREA")
    private String area;
    
    /** Column: REGION type NVARCHAR(1024) */
    @Column(name = "REGION")
    private String region;
    
    /** Column: OFFICE type NVARCHAR(1024) */
    @Column(name = "OFFICE")
    private String office;
    
    /** Column: POSITION type NVARCHAR(1024) */
    @Column(name = "POSITION")
    private String position;
    
    /** Column: IS_FC type BIT */
    @Column(name = "IS_FC")
    private boolean isFc;
    
    @Column(name = "NOTIFY_TYPE")
    private int notifyType;

    @Column(name = "SAVE_DETAIL")
    private boolean saveDetail;
}
