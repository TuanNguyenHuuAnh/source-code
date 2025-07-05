package vn.com.unit.cms.core.module.notify.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "M_NOTIFYS_APPLICABLE_DETAIL")
public class NotifysApplicableDetail {
	@Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.IDENTITY)
    private Long id;
	
	/** Column: NOTIFY_ID type BIGINT */
	@Column(name = "NOTIFY_ID")
	private Long notifyId;
	
	/** Column: TERRITORRY type NVARCHAR(32) */
    @Column(name = "TERRITORRY")
    private String territorry;
    
    /** Column: TERRITORRY type NVARCHAR(32) */
    @Column(name = "AREA")
    private String area;
    
    /** Column: REGION type NVARCHAR(32) */
    @Column(name = "REGION")
    private String region;
    
    /** Column: OFFICE type NVARCHAR(32) */
    @Column(name = "OFFICE")
    private String office;
    
    /** Column: POSITION type NVARCHAR(32) */
    @Column(name = "POSITION")
    private String position;
	
	/** Column: AGENT_CODE type BIGINT */
	@Column(name = "AGENT_CODE")
	private Long agentCode;
	
    
    /** Column: DATE_READ_ALREADY type SMALLDATEIME*/
    @Column(name = "DATE_READ_ALREADY")
    private Date dateReadAlready;
    
    /** Column: IS_READ_ALREADY type BIT*/
    @Column(name = "IS_READ_ALREADY")
    private boolean isReadAlready;
    
    @Column(name = "AGENT_NAME")
    private String agentName;
    
    @Column(name = "MESSAGE_SYSTEM")
    private String messageSystem;
    
    @Column(name = "IS_LIKE")
    private boolean isLike;
    
    @Column(name = "CREATE_DATE")
    private Date createDate;
}
