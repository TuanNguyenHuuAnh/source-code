/*******************************************************************************
 * Class        ：ActionDto
 * Created date ：2020/04/09
 * Lasted date  ：2020/04/09
 * Author       ：KhuongTH
 * Change log   ：2020/04/09：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import vn.com.unit.ep2p.admin.constant.ConstantCore;

/**
 * ActionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class PPLActionDto {

    private String buttonId;
    private String buttonType;
    private String buttonValue;
    private String buttonName;
    private String buttonNamePassive;
    private String functionCode;
    private boolean isSave;
    private boolean isSubmit;
    private boolean isAuthenticate;
    private boolean isSign;
    private boolean isExportPdf;
    private boolean fieldSign;
    private boolean displayHistory;
    private String confirmNote;
    private Long signType;
    private Long signPosition;

    public PPLActionDto() {
        super();
        confirmNote = ConstantCore.EMPTY;
    }

    public PPLActionDto(String buttonId, String buttonType, String buttonValue, String functionCode, boolean isSave, boolean isAuthenticate,
            boolean isSign, boolean isExportPdf, boolean fieldSign) {
        super();
        this.buttonId = buttonId;
        this.buttonType = buttonType;
        this.buttonValue = buttonValue;
        this.functionCode = functionCode;
        this.isSave = isSave;
        this.isAuthenticate = isAuthenticate;
        this.isSign = isSign;
        this.isExportPdf = isExportPdf;
        this.fieldSign = fieldSign;
    }

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean isSave) {
        this.isSave = isSave;
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

    public boolean getFieldSign() {
        return fieldSign;
    }

    public void setFieldSign(boolean fieldSign) {
        this.fieldSign = fieldSign;
    }

    public String getConfirmNote() {
        return confirmNote;
    }

    public void setConfirmNote(String confirmNote) {
        this.confirmNote = confirmNote;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonNamePassive() {
        return buttonNamePassive;
    }

    public void setButtonNamePassive(String buttonNamePassive) {
        this.buttonNamePassive = buttonNamePassive;
    }

    public boolean getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(boolean isSubmit) {
        this.isSubmit = isSubmit;
    }

    public boolean getDisplayHistory() {
        return displayHistory;
    }

    public void setDisplayHistory(boolean displayHistory) {
        this.displayHistory = displayHistory;
    }

    public Long getSignType() {
        return signType;
    }

    public void setSignType(Long signType) {
        this.signType = signType;
    }

    public Long getSignPosition() {
        return signPosition;
    }

    public void setSignPosition(Long signPosition) {
        this.signPosition = signPosition;
    }

}
