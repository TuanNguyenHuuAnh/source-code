package vn.com.unit.process.workflow.dto;

import java.util.List;

import vn.com.unit.workflow.dto.JpmStepLangDto;

public class AppStepDto {

    private Long id;
    private Long stepNo;
    private String code;
    private String name;
    private Long statusId;
    private Long processId;
    private String stepType;
    private String stepKind;
    private String commonStatusCode;
    private boolean useClaimButton;

    private AppButtonForStepDto[] listJpmButton;

    /** Only view */
    private String statusName;
    private String commonStatusName;
    private String buttonName;
    private String buttonClass;
    private boolean isSave;
    private boolean isSaveEform;
    private boolean isAuthenticate;
    private boolean displayHistoryApprove;
    private boolean isSign;
    private boolean isExportPdf;
    private boolean fieldSign;
    private String functionName;
    private int countButton;
    private boolean isHidden;

    private Long signType;
    private Long signPosition;

    private boolean authorityComment;

    private List<JpmStepLangDto> listJpmStepLang;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStepNo() {
        return stepNo;
    }

    public void setStepNo(Long stepNo) {
        this.stepNo = stepNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public AppButtonForStepDto[] getListJpmButton() {
        return listJpmButton;
    }

    public void setListJpmButton(AppButtonForStepDto[] listJpmButton) {
        this.listJpmButton = listJpmButton;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonClass() {
        return buttonClass;
    }

    public void setButtonClass(String buttonClass) {
        this.buttonClass = buttonClass;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean isSave) {
        this.isSave = isSave;
    }

    public int getCountButton() {
        return countButton;
    }

    public void setCountButton(int countButton) {
        this.countButton = countButton;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public List<JpmStepLangDto> getListJpmStepLang() {
        return listJpmStepLang;
    }

    public void setListJpmStepLang(List<JpmStepLangDto> listJpmStepLang) {
        this.listJpmStepLang = listJpmStepLang;
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

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public String getStepKind() {
        return stepKind;
    }

    public void setStepKind(String stepKind) {
        this.stepKind = stepKind;
    }

    public String getCommonStatusCode() {
        return commonStatusCode;
    }

    public void setCommonStatusCode(String commonStatusCode) {
        this.commonStatusCode = commonStatusCode;
    }

    public String getCommonStatusName() {
        return commonStatusName;
    }

    public void setCommonStatusName(String commonStatusName) {
        this.commonStatusName = commonStatusName;
    }

    public boolean getUseClaimButton() {
        return useClaimButton;
    }

    public void setUseClaimButton(boolean useClaimButton) {
        this.useClaimButton = useClaimButton;
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

    public boolean getAuthorityComment() {
        return authorityComment;
    }

    public void setAuthorityComment(boolean authorityComment) {
        this.authorityComment = authorityComment;
    }

}
