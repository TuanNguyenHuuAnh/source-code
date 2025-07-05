/*******************************************************************************
 * Class        ：QrtzMJobSchedule
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/

package vn.com.unit.quartz.job.entity;

import java.util.Date;

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
 * QrtzMJobSchedule
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = "QRTZ_M_JOB_SCHEDULE")
public class QrtzMJobSchedule extends AbstractTracking{

	/** Column: ID type NUMBER(22,0) NOT NULL */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_QRTZ_M_JOB_SCHEDULE")
	private Long id;

	/** Column: JOB_CODE type NUMBER(20,0) NOT NULL */
	@Column(name = "JOB_ID")
	private Long jobId;

	/** Column: JOB_GROUP type VARCHAR2(255,0) NOT NULL */
	@Column(name = "JOB_GROUP")
	private String jobGroup;

	/** Column: JOB_NAME_REF type VARCHAR2(255,0) NOT NULL */
	@Column(name = "JOB_NAME_REF")
	private String jobNameRef;

	/** Column: SCHED_CODE type NUMBER(20,0) NULL */
	@Column(name = "SCHED_ID")
	private Long schedId;

	/** Column: STATUS type NUMBER(22,0) NOT NULL */
	@Column(name = "STATUS")
	private Long status;

	/** Column: START_TIME type TIMESTAMP(6)(11,0) NULL */
	@Column(name = "START_TIME")
	private Date startTime;
	
	/** The start type. */
	@Column(name = "START_TYPE")
    private String startType;

	/** Column: NEXT_FIRE_TIME type TIMESTAMP(6)(11,0) NULL */
	@Column(name = "NEXT_FIRE_TIME")
	private Date nextFireTime;

	/** Column: END_TIME type TIMESTAMP(6)(11,0) NULL */
	@Column(name = "END_TIME")
	private Date endTime;

	/** Column: DESCRIPTION type NVARCHAR2(510,0) NULL */
	@Column(name = "DESCRIPTION")
	private String description;

	/** Column: VALIDFLAG type NUMBER(22,0) NOT NULL */
	@Column(name = "VALIDFLAG")
	private Long validflag;

	/** Column: SUBJECT type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    /** Column: START_DATE type DATE NULL */
    @Column(name = "START_DATE")
    private Date startDate;
    
    /** Column: ERROR_MESSAGE type NVARCHAR2(255) NULL */
    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;
    
    @Transient
    private String url;
    
    @Transient
    private String startTimeString;

}