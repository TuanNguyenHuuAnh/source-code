/*******************************************************************************
 * Class        ：QrtzMSchedule
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
 * QrtzMSchedule
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = "QRTZ_M_SCHEDULE")
public class QrtzMSchedule extends AbstractTracking{

    /** Column: ID type NUMBER(22,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_QRTZ_M_SCHEDULE")
    private Long id;

    /** Column: SCHED_CODE type VARCHAR2(255,0) NOT NULL */
    @Column(name = "SCHED_CODE")
    private String schedCode;

    /** Column: SCHED_NAME type VARCHAR2(255,0) NOT NULL */
    @Column(name = "SCHED_NAME")
    private String schedName;

    /** Column: SCHED_TYPE type NUMBER(22,0) NOT NULL */
    @Column(name = "SCHED_TYPE")
    private Long schedType;

    /** Column: FREQ_TYPE type NUMBER(22,0) NULL */
    @Column(name = "FREQ_TYPE")
    private Long freqType;

    /** Column: FREQ_INTERVAL type NUMBER(22,0) NULL */
    @Column(name = "FREQ_INTERVAL")
    private Long freqInterval;

    /** Column: FREQ_SUBDAY_TYPE type NUMBER(22,0) NULL */
    @Column(name = "FREQ_SUBDAY_TYPE")
    private Long freqSubdayType;

    /** Column: FREQ_SUBDAY_INTERVAL type NUMBER(22,0) NULL */
    @Column(name = "FREQ_SUBDAY_INTERVAL")
    private Long freqSubdayInterval;

    /** Column: FREQ_RECUR_INTERVAL type NUMBER(22,0) NULL */
    @Column(name = "FREQ_RECUR_INTERVAL")
    private Long freqRecurInterval;

    /** Column: FREQ_RECUR_FACTOR type NUMBER(22,0) NULL */
    @Column(name = "FREQ_RECUR_FACTOR")
    private Long freqRecurFactor;

    /** Column: START_DATE type VARCHAR2(8,0) NULL */
    @Column(name = "START_DATE")
    private String startDate;

    /** Column: END_DATE type VARCHAR2(8,0) NULL */
    @Column(name = "END_DATE")
    private String endDate;

    /** Column: START_TIME type VARCHAR2(6,0) NULL */
    @Column(name = "START_TIME")
    private String startTime;

    /** Column: END_TIME type VARCHAR2(6,0) NULL */
    @Column(name = "END_TIME")
    private String endTime;

    /** Column: CRON_EXPRESSION type VARCHAR2(255,0) NOT NULL */
    @Column(name = "CRON_EXPRESSION")
    private String cronExpression;

    /** Column: DESCRIPTION type VARCHAR2(255,0) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: VALIDFLAG type NUMBER(22,0) NOT NULL */
    @Column(name = "VALIDFLAG")
    private Long validflag;
    
    /** Column: SUBJECT type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    @Transient
    private String url;
	
}

