/*******************************************************************************
 * Class        ：OZDocMainFileDto
 * Created date ：2019/08/07
 * Lasted date  ：2019/08/07
 * Author       ：KhuongTH
 * Change log   ：2019/08/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * OZDocMainFileDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
/**
 * OZDocMainFileDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class PPLOZDocMainFileDto {

    private Long id;

    private Long docId;

    private String docInputJson;

    private Long ecmRepositoryId;

    private String formFileName;

    private Long formId;

    private Long majorVersion;

    private Long minorVersion;

    private String fileNamePdf;

    private String fileName;

    private String fileNameView;

    private Long pdfMajorVersion;

    private Long pdfMinorVersion;

    private String validJson;
    
    private Long seqDocId;

    /** only view */
    private String formName;
    private Long companyId;
    private Long historyId;
    private boolean isCompleted;

    /** file ozd */
    private List<String> fileStream;
    private List<String> fileStreamName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getDocInputJson() {
        return docInputJson;
    }

    public void setDocInputJson(String docInputJson) {
        this.docInputJson = docInputJson;
    }

    public Long getEcmRepositoryId() {
        return ecmRepositoryId;
    }

    public void setEcmRepositoryId(Long ecmRepositoryId) {
        this.ecmRepositoryId = ecmRepositoryId;
    }

    public String getFormFileName() {
        return formFileName;
    }

    public void setFormFileName(String formFileName) {
        this.formFileName = formFileName;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
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

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNamePdf() {
        return fileNamePdf;
    }

    public void setFileNamePdf(String fileNamePdf) {
        this.fileNamePdf = fileNamePdf;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
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

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getValidJson() {
        return validJson;
    }

    public void setValidJson(String validJson) {
        this.validJson = validJson;
    }
    
    public Long getSeqDocId() {
        return seqDocId;
    }

    public void setSeqDocId(Long seqDocId) {
        this.seqDocId = seqDocId;
    }
    
}
