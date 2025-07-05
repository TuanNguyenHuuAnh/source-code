/*******************************************************************************
 * Class        OZDocDto
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       KhoaNA
 * Change log   2019/04/12 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OZDocDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class PPLOZDocDto extends DocumentDto {

    private String docUuid;

    private Long formId;

    private String formFileName;

    private String docInputJson;

    private String docName;

    private String fileNamePdf;

    private Long ecmRepositoryId;

    private String summary;

    private String priority;

    private Long businessId;

    private String staff;

    private Long mainFileId;

    protected Long mainFileMajorVersion;

    protected Long mainFileMinorVersion;

    private String docType;

    private String systemCode;

    private String appCode;

    private String col1;
    private String col2;
    private String col3;

    /** file ozd */
    private List<String> fileStream;
    private List<String> fileStreamName;

    /** Only view */
    private String formName;
    private String companyName;
    private String processStatusName;
    private Map<String, String> componentList;
    private String priorityCode;
    private String priorityClass;
    private String orgName;
    private String processDeployName;

    /** AttachFileTemp **/
    private String listIdAttachFiles;

    /** memberId */
    private List<Long> memberAssignIds;
    private List<Long> memberCcIds;

    /* dueDate */
    private Date dueDate;

    private boolean isWorkflow;

    private Long staffId;

    /* INTEGATE */
    private String processType;

    private String isApproved;

    /* REFERENCE */
    private List<Long> memberReferenceIds;
    private String isReference;

    private boolean referenceBtn;

    private String docTitleIsRefer;

    private String validJson;
    
    //MULTI_RECRUITING
    private boolean multiRecruitingFlag;
    
    private String stepCode;
    
    // HIS_INFO AND TOOL_BAR_OZD 
    private Integer toolBarOzd;
    private Integer hisInfo;
    
    // ADD isEditAssignTo CRNew 20200811
    private boolean isOnlineExternal;
    
    private boolean isArchive;
    
    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFormFileName() {
        return formFileName;
    }

    public void setFormFileName(String formFileName) {
        this.formFileName = formFileName;
    }

    public String getDocInputJson() {
        return docInputJson;
    }

    public void setDocInputJson(String docInputJson) {
        this.docInputJson = docInputJson;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public List<String> getFileStream() {
        return fileStream;
    }

    public void setFileStream(List<String> fileStream) {
        this.fileStream = fileStream;
    }

    public List<String> getFileStreamName() {
        return fileStreamName;
    }

    public void setFileStreamName(List<String> fileStreamName) {
        this.fileStreamName = fileStreamName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getFileNamePdf() {
        return fileNamePdf;
    }

    public void setFileNamePdf(String fileNamePdf) {
        this.fileNamePdf = fileNamePdf;
    }

    public Long getEcmRepositoryId() {
        return ecmRepositoryId;
    }

    public void setEcmRepositoryId(Long ecmRepositoryId) {
        this.ecmRepositoryId = ecmRepositoryId;
    }

    public Map<String, String> getComponentList() {
        return componentList;
    }

    public void setComponentList(Map<String, String> componentList) {
        this.componentList = componentList;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getProcessStatusName() {
        return processStatusName;
    }

    public void setProcessStatusName(String processStatusName) {
        this.processStatusName = processStatusName;
    }

    /**
     * getStaff
     * 
     * @return String
     * 
     * @author KhuongTH
     */
    public String getStaff() {
        return staff;
    }

    /**
     * setStaff
     * 
     * @param staff
     *            void
     * 
     * @author KhuongTH
     */
    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getDocUuid() {
        return docUuid;
    }

    public void setDocUuid(String docUuid) {
        this.docUuid = docUuid;
    }

    /**
     * Get listIdAttachFiles
     * 
     * @return String
     * @author taitt
     */
    public String getListIdAttachFiles() {
        return listIdAttachFiles;
    }

    /**
     * Set listIdAttachFiles
     * 
     * @param listIdAttachFiles
     *            type String
     * @return
     * @author taitt
     */
    public void setListIdAttachFiles(String listIdAttachFiles) {
        this.listIdAttachFiles = listIdAttachFiles;
    }

    public Long getMainFileId() {
        return mainFileId;
    }

    public void setMainFileId(Long mainFileId) {
        this.mainFileId = mainFileId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public List<Long> getMemberAssignIds() {
        return memberAssignIds;
    }

    public void setMemberAssignIds(List<Long> memberAssignIds) {
        this.memberAssignIds = memberAssignIds;
    }

    public List<Long> getMemberCcIds() {
        return memberCcIds;
    }

    public void setMemberCcIds(List<Long> memberCcIds) {
        this.memberCcIds = memberCcIds;
    }

    /**
     * Get dueDate
     * 
     * @return Date
     * @author taitt
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set dueDate
     * 
     * @param dueDate
     *            type Date
     * @return
     * @author taitt
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getIsWorkflow() {
        return isWorkflow;
    }

    public void setIsWorkflow(boolean isWorkflow) {
        this.isWorkflow = isWorkflow;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public String getPriorityClass() {
        return priorityClass;
    }

    public void setPriorityClass(String priorityClass) {
        this.priorityClass = priorityClass;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProcessDeployName() {
        return processDeployName;
    }

    public void setProcessDeployName(String processDeployName) {
        this.processDeployName = processDeployName;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    /**
     * Get mainFileMajorVersion
     * 
     * @return Long
     * @author taitt
     */
    public Long getMainFileMajorVersion() {
        return mainFileMajorVersion;
    }

    /**
     * Set mainFileMajorVersion
     * 
     * @param mainFileMajorVersion
     *            type Long
     * @return
     * @author taitt
     */
    public void setMainFileMajorVersion(Long mainFileMajorVersion) {
        this.mainFileMajorVersion = mainFileMajorVersion;
    }

    /**
     * Get mainFileMinorVersion
     * 
     * @return Long
     * @author taitt
     */
    public Long getMainFileMinorVersion() {
        return mainFileMinorVersion;
    }

    /**
     * Set mainFileMinorVersion
     * 
     * @param mainFileMinorVersion
     *            type Long
     * @return
     * @author taitt
     */
    public void setMainFileMinorVersion(Long mainFileMinorVersion) {
        this.mainFileMinorVersion = mainFileMinorVersion;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    /**
     * Get isApproved
     * 
     * @return String
     * @author taitt
     */
    public String getIsApproved() {
        return isApproved;
    }

    /**
     * Set isApproved
     * 
     * @param isApproved
     *            type String
     * @return
     * @author taitt
     */
    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public List<Long> getMemberReferenceIds() {
        return memberReferenceIds;
    }

    public void setMemberReferenceIds(List<Long> memberReferenceIds) {
        this.memberReferenceIds = memberReferenceIds;
    }

    public String getIsReference() {
        return isReference;
    }

    public void setIsReference(String isReference) {
        this.isReference = isReference;
    }

    public boolean getReferenceBtn() {
        return referenceBtn;
    }

    public void setReferenceBtn(boolean referenceBtn) {
        this.referenceBtn = referenceBtn;
    }

    public String getDocTitleIsRefer() {
        return docTitleIsRefer;
    }

    public void setDocTitleIsRefer(String docTitleIsRefer) {
        this.docTitleIsRefer = docTitleIsRefer;
    }

    public String getValidJson() {
        return validJson;
    }

    public void setValidJson(String validJson) {
        this.validJson = validJson;
    }

    public boolean isMultiRecruitingFlag() {
        return multiRecruitingFlag;
    }

    public void setMultiRecruitingFlag(boolean multiRecruitingFlag) {
        this.multiRecruitingFlag = multiRecruitingFlag;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public Integer getToolBarOzd() {
        return toolBarOzd;
    }

    public void setToolBarOzd(Integer toolBarOzd) {
        this.toolBarOzd = toolBarOzd;
    }

    public Integer getHisInfo() {
        return hisInfo;
    }

    public void setHisInfo(Integer hisInfo) {
        this.hisInfo = hisInfo;
    }

    
    public boolean getIsOnlineExternal() {
        return isOnlineExternal;
    }

    
    public void setIsOnlineExternal(boolean isOnlineExternal) {
        this.isOnlineExternal = isOnlineExternal;
    }

    
    public boolean getIsArchive() {
        return isArchive;
    }

    
    public void setIsArchive(boolean isArchive) {
        this.isArchive = isArchive;
    }
    
}
