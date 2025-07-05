/*******************************************************************************
 * Class        JpmButtonForDoc
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       KhoaNA
 * Change log   2019/04/12 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * JpmButtonForDoc
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class PPLJpmButtonForDocDto {

    private Long id;

    private String buttonCode;

    private String buttonText;

    private String buttonValue;

    private String buttonClass;

    private String buttonType;

    private Long orders;

    private Long assignTo;

    private boolean isSave;

    private boolean isSaveEform;

    private boolean isSign;

    private boolean isAuthenticate;

    private boolean isExportPdf;

    private String functionCode;

    private String buttonIcon;

    private boolean fieldSign;

    private boolean displayHistory;
    private String buttonNamePassive;
    
    /** support API */
    private boolean integrate;
    private boolean transfer;
    private String docUuid;
    private String stepCode;
    private Long processDeployId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(Long assignTo) {
        this.assignTo = assignTo;
    }

    public boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean save) {
        isSave = save;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getButtonIcon() {
        return buttonIcon;
    }

    public void setButtonIcon(String buttonIcon) {
        this.buttonIcon = buttonIcon;
    }

    public boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public boolean getIsAuthenticate() {
        return isAuthenticate;
    }

    public void setIsAuthenticate(boolean isAuthenticate) {
        this.isAuthenticate = isAuthenticate;
    }

    public boolean getIsExportPdf() {
        return isExportPdf;
    }

    public void setIsExportPdf(boolean isExportPdf) {
        this.isExportPdf = isExportPdf;
    }

    public boolean getIsSaveEform() {
        return isSaveEform;
    }

    public void setIsSaveEform(boolean isSaveEform) {
        this.isSaveEform = isSaveEform;
    }

    public boolean getFieldSign() {
        return fieldSign;
    }

    public void setFieldSign(boolean fieldSign) {
        this.fieldSign = fieldSign;
    }

    public boolean getDisplayHistory() {
        return displayHistory;
    }

    public void setDisplayHistory(boolean displayHistory) {
        this.displayHistory = displayHistory;
    }

    public String getButtonNamePassive() {
        return buttonNamePassive;
    }

    public void setButtonNamePassive(String buttonNamePassive) {
        this.buttonNamePassive = buttonNamePassive;
    }

    
    public boolean getIntegrate() {
        return integrate;
    }

    
    public void setIntegrate(boolean integrate) {
        this.integrate = integrate;
    }

    
    public boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer){
        this.transfer = transfer;
    }

    
    public String getDocUuid() {
        return docUuid;
    }

    
    public void setDocUuid(String docUuid) {
        this.docUuid = docUuid;
    }

    
    public String getStepCode() {
        return stepCode;
    }

    
    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    
    public Long getProcessDeployId() {
        return processDeployId;
    }

    
    public void setProcessDeployId(Long processDeployId) {
        this.processDeployId = processDeployId;
    }

}
