package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name="ERS_CLASS_INFO")
public class ErsClassInfo extends AbstractTracking{
	
	@Id
	@PrimaryKey(generationType =GenerationType.SEQUENCE, generator = "SEQ_ERS_CLASS_INFO")
	@Column(name = "ID")
	private Long id;	
	
	@Column(name="CLASS_TYPE")
    private String classType;
    
    @Column(name="CLASS_CODE")
    private String classCode;
    
    @Column(name="ONLINE_OFFLINE")
    private String onlineOffline;
    
    @Column(name="PROVINCE")
    private String province;
    
    @Column(name="START_DATE")
    private Date startDate;
    
    @Column(name="END_DATE")
    private Date endDate;
    
    @Column(name="EXAM_DATE")
    private Date examDate;
    
    @Column(name="CHANNEL")
    private String channel;
    
//    @Column(name="CREATED_BY")
//    private String createdBy;
//    
//    @Column(name="CREATED_DATE")
//    private Date createdDate;
//    
//    @Column(name="UPDATED_BY")
//    private String updatedBy;
//    
//    @Column(name="UPDATED_DATE")
//    private Date updatedDate;
//    
//    @Column(name="DELETED_FLAG")
//    private int deletedFlag;
//    
//    @Column(name="DELETED_DATE")
//    private Date deletedDate;
//    
//    @Column(name="DELETED_BY")
//    private String deletedBy;
    
	@Transient
	private Byte[] versionData;
	
}
