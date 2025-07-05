/*******************************************************************************
 * Class        ：JobDto
 * Created date ：2017/03/06
 * Lasted date  ：2017/03/06
 * Author       ：TranLTH
 * Change log   ：2017/03/06：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.util.Util;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

/**
 * JobDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class JobDto extends DocumentActionReq {
    private Long jobId;
    
    private String careerId;
    
    private String jobCode;
   
    private String jobName;
    
    @Size(max=65535)
    private String note;        
    
    private String position;
    
    private String location;
    
    private Boolean urgent;
    
    private Boolean enabled;
    
    private String sort;
    
    private Date expiryDate;
    
    private String catOfficialName;
    
    private String orgCode;
    
    private Long ownerId;
    
    private Long ownerBranchId;
    
    private Long ownerSectionId;
    
    private Long assignerId;
    
    private Long assignerBranchId;
    
    private Long assignerSectionId;
    
    private String statusCode;
    
    private Long processId;   
    
    private String comment;
    
    private Long referenceId;
    
    private String url;
    
    private Date createDate;
    
    private String statusCodeDisp;
    
    private String referenceType;
    
    private Date effectiveDate;
    
    private String salary;
    
    
    @Valid
    private List<JobLanguageDto> jobLanguageDtos;
    
    @Valid
    private List<HistoryApproveDto> historyApproveDtos;
    
    private boolean action;
    
    private String positionName;
    
    private String experience;
    
    private List<String> listLocation;    
    
    private String division;
    
    private String divisionName;
    
    private List<String> listCareer;
    
    private int recruitmentNumber;
    
    private String jobTitle;
    private String linkAlias;
    private String languageCode;
    private String createBy;
    private Date expiryDateFrom;
    private Date expiryDateTo;
    
    /**
     * Get jobLanguageDtos
     * @return List<JobLanguageDto>
     * @author TranLTH
     */
    public List<JobLanguageDto> getJobLanguageDtos() {
        return jobLanguageDtos;
    }

    /**
     * Set jobLanguageDtos
     * @param   jobLanguageDtos
     *          type List<JobLanguageDto>
     * @return
     * @author  TranLTH
     */
    public void setJobLanguageDtos(List<JobLanguageDto> jobLanguageDtos) {
        this.jobLanguageDtos = jobLanguageDtos;
    }
    
    /**
     * Get historyApproveDtos
     * @return List<HistoryApproveDto>
     * @author TranLTH
     */
    public List<HistoryApproveDto> getHistoryApproveDtos() {
        return historyApproveDtos;
    }

    /**
     * Set historyApproveDtos
     * @param   historyApproveDtos
     *          type List<HistoryApproveDto>
     * @return
     * @author  TranLTH
     */
    public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
        this.historyApproveDtos = historyApproveDtos;
    }

    /**
     * Get jobId
     * @return Long
     * @author TranLTH
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * Set jobId
     * @param   jobId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * Get jobCode
     * @return String
     * @author TranLTH
     */
    public String getJobCode() {
        return jobCode;
    }

    /**
     * Set jobCode
     * @param   jobCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setJobCode(String jobCode) {
        this.jobCode = CmsUtils.toUppercase(jobCode);
    }

    /**
     * Get jobName
     * @return String
     * @author TranLTH
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Set jobName
     * @param   jobName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }    
    /**
     * Get careerId
     * @return String
     * @author TranLTH
     */
    public String getCareerId() {
        return careerId;
    }

    /**
     * Set careerId
     * @param   careerId
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCareerId(String careerId) {
        this.careerId = careerId;
    }

    /**
     * Get note
     * @return String
     * @author TranLTH
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * Get position
     * @return String
     * @author TranLTH
     */
    public String getPosition() {
        return position;
    }

    /**
     * Set position
     * @param   position
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Get location
     * @return String
     * @author TranLTH
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set location
     * @param   location
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get urgent
     * @return Boolean
     * @author TranLTH
     */
    public Boolean getUrgent() {
        return urgent;
    }

    /**
     * Set urgent
     * @param   urgent
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    /**
     * Get enabled
     * @return Boolean
     * @author TranLTH
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get sort
     * @return String
     * @author TranLTH
     */
    public String getSort() {
        return sort;
    }

    /**
     * Set sort
     * @param   sort
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * Get expiryDate
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Set expiryDate
     * @param   expiryDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Get catOfficialName
     * @return String
     * @author TranLTH
     */
    public String getCatOfficialName() {
        return catOfficialName;
    }

    /**
     * Set catOfficialName
     * @param   catOfficialName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCatOfficialName(String catOfficialName) {
        this.catOfficialName = catOfficialName;
    }

    /**
     * Get orgCode
     * @return String
     * @author TranLTH
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * Set orgCode
     * @param   orgCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = CmsUtils.toUppercase(orgCode);
    }

    /**
     * Get ownerId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Set ownerId
     * @param   ownerId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get ownerBranchId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerBranchId() {
        return ownerBranchId;
    }

    /**
     * Set ownerBranchId
     * @param   ownerBranchId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerBranchId(Long ownerBranchId) {
        this.ownerBranchId = ownerBranchId;
    }

    /**
     * Get ownerSectionId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerSectionId() {
        return ownerSectionId;
    }

    /**
     * Set ownerSectionId
     * @param   ownerSectionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerSectionId(Long ownerSectionId) {
        this.ownerSectionId = ownerSectionId;
    }

    /**
     * Get assignerId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerId() {
        return assignerId;
    }

    /**
     * Set assignerId
     * @param   assignerId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerId(Long assignerId) {
        this.assignerId = assignerId;
    }

    /**
     * Get assignerBranchId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerBranchId() {
        return assignerBranchId;
    }

    /**
     * Set assignerBranchId
     * @param   assignerBranchId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerBranchId(Long assignerBranchId) {
        this.assignerBranchId = assignerBranchId;
    }

    /**
     * Get assignerSectionId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerSectionId() {
        return assignerSectionId;
    }

    /**
     * Set assignerSectionId
     * @param   assignerSectionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerSectionId(Long assignerSectionId) {
        this.assignerSectionId = assignerSectionId;
    }

    /**
     * Get statusCode
     * @return String
     * @author TranLTH
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * @param   statusCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get processId
     * @return Long
     * @author TranLTH
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get comment
     * @return String
     * @author TranLTH
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     * @param   comment
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * Get referenceId
     * @return Long
     * @author TranLTH
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * @param   referenceId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get url
     * @return String
     * @author TranLTH
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get createDate
     * @return Date
     * @author TranLTH
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get statusCodeDisp
     * @return String
     * @author TranLTH
     */
    public String getStatusCodeDisp() {
        return statusCodeDisp;
    }

    /**
     * Set statusCodeDisp
     * @param   statusCodeDisp
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatusCodeDisp(String statusCodeDisp) {
        this.statusCodeDisp = statusCodeDisp;
    }

    /**
     * Get action
     * @return boolean
     * @author TranLTH
     */
    public boolean isAction() {
        return action;
    }

    /**
     * Set action
     * @param   action
     *          type boolean
     * @return
     * @author  TranLTH
     */
    public void setAction(boolean action) {
        this.action = action;
    }

    /**
     * Get referenceType
     * @return String
     * @author TranLTH
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Set referenceType
     * @param   referenceType
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * Get positionName
     * @return String
     * @author TranLTH
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Set positionName
     * @param   positionName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * Get experience
     * @return String
     * @author TranLTH
     */
    public String getExperience() {
        return experience;
    }

    /**
     * Set experience
     * @param   experience
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * Get listLocation
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getListLocation() {
        return listLocation;
    }

    /**
     * Set listLocation
     * @param   listLocation
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setListLocation(List<String> listLocation) {
        this.listLocation = listLocation;
    }

    /**
     * Get division
     * @return String
     * @author TranLTH
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division
     * @param   division
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get divisionName
     * @return String
     * @author TranLTH
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Set divisionName
     * @param   divisionName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Get listCareer
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getListCareer() {
        return listCareer;
    }

    /**
     * Set listCareer
     * @param   listCareer
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setListCareer(List<String> listCareer) {
        this.listCareer = listCareer;
    }

    /**
     * Get effectiveDate
     * @return Date
     * @author TranLTH
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Set effectiveDate
     * @param   effectiveDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Get salary
     * @return String
     * @author TranLTH
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Set salary
     * @param   salary
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * Get jobTitle
     * @return String
     * @author TranLTH
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Set jobTitle
     * @param   jobTitle
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Get linkAlias
     * @return String
     * @author TranLTH
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * Set linkAlias
     * @param   linkAlias
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * Get languageCode
     * @return String
     * @author TranLTH
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get createBy
     * @return String
     * @author TranLTH
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Set createBy
     * @param   createBy
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Get expiryDateFrom
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDateFrom() {
        return expiryDateFrom;
    }

    /**
     * Set expiryDateFrom
     * @param   expiryDateFrom
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDateFrom(Date expiryDateFrom) {
        this.expiryDateFrom = expiryDateFrom;
    }

    /**
     * Get expiryDateTo
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDateTo() {
        return expiryDateTo;
    }

    /**
     * Set expiryDateTo
     * @param   expiryDateTo
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDateTo(Date expiryDateTo) {
        this.expiryDateTo = expiryDateTo;
    }

	/**
	 * @return the recruitmentNumber
	 */
	public int getRecruitmentNumber() {
		return recruitmentNumber;
	}

	/**
	 * @param recruitmentNumber the recruitmentNumber to set
	 */
	public void setRecruitmentNumber(int recruitmentNumber) {
		this.recruitmentNumber = recruitmentNumber;
	}
}