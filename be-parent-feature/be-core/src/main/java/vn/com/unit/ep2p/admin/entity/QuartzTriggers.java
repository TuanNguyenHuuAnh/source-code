/*******************************************************************************
 * Class        QuartzTriggers
 * Created date 2018/08/14
 * Lasted date  2018/08/14
 * Author       hangnkm
 * Change log   2018/08/1401-00 hangnkm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;

/**
 * QuartzTriggers
 * 
 * @version 01-00
 * @since 01-00
 * @author hangnkm
 */
@Table(name = "QRTZ_TRIGGERS")
public class QuartzTriggers {

    @Id
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_QRTZ_TRIGGERS")
    @Column(name = "SCHED_ID")
    private String schedId;

    @Column(name = "SCHED_NAME")
    private String schedName;

    @Column(name = "TRIGGER_NAME")
    private String triggerName;

    @Column(name = "TRIGGER_GROUP")
    private String triggerGroup;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "JOB_GROUP")
    private String jobGroup;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NEXT_FIRE_TIME")
    private Long nextFireTime;

    @Column(name = "PREV_FIRE_TIME")
    private Long prevFireTime;

    @Column(name = "PRIORITY")
    private Long priority;

    @Column(name = "TRIGGER_STATE")
    private String triggerState;

    @Column(name = "TRIGGER_TYPE")
    private String triggerType;

    @Column(name = "START_TIME")
    private Long startTime;

    @Column(name = "END_TIME")
    private Long endTime;

    @Column(name = "CALENDAR_NAME")
    private String calendarName;

    @Column(name = "MISFIRE_INSTR")
    private Integer misfireInstr;

    @Column(name = "JOB_DATA")
    private Long jobData;

    @Transient
    private String cronExpression;

    
    /** getSchedId
     *
     * @return
     * @author hangnkm
     */
    public String getSchedId() {
        return schedId;
    }

   
    /** setSchedId
     *
     * @param schedId
     * @author hangnkm
     */
    public void setSchedId(String schedId) {
        this.schedId = schedId;
    }

    
    /** getSchedName
     *
     * @return
     * @author hangnkm
     */
    public String getSchedName() {
        return schedName;
    }

    
    /** setSchedName
     *
     * @param schedName
     * @author hangnkm
     */
    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    
    /** getTriggerName
     *
     * @return
     * @author hangnkm
     */
    public String getTriggerName() {
        return triggerName;
    }

    
    /** setTriggerName
     *
     * @param triggerName
     * @author hangnkm
     */
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    
    /** getTriggerGroup
     *
     * @return
     * @author hangnkm
     */
    public String getTriggerGroup() {
        return triggerGroup;
    }

    
    /** setTriggerGroup
     *
     * @param triggerGroup
     * @author hangnkm
     */
    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

   
    /** getJobName
     *
     * @return
     * @author hangnkm
     */
    public String getJobName() {
        return jobName;
    }

   
    /** setJobName
     *
     * @param jobName
     * @author hangnkm
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    
    /** getJobGroup
     *
     * @return
     * @author hangnkm
     */
    public String getJobGroup() {
        return jobGroup;
    }

   
    /** setJobGroup
     *
     * @param jobGroup
     * @author hangnkm
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

   
    /** getDescription
     *
     * @return
     * @author hangnkm
     */
    public String getDescription() {
        return description;
    }

    
    /** setDescription
     *
     * @param description
     * @author hangnkm
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** getNextFireTime
     *
     * @return
     * @author hangnkm
     */
    public Long getNextFireTime() {
        return nextFireTime;
    }

    
    /** setNextFireTime
     *
     * @param nextFireTime
     * @author hangnkm
     */
    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    
    /** getPrevFireTime
     *
     * @return
     * @author hangnkm
     */
    public Long getPrevFireTime() {
        return prevFireTime;
    }

   
    /** setPrevFireTime
     *
     * @param prevFireTime
     * @author hangnkm
     */
    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    
    /** getPriority
     *
     * @return
     * @author hangnkm
     */
    public Long getPriority() {
        return priority;
    }

   
    /** setPriority
     *
     * @param priority
     * @author hangnkm
     */
    public void setPriority(Long priority) {
        this.priority = priority;
    }

   
    /** getTriggerState
     *
     * @return
     * @author hangnkm
     */
    public String getTriggerState() {
        return triggerState;
    }

    
    /** setTriggerState
     *
     * @param triggerState
     * @author hangnkm
     */
    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    
    /** getTriggerType
     *
     * @return
     * @author hangnkm
     */
    public String getTriggerType() {
        return triggerType;
    }

   
    /** setTriggerType
     *
     * @param triggerType
     * @author hangnkm
     */
    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

   
    /** getStartTime
     *
     * @return
     * @author hangnkm
     */
    public Long getStartTime() {
        return startTime;
    }

  
    /** setStartTime
     *
     * @param startTime
     * @author hangnkm
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

   
    /** getEndTime
     *
     * @return
     * @author hangnkm
     */
    public Long getEndTime() {
        return endTime;
    }

  
    /** setEndTime
     *
     * @param endTime
     * @author hangnkm
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    
    /** getCalendarName
     *
     * @return
     * @author hangnkm
     */
    public String getCalendarName() {
        return calendarName;
    }

    
    /** setCalendarName
     *
     * @param calendarName
     * @author hangnkm
     */
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

   
    /** getMisfireInstr
     *
     * @return
     * @author hangnkm
     */
    public Integer getMisfireInstr() {
        return misfireInstr;
    }

    
    /** setMisfireInstr
     *
     * @param misfireInstr
     * @author hangnkm
     */
    public void setMisfireInstr(Integer misfireInstr) {
        this.misfireInstr = misfireInstr;
    }

   
    /** getJobData
     *
     * @return
     * @author hangnkm
     */
    public Long getJobData() {
        return jobData;
    }

    
    /** setJobData
     *
     * @param jobData
     * @author hangnkm
     */
    public void setJobData(Long jobData) {
        this.jobData = jobData;
    }

   
    /** getCronExpression
     *
     * @return
     * @author hangnkm
     */
    public String getCronExpression() {
        return cronExpression;
    }

   
    /** setCronExpression
     *
     * @param cronExpression
     * @author hangnkm
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

}
