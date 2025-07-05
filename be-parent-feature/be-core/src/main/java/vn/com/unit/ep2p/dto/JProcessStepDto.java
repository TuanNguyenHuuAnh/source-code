/*******************************************************************************
 * Class        JProcessStepDto
 * Created date 2017/03/28
 * Lasted date  2017/03/28
 * Author       hand
 * Change log   2017/03/2801-00 hand create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * JProcessStepDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class JProcessStepDto {

    /** Business Code */
    private String bussinesCode;

    /** Button id */
    private Long processId;

    /** Step Number */
    private Integer stepNo;
    
    /** Step Name */
    private String stepName;
    
    /** Status Code */
    private String statusCode;

    /** Status Name */
    private String statusName;

    /** Button id */
    private String buttonId;

    /** Button name */
    private String buttonText;
    
    /** Condition */
    private String condition;

    /** step Jump if conditon is true */
    private Integer condTrueStep;

    /** step Jump if conditon is false */
    private Integer condFalseStep;

    /** Items to check role */
    private String items;

    /** Auto check */
    private boolean autoCheck;

    /** Next step */
    private Integer nextStep;
    
    /** reference id */
    private Long referenceId;

    /**
     * Get bussinesCode
     * @return String
     * @author hand
     */
    public String getBussinesCode() {
        return bussinesCode;
    }

    /**
     * Set bussinesCode
     * @param   bussinesCode
     *          type String
     * @return
     * @author  hand
     */
    public void setBussinesCode(String bussinesCode) {
        this.bussinesCode = bussinesCode;
    }

    /**
     * Get processId
     * @return Long
     * @author hand
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type Long
     * @return
     * @author  hand
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get stepNo
     * @return Integer
     * @author hand
     */
    public Integer getStepNo() {
        return stepNo;
    }

    /**
     * Set stepNo
     * @param   stepNo
     *          type Integer
     * @return
     * @author  hand
     */
    public void setStepNo(Integer stepNo) {
        this.stepNo = stepNo;
    }

    /**
     * Get stepName
     * @return String
     * @author tritv
     */
    public String getStepName() {
        return stepName;
    }

    /**
     * Set stepName
     * @param   stepName
     *          type String
     * @return
     * @author  tritv
     */
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    /**
     * Get statusCode
     * @return String
     * @author hand
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * @param   statusCode
     *          type String
     * @return
     * @author  hand
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get statusName
     * @return String
     * @author hand
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Set statusName
     * @param   statusName
     *          type String
     * @return
     * @author  hand
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Get buttonId
     * @return String
     * @author hand
     */
    public String getButtonId() {
        return buttonId;
    }

    /**
     * Set buttonId
     * @param   buttonId
     *          type String
     * @return
     * @author  hand
     */
    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    /**
     * Get condition
     * @return String
     * @author hand
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Set condition
     * @param   condition
     *          type String
     * @return
     * @author  hand
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Get condTrueStep
     * @return Integer
     * @author hand
     */
    public Integer getCondTrueStep() {
        return condTrueStep;
    }

    /**
     * Set condTrueStep
     * @param   condTrueStep
     *          type Integer
     * @return
     * @author  hand
     */
    public void setCondTrueStep(Integer condTrueStep) {
        this.condTrueStep = condTrueStep;
    }

    /**
     * Get condFalseStep
     * @return Integer
     * @author hand
     */
    public Integer getCondFalseStep() {
        return condFalseStep;
    }

    /**
     * Set condFalseStep
     * @param   condFalseStep
     *          type Integer
     * @return
     * @author  hand
     */
    public void setCondFalseStep(Integer condFalseStep) {
        this.condFalseStep = condFalseStep;
    }

    /**
     * Get items
     * @return String
     * @author hand
     */
    public String getItems() {
        return items;
    }

    /**
     * Set items
     * @param   items
     *          type String
     * @return
     * @author  hand
     */
    public void setItems(String items) {
        this.items = items;
    }

    /**
     * Get autoCheck
     * @return boolean
     * @author hand
     */
    public boolean isAutoCheck() {
        return autoCheck;
    }

    /**
     * Set autoCheck
     * @param   autoCheck
     *          type boolean
     * @return
     * @author  hand
     */
    public void setAutoCheck(boolean autoCheck) {
        this.autoCheck = autoCheck;
    }

    /**
     * Get nextStep
     * @return Integer
     * @author hand
     */
    public Integer getNextStep() {
        return nextStep;
    }

    /**
     * Set nextStep
     * @param   nextStep
     *          type Integer
     * @return
     * @author  hand
     */
    public void setNextStep(Integer nextStep) {
        this.nextStep = nextStep;
    }

    /**
     * Get buttonText
     * @return String
     * @author hand
     */
    public String getButtonText() {
        return buttonText;
    }

    /**
     * Set buttonText
     * @param   buttonText
     *          type String
     * @return
     * @author  hand
     */
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    /**
     * Get referenceId
     * @return Long
     * @author tritv
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * @param   referenceId
     *          type Long
     * @return
     * @author  tritv
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
    
}