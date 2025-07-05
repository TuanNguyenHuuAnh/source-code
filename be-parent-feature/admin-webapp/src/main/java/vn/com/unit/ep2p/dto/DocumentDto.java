/*******************************************************************************
 * Class        DocumentDto
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * DocumentDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public abstract class DocumentDto {

    protected Long id;

    protected String docUuid;

    @NotNull
    protected String docTitle;

    protected Long processInstanceId;

    protected String processStatusCode;

    protected Long processDeployId;

    protected String docState;

    protected Long companyId;

    protected Long businessId;

    protected Long orgId;

    protected Long majorVersion;

    protected Long minorVersion;

    protected String formName;

    protected Date createdDate;

    protected String createdBy;

    protected String updatedBy;

    protected Date updatedDate;

    // activiti process
    protected String actTaskId;
    // confirm popup
    protected String confirmNote;
    protected String actButtonIdStr;
    protected String actButtonType;
    protected String actButtonValue;

    protected String docCode;

    public DocumentDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getDocState() {
        return docState;
    }

    public void setDocState(String docState) {
        this.docState = docState;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessStatusCode() {
        return processStatusCode;
    }

    public void setProcessStatusCode(String processStatusCode) {
        this.processStatusCode = processStatusCode;
    }

    public Long getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(Long majorVersion) {
        this.majorVersion = majorVersion;
    }

    public Long getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(Long minorVersion) {
        this.minorVersion = minorVersion;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getProcessDeployId() {
        return processDeployId;
    }

    public void setProcessDeployId(Long processDeployId) {
        this.processDeployId = processDeployId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DocumentDto document = (DocumentDto) o;

        if (id != null ? !id.equals(document.id) : document.id != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    /**
     * Get updatedBy
     * 
     * @return String
     * @author taitt
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set updatedBy
     * 
     * @param updatedBy
     *            type String
     * @return
     * @author taitt
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get updatedDate
     * 
     * @return Date
     * @author taitt
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set updatedDate
     * 
     * @param updatedDate
     *            type Date
     * @return
     * @author taitt
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDocUuid() {
        return docUuid;
    }

    public void setDocUuid(String docUuid) {
        this.docUuid = docUuid;
    }

    public String getConfirmNote() {
        return confirmNote;
    }

    public void setConfirmNote(String confirmNote) {
        this.confirmNote = confirmNote;
    }

    public String getActTaskId() {
        return actTaskId;
    }

    public void setActTaskId(String actTaskId) {
        this.actTaskId = actTaskId;
    }

    public String getActButtonIdStr() {
        return actButtonIdStr;
    }

    public void setActButtonIdStr(String actButtonIdStr) {
        this.actButtonIdStr = actButtonIdStr;
    }

    public String getActButtonType() {
        return actButtonType;
    }

    public void setActButtonType(String actButtonType) {
        this.actButtonType = actButtonType;
    }

    public String getActButtonValue() {
        return actButtonValue;
    }

    public void setActButtonValue(String actButtonValue) {
        this.actButtonValue = actButtonValue;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

}
