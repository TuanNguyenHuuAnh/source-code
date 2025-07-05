package vn.com.unit.process.workflow.dto;

import java.util.List;

import vn.com.unit.workflow.dto.JpmButtonLangDeployDto;

public class AppButtonDeployDto {
    private Long id;
    private Long processId;

    private String buttonCode;
    private String buttonText;
    private String buttonValue;
    private String buttonClass;
    private String buttonType;

    private Long orders;
    private Long assignTo;
    
    private List<JpmButtonLangDeployDto> listJpmButtonLang;

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

    public String getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    public String getButtonClass() {
        return buttonClass;
    }

    public void setButtonClass(String buttonClass) {
        this.buttonClass = buttonClass;
    }

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public List<JpmButtonLangDeployDto> getListJpmButtonLang() {
        return listJpmButtonLang;
    }

    public void setListJpmButtonLang(List<JpmButtonLangDeployDto> listJpmButtonLang) {
        this.listJpmButtonLang = listJpmButtonLang;
    }

	public Long getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(Long assignTo) {
		this.assignTo = assignTo;
	}
    
    
}
