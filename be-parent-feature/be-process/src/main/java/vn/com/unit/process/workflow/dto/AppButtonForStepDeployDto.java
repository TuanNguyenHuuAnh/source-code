package vn.com.unit.process.workflow.dto;

public class AppButtonForStepDeployDto {

    private Long id;
    private Long stepId;
    private Long buttonId;
    private Long functionId;
    private boolean isSave;
    private boolean isDeleted;
    private boolean isAuthenticate;
    private boolean displayHistoryApprove;
    private boolean isSign;
    private boolean isExportPdf;
    private boolean fieldSign;
    private boolean isSaveEform;
    private Long signType;
    private Long signPosition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getButtonId() {
        return buttonId;
    }

    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean save) {
        isSave = save;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean getIsAuthenticate() {
        return isAuthenticate;
    }

    public void setIsAuthenticate(boolean isAuthenticate) {
        this.isAuthenticate = isAuthenticate;
    }

    public boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public boolean getIsExportPdf() {
        return isExportPdf;
    }

    public void setIsExportPdf(boolean isExportPdf) {
        this.isExportPdf = isExportPdf;
    }

    public boolean getDisplayHistoryApprove() {
        return displayHistoryApprove;
    }

    public void setDisplayHistoryApprove(boolean displayHistoryApprove) {
        this.displayHistoryApprove = displayHistoryApprove;
    }

    public boolean getFieldSign() {
        return fieldSign;
    }

    public void setFieldSign(boolean fieldSign) {
        this.fieldSign = fieldSign;
    }

    public boolean getIsSaveEform() {
        return isSaveEform;
    }

    public void setIsSaveEform(boolean isSaveEform) {
        this.isSaveEform = isSaveEform;
    }

    /**
     * Get signType
     * 
     * @return Long
     * @author KhuongTH
     */
    public Long getSignType() {
        return signType;
    }

    /**
     * Set signType
     * 
     * @param signType
     *            type Long
     * @return
     * @author KhuongTH
     */
    public void setSignType(Long signType) {
        this.signType = signType;
    }

    /**
     * Get signPosition
     * 
     * @return Long
     * @author KhuongTH
     */
    public Long getSignPosition() {
        return signPosition;
    }

    /**
     * Set signPosition
     * 
     * @param signPosition
     *            type Long
     * @return
     * @author KhuongTH
     */
    public void setSignPosition(Long signPosition) {
        this.signPosition = signPosition;
    }

}
