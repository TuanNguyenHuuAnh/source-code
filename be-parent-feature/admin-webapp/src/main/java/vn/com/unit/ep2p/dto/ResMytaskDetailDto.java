/*******************************************************************************
 * Class        ：ResMytaskDetailDto
 * Created date ：2019/08/02
 * Lasted date  ：2019/08/02
 * Author       ：KhuongTH
 * Change log   ：2019/08/02：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ResMytaskDetailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class ResMytaskDetailDto {

    private Long documentId;
    private Long serviceId;
    private String serviceName;
    private String serviceType;
    private String serviceImagePath;
    private Long serviceImageRepoId;
    private String documentName;
    private String documentCode;
    private String description;
    private String docState;
    private String priority;
    private Long majorVersion;
    private Long minorVersion;
    private String isReference;
    
    @JsonInclude(Include.NON_NULL)
    private String created;
    
    private String dueDate;
    @JsonInclude(Include.NON_NULL)
    private Date dueDated;

    private Long companyId;
    private String companyName;
    private String systemCode;
    private String systemName;
    private String logoPath;
    private Long logoRepoId;

    private Long categoryId;
    private String categoryName;

    private Integer toolBarOzd;
    private String ozdFileName;
    private Long ozdFileNameRepoId;
    private Long majorFileVersion;
    private Long minorFileVersion;
    private String fileName;
    private Long fileNameRepoId;
    private String validJson;
    private OZDocMainFileValidObjectDto errorFieldFile;
    
    private Long ozdFileNameId;
    private String docName;

    private Long businessId;

    private String actTaskId;

    private String submitBy;
    @JsonInclude(Include.NON_NULL)
    private Long submitById;
    private String submitByName;
    private String submitByPosition;
    private Long departmentId;
    private String departmentName;
    private String status;
    private String statusDate;

    private Long processId;
    private String processName;
    private String processType;

    /** free form */
    private List<ResAssigneDto> assingedTo;
    private List<ResAssigneDto> relatedEmails;
    private List<ResAssigneDto> referenceEmails;

    @JsonInclude(Include.NON_NULL)
    private String businessKey;

    @JsonInclude(Include.NON_NULL)
    private Date createdDate;

    @JsonInclude(Include.NON_NULL)
    private Date statusDated;

    @JsonInclude(Include.NON_NULL)
    private String statusCode;
    @JsonInclude(Include.NON_NULL)
    private String docStateCode;

    @JsonInclude(Include.NON_NULL)
    private String processStatusCode;

    @JsonInclude(Include.NON_NULL)
    private List<ResDocumentApprovalDto> approvalProcess;

    private String isSaveEform;
    @JsonInclude(Include.NON_NULL)
    private List<ResDocumentButtonDto> bpmButtons;

    @JsonInclude(Include.NON_NULL)
    private String busCode;


    private Long staffId;
    private String staffEmail;
    private String staffName;
    private String position;
    
    private Long isEditAssignTo;
    
    private boolean isDataArchive;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOzdFileName() {
        return ozdFileName;
    }

    public void setOzdFileName(String ozdFileName) {
        this.ozdFileName = ozdFileName;
    }

    public Long getOzdFileNameRepoId() {
        return ozdFileNameRepoId;
    }

    public void setOzdFileNameRepoId(Long ozdFileNameRepoId) {
        this.ozdFileNameRepoId = ozdFileNameRepoId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileNameRepoId() {
        return fileNameRepoId;
    }

    public void setFileNameRepoId(Long fileNameRepoId) {
        this.fileNameRepoId = fileNameRepoId;
    }

    public String getSubmitBy() {
        return submitBy;
    }

    public void setSubmitBy(String submitBy) {
        this.submitBy = submitBy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<ResDocumentApprovalDto> getApprovalProcess() {
        return approvalProcess;
    }

    public void setApprovalProcess(List<ResDocumentApprovalDto> approvalProcess) {
        this.approvalProcess = approvalProcess;
    }

    public List<ResDocumentButtonDto> getBpmButtons() {
        return bpmButtons;
    }

    public void setBpmButtons(List<ResDocumentButtonDto> bpmButtons) {
        this.bpmButtons = bpmButtons;
    }

    /**
     * Get statusDate
     * 
     * @return String
     * @author taitt
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * Set statusDate
     * 
     * @param statusDate
     *            type String
     * @return
     * @author taitt
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * Get statusDated
     * 
     * @return Date
     * @author taitt
     */
    public Date getStatusDated() {
        return statusDated;
    }

    /**
     * Set statusDated
     * 
     * @param statusDated
     *            type Date
     * @return
     * @author taitt
     */
    public void setStatusDated(Date statusDated) {
        this.statusDated = statusDated;
    }

    /**
     * Get department
     * 
     * @return Long
     * @author taitt
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Set department
     * 
     * @param department
     *            type Long
     * @return
     * @author taitt
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Get departmentName
     * 
     * @return String
     * @author taitt
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Set departmentName
     * 
     * @param departmentName
     *            type String
     * @return
     * @author taitt
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Get majorVersion
     * 
     * @return Long
     * @author taitt
     */
    public Long getMajorVersion() {
        return majorVersion;
    }

    /**
     * Set majorVersion
     * 
     * @param majorVersion
     *            type Long
     * @return
     * @author taitt
     */
    public void setMajorVersion(Long majorVersion) {
        this.majorVersion = majorVersion;
    }

    /**
     * Get minorVersion
     * 
     * @return Long
     * @author taitt
     */
    public Long getMinorVersion() {
        return minorVersion;
    }

    /**
     * Set minorVersion
     * 
     * @param minorVersion
     *            type Long
     * @return
     * @author taitt
     */
    public void setMinorVersion(Long minorVersion) {
        this.minorVersion = minorVersion;
    }

    /**
     * Get ozdFileNameId
     * 
     * @return Long
     * @author taitt
     */
    public Long getOzdFileNameId() {
        return ozdFileNameId;
    }

    /**
     * Set ozdFileNameId
     * 
     * @param ozdFileNameId
     *            type Long
     * @return
     * @author taitt
     */
    public void setOzdFileNameId(Long ozdFileNameId) {
        this.ozdFileNameId = ozdFileNameId;
    }

    /**
     * Get docName
     * 
     * @return String
     * @author taitt
     */
    public String getDocName() {
        return docName;
    }

    /**
     * Set docName
     * 
     * @param docName
     *            type String
     * @return
     * @author taitt
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }

    /**
     * Get actTaskId
     * 
     * @return String
     * @author taitt
     */
    public String getActTaskId() {
        return actTaskId;
    }

    /**
     * Set actTaskId
     * 
     * @param actTaskId
     *            type String
     * @return
     * @author taitt
     */
    public void setActTaskId(String actTaskId) {
        this.actTaskId = actTaskId;
    }

    /**
     * Get serviceName
     * 
     * @return String
     * @author taitt
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Set serviceName
     * 
     * @param serviceName
     *            type String
     * @return
     * @author taitt
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Get busCode
     * 
     * @return String
     * @author taitt
     */
    public String getBusCode() {
        return busCode;
    }

    /**
     * Set busCode
     * 
     * @param busCode
     *            type String
     * @return
     * @author taitt
     */
    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    /**
     * Get companyId
     * 
     * @return Long
     * @author taitt
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Set companyId
     * 
     * @param companyId
     *            type Long
     * @return
     * @author taitt
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Get companyName
     * 
     * @return String
     * @author taitt
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set companyName
     * 
     * @param companyName
     *            type String
     * @return
     * @author taitt
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get systemCode
     * 
     * @return String
     * @author taitt
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * Set systemCode
     * 
     * @param systemCode
     *            type String
     * @return
     * @author taitt
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * Get systemName
     * 
     * @return String
     * @author taitt
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * Set systemName
     * 
     * @param systemName
     *            type String
     * @return
     * @author taitt
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * Get logoPath
     * 
     * @return String
     * @author taitt
     */
    public String getLogoPath() {
        return logoPath;
    }

    /**
     * Set logoPath
     * 
     * @param logoPath
     *            type String
     * @return
     * @author taitt
     */
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    /**
     * Get logoRepoId
     * 
     * @return String
     * @author taitt
     */
    public Long getLogoRepoId() {
        return logoRepoId;
    }

    /**
     * Set logoRepoId
     * 
     * @param logoRepoId
     *            type String
     * @return
     * @author taitt
     */
    public void setLogoRepoId(Long logoRepoId) {
        this.logoRepoId = logoRepoId;
    }

    /**
     * Get categoryId
     * 
     * @return Long
     * @author taitt
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Set categoryId
     * 
     * @param categoryId
     *            type Long
     * @return
     * @author taitt
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get categoryName
     * 
     * @return String
     * @author taitt
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set categoryName
     * 
     * @param categoryName
     *            type String
     * @return
     * @author taitt
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Get serviceImagePath
     * 
     * @return String
     * @author taitt
     */
    public String getServiceImagePath() {
        return serviceImagePath;
    }

    /**
     * Set serviceImagePath
     * 
     * @param serviceImagePath
     *            type String
     * @return
     * @author taitt
     */
    public void setServiceImagePath(String serviceImagePath) {
        this.serviceImagePath = serviceImagePath;
    }

    /**
     * Get serviceImageRepoId
     * 
     * @return Long
     * @author taitt
     */
    public Long getServiceImageRepoId() {
        return serviceImageRepoId;
    }

    /**
     * Set serviceImageRepoId
     * 
     * @param serviceImageRepoId
     *            type Long
     * @return
     * @author taitt
     */
    public void setServiceImageRepoId(Long serviceImageRepoId) {
        this.serviceImageRepoId = serviceImageRepoId;
    }

    /**
     * Get assingedTo
     * 
     * @return List<ResAssigneDto>
     * @author taitt
     */
    public List<ResAssigneDto> getAssingedTo() {
        return assingedTo;
    }

    /**
     * Set assingedTo
     * 
     * @param assingedTo
     *            type List<ResAssigneDto>
     * @return
     * @author taitt
     */
    public void setAssingedTo(List<ResAssigneDto> assingedTo) {
        this.assingedTo = assingedTo;
    }

    /**
     * Get relatedEmails
     * 
     * @return List<ResAssigneDto>
     * @author taitt
     */
    public List<ResAssigneDto> getRelatedEmails() {
        return relatedEmails;
    }

    /**
     * Set relatedEmails
     * 
     * @param relatedEmails
     *            type List<ResAssigneDto>
     * @return
     * @author taitt
     */
    public void setRelatedEmails(List<ResAssigneDto> relatedEmails) {
        this.relatedEmails = relatedEmails;
    }

    /**
     * Get staffId
     * 
     * @return Long
     * @author taitt
     */
    public Long getStaffId() {
        return staffId;
    }

    /**
     * Set staffId
     * 
     * @param staffId
     *            type Long
     * @return
     * @author taitt
     */
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    /**
     * Get processStatusCode
     * 
     * @return String
     * @author taitt
     */
    public String getProcessStatusCode() {
        return processStatusCode;
    }

    /**
     * Set processStatusCode
     * 
     * @param processStatusCode
     *            type String
     * @return
     * @author taitt
     */
    public void setProcessStatusCode(String processStatusCode) {
        this.processStatusCode = processStatusCode;
    }

    /**
     * Get majorFileVersion
     * 
     * @return Long
     * @author taitt
     */
    public Long getMajorFileVersion() {
        return majorFileVersion;
    }

    /**
     * Set majorFileVersion
     * 
     * @param majorFileVersion
     *            type Long
     * @return
     * @author taitt
     */
    public void setMajorFileVersion(Long majorFileVersion) {
        this.majorFileVersion = majorFileVersion;
    }

    /**
     * Get minorFileVersion
     * 
     * @return Long
     * @author taitt
     */
    public Long getMinorFileVersion() {
        return minorFileVersion;
    }

    /**
     * Set minorFileVersion
     * 
     * @param minorFileVersion
     *            type Long
     * @return
     * @author taitt
     */
    public void setMinorFileVersion(Long minorFileVersion) {
        this.minorFileVersion = minorFileVersion;
    }

    /**
     * Get businessId
     * 
     * @return Long
     * @author taitt
     */
    public Long getBusinessId() {
        return businessId;
    }

    /**
     * Set businessId
     * 
     * @param businessId
     *            type Long
     * @return
     * @author taitt
     */
    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public Date getDueDated() {
		return dueDated;
	}

	public void setDueDated(Date dueDated) {
		this.dueDated = dueDated;
	}

	public String getDocState() {
		return docState;
	}

	public void setDocState(String docState) {
		this.docState = docState;
	}

	public String getDocStateCode() {
		return docStateCode;
	}

	public void setDocStateCode(String docStateCode) {
		this.docStateCode = docStateCode;
	}

    
    /**
     * Get created
     * @return String
     * @author taitt
     */
    public String getCreated() {
        return created;
    }

    
    /**
     * Set created
     * @param   created
     *          type String
     * @return
     * @author  taitt
     */
    public void setCreated(String created) {
        this.created = created;
    }

    
    /**
     * Get createdDate
     * @return Date
     * @author taitt
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    
    /**
     * Set createdDate
     * @param   createdDate
     *          type Date
     * @return
     * @author  taitt
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

	public String getSubmitByName() {
		return submitByName;
	}

	public void setSubmitByName(String submitByName) {
		this.submitByName = submitByName;
	}

	public String getSubmitByPosition() {
		return submitByPosition;
	}

	public void setSubmitByPosition(String submitByPosition) {
		this.submitByPosition = submitByPosition;
	}

	public Long getSubmitById() {
		return submitById;
	}

	public void setSubmitById(Long submitById) {
		this.submitById = submitById;
	}

	public List<ResAssigneDto> getReferenceEmails() {
		return referenceEmails;
	}

	public void setReferenceEmails(List<ResAssigneDto> referenceEmails) {
		this.referenceEmails = referenceEmails;
	}

	public String getIsReference() {
		return isReference;
	}

	public void setIsReference(String isReference) {
		this.isReference = isReference;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getValidJson() {
		return validJson;
	}

	public void setValidJson(String validJson) {
		this.validJson = validJson;
	}

    
    public OZDocMainFileValidObjectDto getErrorFieldFile() {
        return errorFieldFile;
    }

    
    public void setErrorFieldFile(OZDocMainFileValidObjectDto errorFieldFile) {
        this.errorFieldFile = errorFieldFile;
    }

    
    public String getIsSaveEform() {
        return isSaveEform;
    }

    
    public void setIsSaveEform(String isSaveEform) {
        this.isSaveEform = isSaveEform;
    }

    
    public Integer getToolBarOzd() {
        return toolBarOzd;
    }

    
    public void setToolBarOzd(Integer toolBarOzd) {
        this.toolBarOzd = toolBarOzd;
    }

    
    public Long getIsEditAssignTo() {
        return isEditAssignTo;
    }

    
    public void setIsEditAssignTo(Long isEditAssignTo) {
        this.isEditAssignTo = isEditAssignTo;
    }

    
    public boolean getIsDataArchive() {
        return isDataArchive;
    }

    
    public void setIsDataArchive(boolean isDataArchive) {
        this.isDataArchive = isDataArchive;
    }

}
