/*******************************************************************************
 * Class        ：QrtzMJob
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/

package vn.com.unit.quartz.job.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;


/**
 * <p>
 * QrtzMJob
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Setter
@Getter
@Table(name = "QRTZ_M_JOB")
public class QrtzMJob extends AbstractTracking{

    /** Column: ID type NUMBER(22,0) NOT NULL */
    @Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_QRTZ_M_JOB")
    private Long id;

    /** Column: JOB_CODE type VARCHAR2(255,0) NULL */
    @Column(name = "JOB_CODE")
    private String jobCode;

    /** Column: JOB_NAME type VARCHAR2(255,0) NULL */
    @Column(name = "JOB_NAME")
    private String jobName;

    /** Column: JOB_GROUP type VARCHAR2(255,0) NULL */
    @Column(name = "JOB_GROUP")
    private String jobGroup;

    /** Column: JOB_CLASS_NAME type VARCHAR2(255,0) NULL */
    @Column(name = "JOB_CLASS_NAME")
    private String jobClassName;

    /** Column: DESCRIPTION type NVARCHAR2(510,0) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: VALIDFLAG type NUMBER(22,0) NULL */
    @Column(name = "VALIDFLAG")
    private Long validflag;

    /** Column: JOB_TYPE type NUMBER(20,0) NULL */
    @Column(name = "JOB_TYPE")
    private Long jobType;

    /** Column: SEND_NOTIFICATION type NUMBER(22,0) NULL */
    @Column(name = "SEND_NOTIFICATION")
    private Boolean sendNotification;
    
    /** Column: SEND_STATUS type VARCHAR2(10,0) NULL */
    private String sendStatus;

    /** Column: EMAIL_TEMPLATE type NUMBER(20, 0) NULL */
    @Column(name = "EMAIL_TEMPLATE")
    private Long emailTemplate;

    /** Column: TEMPLATE_FILE_NAME type VARCHAR2(255,0) NULL */
    @Column(name = "TEMPLATE_FILE_NAME")
    private String templateFileName;

    /** Column: RECIPIENT_ADDRESS type NCLOB(4000,0) NULL */
    @Column(name = "RECIPIENT_ADDRESS")
    private String recipientAddress;
    
    /** Column: RECIPIENT_NAME type NVARCHAR2(510,0) NULL */
    @Column(name = "RECIPIENT_NAME")
    private String recipientName;

    /** Column: BCC_ADDRESS type NCLOB(4000,0) NULL */
    @Column(name = "BCC_ADDRESS")
    private String bccAddress;

    /** Column: CC_ADDRESS type NCLOB(4000,0) NULL */
    @Column(name = "CC_ADDRESS")
    private String ccAddress;

    /** Column: SUBJECT type NCLOB(4000,0) NULL */
    @Column(name = "SUBJECT")
    private String subject;
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    @Transient
    private String url;

	
}

