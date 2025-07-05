/*******************************************************************************
 * Class        ProcessDto
 * Created date 2017/03/28
 * Lasted date  2017/03/28
 * Author       TranLTH
 * Change log   2017/03/2801-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

//import vn.com.unit.jcanary.entity.Step;

/**
 * ProcessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class ProcessDto extends AbstractDTO {

    private Long processId;

    private String name;

    private String bussinesCode;

    private String processDefinitionId;

    private String deploymentId;

    private Date effectedDate;

    private Date expiredDate;

    private String statusName;

    private String statusCode;

    private Long processStep;

    private String active;

    private String code;

    private String status;

    private Date effectiveDate;

    private Integer numOfStep;

//	private Step[] lstSteps;

    /** Current Step */
    private Integer stepNo;

    /** step Jump if conditon is true */
    private Integer condTrueStep;

    /** step Jump if conditon is false */
    private Integer condFalseStep;

    /** Condition */
    private String condition;

    /** Items to check role */
    private String items;

    /** Auto check */
    private boolean autoCheck;

    /** Button of step */
    private String buttonVlalue;

    /** Next step */
    private Integer nextStep;

    /** Button id */
    private String buttonId;

    public ProcessDto() {
    }

    public ProcessDto(Long processId, String name, String bussinesCode, String processDefinitionId, String deploymentId,
            Date effectedDate, Date expiredDate) {
        super();
        this.processId = processId;
        this.name = name;
        this.bussinesCode = bussinesCode;
        this.processDefinitionId = processDefinitionId;
        this.deploymentId = deploymentId;
        this.effectedDate = effectedDate;
        this.expiredDate = expiredDate;
    }

    /**
     * Get name
     * 
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * 
     * @param name type String
     * @return
     * @author TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get bussinesCode
     * 
     * @return String
     * @author TranLTH
     */
    public String getBussinesCode() {
        return bussinesCode;
    }

    /**
     * Set bussinesCode
     * 
     * @param bussinesCode type String
     * @return
     * @author TranLTH
     */
    public void setBussinesCode(String bussinesCode) {
        this.bussinesCode = bussinesCode;
    }

    /**
     * Get processDefinitionId
     * 
     * @return String
     * @author TranLTH
     */
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    /**
     * Set processDefinitionId
     * 
     * @param processDefinitionId type String
     * @return
     * @author TranLTH
     */
    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    /**
     * Get deploymentId
     * 
     * @return String
     * @author TranLTH
     */
    public String getDeploymentId() {
        return deploymentId;
    }

    /**
     * Set deploymentId
     * 
     * @param deploymentId type String
     * @return
     * @author TranLTH
     */
    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    /**
     * Get effectedDate
     * 
     * @return Date
     * @author TranLTH
     */
    public Date getEffectedDate() {
        return effectedDate;
    }

    /**
     * Set effectedDate
     * 
     * @param effectedDate type Date
     * @return
     * @author TranLTH
     */
    public void setEffectedDate(Date effectedDate) {
        this.effectedDate = effectedDate;
    }

    /**
     * Get expiredDate
     * 
     * @return Date
     * @author VinhLT
     */
    public Date getExpiredDate() {
        return expiredDate;
    }

    /**
     * Set expiredDate
     * 
     * @param expiredDate type Date
     * @return
     * @author VinhLT
     */
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * Get statusName
     * 
     * @return String
     * @author TranLTH
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Set statusName
     * 
     * @param statusName type String
     * @return
     * @author TranLTH
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Get statusCode
     * 
     * @return String
     * @author TranLTH
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * 
     * @param statusCode type String
     * @return
     * @author TranLTH
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get processStep
     * 
     * @return Long
     * @author TranLTH
     */
    public Long getProcessStep() {
        return processStep;
    }

    /**
     * Set processStep
     * 
     * @param processStep type Long
     * @return
     * @author TranLTH
     */
    public void setProcessStep(Long processStep) {
        this.processStep = processStep;
    }

    /**
     * Get active
     * 
     * @return String
     * @author TranLTH
     */
    public String getActive() {
        return active;
    }

    /**
     * Set active
     * 
     * @param active type String
     * @return
     * @author TranLTH
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * Get code
     * 
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * 
     * @param code type String
     * @return
     * @author hand
     */
    public void setCode(String code) {
        this.code = code;
    }

//	/**
//	 * Get lstSteps
//	 * 
//	 * @return Step[]
//	 * @author hand
//	 */
//	public Step[] getLstSteps() {
//		return lstSteps;
//	}
//
//	/**
//	 * Set lstSteps
//	 * 
//	 * @param lstSteps
//	 *            type Step[]
//	 * @return
//	 * @author hand
//	 */
//	public void setLstSteps(Step[] lstSteps) {
//		this.lstSteps = lstSteps;
//	}

    /**
     * Get status
     * 
     * @return String
     * @author hand
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status type String
     * @return
     * @author hand
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get effectiveDate
     * 
     * @return Date
     * @author hand
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Set effectiveDate
     * 
     * @param effectiveDate type Date
     * @return
     * @author hand
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Get numOfStep
     * 
     * @return Integer
     * @author hand
     */
    public Integer getNumOfStep() {
        return numOfStep;
    }

    /**
     * Set numOfStep
     * 
     * @param numOfStep type Integer
     * @return
     * @author hand
     */
    public void setNumOfStep(Integer numOfStep) {
        this.numOfStep = numOfStep;
    }

    /**
     * Get processId
     * 
     * @return Long
     * @author hand
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * 
     * @param processId type Long
     * @return
     * @author hand
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get stepNo
     * 
     * @return Integer
     * @author hand
     */
    public Integer getStepNo() {
        return stepNo;
    }

    /**
     * Set stepNo
     * 
     * @param stepNo type Integer
     * @return
     * @author hand
     */
    public void setStepNo(Integer stepNo) {
        this.stepNo = stepNo;
    }

    /**
     * Get condTrueStep
     * 
     * @return Integer
     * @author hand
     */
    public Integer getCondTrueStep() {
        return condTrueStep;
    }

    /**
     * Set condTrueStep
     * 
     * @param condTrueStep type Integer
     * @return
     * @author hand
     */
    public void setCondTrueStep(Integer condTrueStep) {
        this.condTrueStep = condTrueStep;
    }

    /**
     * Get condFalseStep
     * 
     * @return Integer
     * @author hand
     */
    public Integer getCondFalseStep() {
        return condFalseStep;
    }

    /**
     * Set condFalseStep
     * 
     * @param condFalseStep type Integer
     * @return
     * @author hand
     */
    public void setCondFalseStep(Integer condFalseStep) {
        this.condFalseStep = condFalseStep;
    }

    /**
     * Get condition
     * 
     * @return String
     * @author hand
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Set condition
     * 
     * @param condition type String
     * @return
     * @author hand
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Get items
     * 
     * @return String
     * @author hand
     */
    public String getItems() {
        return items;
    }

    /**
     * Set items
     * 
     * @param items type String
     * @return
     * @author hand
     */
    public void setItems(String items) {
        this.items = items;
    }

    /**
     * Get autoCheck
     * 
     * @return boolean
     * @author hand
     */
    public boolean isAutoCheck() {
        return autoCheck;
    }

    /**
     * Set autoCheck
     * 
     * @param autoCheck type boolean
     * @return
     * @author hand
     */
    public void setAutoCheck(boolean autoCheck) {
        this.autoCheck = autoCheck;
    }

    /**
     * Get buttonVlalue
     * 
     * @return String
     * @author hand
     */
    public String getButtonVlalue() {
        return buttonVlalue;
    }

    /**
     * Set buttonVlalue
     * 
     * @param buttonVlalue type String
     * @return
     * @author hand
     */
    public void setButtonVlalue(String buttonVlalue) {
        this.buttonVlalue = buttonVlalue;
    }

    /**
     * Get nextStep
     * 
     * @return Integer
     * @author hand
     */
    public Integer getNextStep() {
        return nextStep;
    }

    /**
     * Set nextStep
     * 
     * @param nextStep type Integer
     * @return
     * @author hand
     */
    public void setNextStep(Integer nextStep) {
        this.nextStep = nextStep;
    }

    /**
     * Get buttonId
     * 
     * @return String
     * @author hand
     */
    public String getButtonId() {
        return buttonId;
    }

    /**
     * Set buttonId
     * 
     * @param buttonId type String
     * @return
     * @author hand
     */
    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }
}
