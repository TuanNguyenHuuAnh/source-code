package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

//import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="ERS_STUDY_ATT_RESULT_IMPORT")
public class ErsStudyAttResultImport {
	
	@Id
	@PrimaryKey(generationType =GenerationType.SEQUENCE, generator = "SEQ_ERS_STUDY_ATT_RESULT_IMPORT")
	@Column(name = "ID")
	private Long id;	
	
	@Column(name = "NAME")
	private String name;

    @Column(name = "ID_NUMBER")
    private String idNumber;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "CLASS_CODE")
    private String classCode;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SALES_CHANNEL")
    private String salesChannel;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "PROVIDE_CODE")
    private String provideCode;

    @Column(name = "CERTI_NUMBER")
    private String certiNumber;

    @Column(name = "DECISION_NUMBER")
    private String decisionNumber;

    @Column(name = "DECISION_DATE")
    private String decisionDate;

    @Column(name = "COMPLETE_MBSU")
    private String completeMBSU;

    @Column(name = "COMPLETE_MBFS")
    private String completeMBFS;

    @Column(name = "COMPLETE_ICOB")
    private String completeICOB;

    @Column(name = "RESULT")
    private String result;

    @Column(name = "NOTE")
    private String note;
    
    @Column(name = "SESSION_KEY")
    private String sessionKey;

    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="CREATED_DATE")
    private Date createdDate;
    
	@Transient
	private Byte[] versionData;
	
}
