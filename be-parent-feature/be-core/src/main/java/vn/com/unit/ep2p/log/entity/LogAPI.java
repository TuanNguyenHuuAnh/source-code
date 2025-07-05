package vn.com.unit.ep2p.log.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "M_LOG_API")
public class LogAPI {

    @Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_LOG_API")
    private Long id;
    
    @Column(name = "DEVICE")
    private String device;

    @Column(name = "CREATED_DATE")
    private Date createdDate;
   
    @Column(name = "REQUEST_JSON")
    private String requestJson;
    
    @Column(name = "TOTAL_ACTION_TIME")
    private Long tats;
        
    @Column(name = "MESSAGE")
    private String message;
    
    @Column(name = "LOG_LEVEL")
    private String logLevel;
    
    @Column(name = "SOURCE_IP")
    private String sourceIp;
    
    @Column(name = "USER_AGENT")
    private String userAgent;
    
    @Column(name = "USER_ACCOUNT")
    private String userAccount;
    
    @Column(name = "EVENT_NAME")
    private String eventName;
    
    @Column(name = "OUTCOME")
    private String outcome;
    
    @Column(name = "SYSTEM")
    private String system;
    
    @Column(name = "URL_PATH")
    private String urlPath;
    
    @Column(name = "ERROR_CODE")
    private String errorCode;
    
    @Column(name = "REQ_PARAMS")
    private String reqParams;
}
