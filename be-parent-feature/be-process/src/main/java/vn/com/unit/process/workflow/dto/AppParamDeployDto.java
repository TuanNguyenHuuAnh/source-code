/*******************************************************************************
 * Class        AppParamDeployDto
 * Created date 2019/07/05
 * Lasted date  2019/07/05
 * Author       KhuongTH
 * Change log   2019/07/05 01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.dto;

import java.util.List;

import vn.com.unit.workflow.dto.JpmParamConfigDeployDto;

/**
 * AppParamDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class AppParamDeployDto {
	private Long id;
    private Long processId;
    private String fieldName;
    private String formFieldName;
    private String dataType;
    
    private List<JpmParamConfigDeployDto> configSteps;

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

    public List<JpmParamConfigDeployDto> getConfigSteps() {
        return configSteps;
    }

    public void setConfigSteps(List<JpmParamConfigDeployDto> configSteps) {
        this.configSteps = configSteps;
    }
}
