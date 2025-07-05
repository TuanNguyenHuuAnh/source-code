/*******************************************************************************
 * Class        QuartzTriggersDto
 * Created date 2018/08/14
 * Lasted date  2018/08/14
 * Author       hangnkm
 * Change log   2018/08/1401-00 hangnkm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.ep2p.admin.entity.QuartzTriggers;

/**
 * QuartzTriggersDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hangnkm
 */
public class QuartzTriggersDto {

    private String schedName;

    private String triggerState;

    private String triggerName;

    private String triggerGroup;

    private String jobName;

    private String jobGroup;

    private String triggerType;

    private Boolean playStatus;

    private Boolean pauseStatus;

    private String cronExpression;
    
    private Long nextFireTime;
    
    private Long prevFireTime;

    private QuartzTriggers entity;

    private List<QuartzTriggers> listResult;
    
    private String url;

    
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

    
    /** getPlayStatus
     *
     * @return
     * @author hangnkm
     */
    public Boolean getPlayStatus() {
        return playStatus;
    }

    
    /** setPlayStatus
     *
     * @param playStatus
     * @author hangnkm
     */
    public void setPlayStatus(Boolean playStatus) {
        this.playStatus = playStatus;
    }

    
    /** getPauseStatus
     *
     * @return
     * @author hangnkm
     */
    public Boolean getPauseStatus() {
        return pauseStatus;
    }

    
    /** setPauseStatus
     *
     * @param pauseStatus
     * @author hangnkm
     */
    public void setPauseStatus(Boolean pauseStatus) {
        this.pauseStatus = pauseStatus;
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

   
    /** getEntity
     *
     * @return
     * @author hangnkm
     */
    public QuartzTriggers getEntity() {
        return entity;
    }

   
    /** setEntity
     *
     * @param entity
     * @author hangnkm
     */
    public void setEntity(QuartzTriggers entity) {
        this.entity = entity;
    }

   
    /** getListResult
     *
     * @return
     * @author hangnkm
     */
    public List<QuartzTriggers> getListResult() {
        return listResult != null ? new ArrayList<QuartzTriggers>(listResult) : null;
    }

    
    /** setListResult
     *
     * @param listResult
     * @author hangnkm
     */
    public void setListResult(List<QuartzTriggers> listResult) {
        this.listResult = listResult != null ? new ArrayList<QuartzTriggers>(listResult) : null;
    }


    
    public Long getNextFireTime() {
        return nextFireTime;
    }


    
    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }


    
    public Long getPrevFireTime() {
        return prevFireTime;
    }


    
    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }


    
    public String getUrl() {
        return url;
    }


    
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    

}

