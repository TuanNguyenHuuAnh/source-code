/*******************************************************************************
 * Class        ：JpmParamConfigStepDeployDto
 * Created date ：2019/11/29
 * Lasted date  ：2019/11/29
 * Author       ：KhuongTH
 * Change log   ：2019/11/29：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * JpmParamConfigStepDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class JpmParamConfigStepDeployDto {

    private Long id;
    private Long processId;
    private Long paramId;
    private Long stepId;
    private String stepName;
    private boolean required;
    private Long paramConfigStepId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Long getParamConfigStepId() {
        return paramConfigStepId;
    }

    public void setParamConfigStepId(Long paramConfigStepId) {
        this.paramConfigStepId = paramConfigStepId;
    }

    /**
     * @author KhuongTH
     */
    public JpmParamConfigStepDeployDto() {
    }

}
