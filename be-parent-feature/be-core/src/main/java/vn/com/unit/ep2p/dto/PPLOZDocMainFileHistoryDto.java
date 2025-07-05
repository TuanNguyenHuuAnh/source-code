/*******************************************************************************
 * Class        ：OZDocMainFileHistoryDto
 * Created date ：2019/08/09
 * Lasted date  ：2019/08/09
 * Author       ：KhuongTH
 * Change log   ：2019/08/09：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * OZDocMainFileHistoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class PPLOZDocMainFileHistoryDto {

    private Long id;

    private Long majorVersion;

    private Long minorVersion;

    private String note;

    private Date createdDate;

    private String createdBy;

    private String action;

    private String revertTo;

    private String fileName;

    private String formFileName;

    private Long ecmRepositoryId;

    private String fileNameView;

    private String fileNamePdf;

    private Long pdfMajorVersion;

    private Long pdfMinorVersion;

    private String versionFileHis;

    private String validJson;

    /** view */
    private String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRevertTo() {
        return revertTo;
    }

    public void setRevertTo(String revertTo) {
        this.revertTo = revertTo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameView() {
        return fileNameView;
    }

    public void setFileNameView(String fileNameView) {
        this.fileNameView = fileNameView;
    }

    public Long getPdfMajorVersion() {
        return pdfMajorVersion;
    }

    public void setPdfMajorVersion(Long pdfMajorVersion) {
        this.pdfMajorVersion = pdfMajorVersion;
    }

    public Long getPdfMinorVersion() {
        return pdfMinorVersion;
    }

    public void setPdfMinorVersion(Long pdfMinorVersion) {
        this.pdfMinorVersion = pdfMinorVersion;
    }

    public String getFormFileName() {
        return formFileName;
    }

    public void setFormFileName(String formFileName) {
        this.formFileName = formFileName;
    }

    public Long getEcmRepositoryId() {
        return ecmRepositoryId;
    }

    public void setEcmRepositoryId(Long ecmRepositoryId) {
        this.ecmRepositoryId = ecmRepositoryId;
    }

    public String getFileNamePdf() {
        return fileNamePdf;
    }

    public void setFileNamePdf(String fileNamePdf) {
        this.fileNamePdf = fileNamePdf;
    }

    public String getVersionFileHis() {
        return versionFileHis;
    }

    public void setVersionFileHis(String versionFileHis) {
        this.versionFileHis = versionFileHis;
    }

    public String getValidJson() {
        return validJson;
    }

    public void setValidJson(String validJson) {
        this.validJson = validJson;
    }

}
