package vn.com.unit.process.workflow.dto;

import java.util.List;

import vn.com.unit.workflow.dto.JpmParamConfigDto;

public class AppParamDto {

    private Long id;
    private Long processId;
    private String fieldName;
    private String formFieldName;
    private String dataType;

    private List<JpmParamConfigDto> configSteps;

    /** only view */
    private Long businessId;

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFormFieldName() {
        return formFieldName;
    }

    public void setFormFieldName(String formFieldName) {
        this.formFieldName = formFieldName;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public List<JpmParamConfigDto> getConfigSteps() {
        return configSteps;
    }

    public void setConfigSteps(List<JpmParamConfigDto> configSteps) {
        this.configSteps = configSteps;
    }

}
