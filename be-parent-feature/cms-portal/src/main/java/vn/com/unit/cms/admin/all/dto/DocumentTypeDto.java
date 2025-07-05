/*******************************************************************************
 * Class        ：DocumentTypeEditDto
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import vn.com.unit.cms.admin.all.entity.DocumentType;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.dto.JProcessStepDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

/**
 * DocumentTypeEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentTypeDto extends DocumentActionReq {

	/** id */
	private Long id;

	/** code */
	// @Size(min = 1, max = 30)
	// @NotEmpty
	private String code;

	/** name */
	// @Size(min = 1, max = 255)
	// @NotEmpty
	private String name;

	/** description */
	private String description;

	/** note */
	private String note;

	/** sort */
	private Long sortOrder;

	private Long customerTypeId;

	/** typeLanguageList */
	@Valid
	private List<DocumentTypeLanguageDto> typeLanguageList;

	private List<DocumentTypeDto> lstDocumentTypeSort;

	/** url */
	private String url;

	private Integer status;
	private String createdBy;
	private String approvedBy;
	private String publishedBy;
	private Long beforeId;

	private boolean enabled;
	private boolean interestRate;
	private String typeComment;

	private Integer indexLangActive;

	private String searchDto;
	
    /** processId */
    private Long processId;

	/** Customer Alias */
	private String customerAlias;

	/** Status name */
	private String statusName;

	private List<JProcessStepDto> stepBtnList;

	/** button action */
	private String buttonAction;

	/** Button id */
	private Long buttonId;
	
    private String currItem;
    
    private String statusCode;
    
    private Integer oldStatus;
    
    /** referenceId */
    private Long referenceId;

    /** referenceType */
    private String referenceType;
    
    private List<SortOrderDto> sortOderList;
    
    private Date updateDate;
    
    private String buttonCode;

	/**
	 * @param type
	 */
	public DocumentTypeDto(DocumentType type) {
		this.id = type.getId();
		this.name = type.getName();
		this.code = type.getCode();
		this.description = type.getDescription();
		this.note = type.getNote();
		this.sortOrder = type.getSortOrder();

		this.status = type.getStatus();
		this.createdBy = type.getCreateBy();
		this.approvedBy = type.getApproveBy();
		this.publishedBy = type.getPublishBy();
		this.beforeId = type.getBeforeId();
		this.enabled = type.isEnabled();
		this.typeComment = type.getTypeComment();
		this.interestRate = type.isInterestRate();
	}

	/**
	 * 
	 */
	public DocumentTypeDto() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Get code
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Get name
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get description
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get note
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Get sort
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getSortOrder() {
		return sortOrder;
	}

	/**
	 * Set id
	 * 
	 * @param id
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Set code
	 * 
	 * @param code
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setCode(String code) {
		this.code = CmsUtils.toUppercase(code);
	}

	/**
	 * Set name
	 * 
	 * @param name
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set description
	 * 
	 * @param description
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set note
	 * 
	 * @param note
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Set sort
	 * 
	 * @param currentSort
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setSortOrder(Long currentSort) {
		this.sortOrder = currentSort;
	}

	/**
	 * Get typeLanguageList
	 * 
	 * @return List<DocumentTypeLanguageDto>
	 * @author thuydtn
	 */
	public List<DocumentTypeLanguageDto> getTypeLanguageList() {
		return typeLanguageList;
	}

	/**
	 * Set typeLanguageList
	 * 
	 * @param typeLanguageList
	 *            type List<DocumentTypeLanguageDto>
	 * @return
	 * @author thuydtn
	 */
	public void setTypeLanguageList(List<DocumentTypeLanguageDto> typeLanguageList) {
		this.typeLanguageList = typeLanguageList;
	}

	/**
	 * Get url
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set url
	 * 
	 * @param url
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get customerTypeId
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * Set customerTypeId
	 * 
	 * @param customerTypeId
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getPublishedBy() {
		return publishedBy;
	}

	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	public Long getBeforeId() {
		return beforeId;
	}

	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<DocumentTypeDto> getLstDocumentTypeSort() {
		return lstDocumentTypeSort;
	}

	public void setLstDocumentTypeSort(List<DocumentTypeDto> lstDocumentTypeSort) {
		this.lstDocumentTypeSort = lstDocumentTypeSort;
	}

	public String getTypeComment() {
		return typeComment;
	}

	public void setTypeComment(String typeComment) {
		this.typeComment = typeComment;
	}

	public Integer getIndexLangActive() {
		return indexLangActive;
	}

	public void setIndexLangActive(Integer indexLangActive) {
		this.indexLangActive = indexLangActive;
	}

	/**
	 * Get searchDto
	 * 
	 * @return String
	 * @author taitm
	 */
	public String getSearchDto() {
		return searchDto;
	}

	/**
	 * Set searchDto
	 * 
	 * @param searchDto
	 *            type String
	 * @return
	 * @author taitm
	 */
	public void setSearchDto(String searchDto) {
		this.searchDto = searchDto;
	}

	public boolean isInterestRate() {
		return interestRate;
	}

	public void setInterestRate(boolean interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the customerAlias
	 * @author taitm
	 */
	public String getCustomerAlias() {
		return customerAlias;
	}

	/**
	 * @param customerAlias
	 *            the customerAlias to set
	 * @author taitm
	 */
	public void setCustomerAlias(String customerAlias) {
		this.customerAlias = customerAlias;
	}

	/**
	 * @return the statusName
	 * @author taitm
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 * @author taitm
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the stepBtnList
	 * @author taitm
	 */
	public List<JProcessStepDto> getStepBtnList() {
		return stepBtnList;
	}

	/**
	 * @param stepBtnList
	 *            the stepBtnList to set
	 * @author taitm
	 */
	public void setStepBtnList(List<JProcessStepDto> stepBtnList) {
		this.stepBtnList = stepBtnList;
	}

	/**
	 * @return the buttonAction
	 * @author taitm
	 */
	public String getButtonAction() {
		return buttonAction;
	}

	/**
	 * @param buttonAction
	 *            the buttonAction to set
	 * @author taitm
	 */
	public void setButtonAction(String buttonAction) {
		this.buttonAction = buttonAction;
	}

	/**
	 * @return the buttonId
	 * @author taitm
	 */
	public Long getButtonId() {
		return buttonId;
	}

	/**
	 * @param buttonId
	 *            the buttonId to set
	 * @author taitm
	 */
	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	/**
	 * @return the processId
	 * @author taitm
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
     * @param processId
     *            the processId to set
     * @author taitm
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * @return the currItem
     * @author taitm
     */
    public String getCurrItem() {
        return currItem;
    }

    /**
     * @param currItem
     *            the currItem to set
     * @author taitm
     */
    public void setCurrItem(String currItem) {
        this.currItem = currItem;
    }

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the oldStatus
     * @author taitm
     */
    public Integer getOldStatus() {
        return oldStatus;
    }

    /**
     * @param oldStatus
     *            the oldStatus to set
     * @author taitm
     */
    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    /**
     * @return the referenceId
     * @author taitm
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * @param referenceId
     *            the referenceId to set
     * @author taitm
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * @return the referenceType
     * @author taitm
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * @param referenceType
     *            the referenceType to set
     * @author taitm
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

	public List<SortOrderDto> getSortOderList() {
		return sortOderList;
	}

	public void setSortOderList(List<SortOrderDto> sortOderList) {
		this.sortOderList = sortOderList;
	}

    /**
     * @return the updateDate
     * @author taitm
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     * @author taitm
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}
	

}
