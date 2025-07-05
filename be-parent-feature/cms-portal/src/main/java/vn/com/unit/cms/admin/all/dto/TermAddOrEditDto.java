package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermAddOrEditDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** description */
    private String description;

    /** sort order */
    private int sortOrder;

    /** create date */
    private Date createDate;

    /** update date */
    private Date updateDate;

    /** delete date */
    private Date deleteDate;

    /** creator */
    private String createBy;

    /** updater */
    private String updateBy;

    /** deleter */
    private String deleteBy;

    /** name */
    private String name;

    /** termValue */
    private int termValue;

    /** termType */
    private String termType;
    
    /** termName */
    private String termName;

    /** language list */
    private List<TermLanguageDto> termLanguageList;

    /** url */
    private String url;
    
    private Locale locale;
    
    /** loanTerm */
    private boolean loanTerm;
    
    private String comment;
    
    private String status;
    
    private String approveBy;
    
    private Date approveDate;
    
    private String publishBy;
    
    private Date publishDate;
    
    /** unitType */
    private String unitType;
    
    private Long fromValue;
    
    private Long toValue;

    /**
     * get Id
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set id
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get code
     * 
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * set code
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * get description
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get sort order
     * 
     * @return
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * set sort order
     * 
     * @param sortOrder
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * get create date
     * 
     * @return
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * set create date
     * 
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * get update date
     * 
     * @return
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * set update date
     * 
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * get delete date
     * 
     * @return
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * set delete date
     * 
     * @param deleteDate
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    /**
     * get creator
     * 
     * @return
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * set creator
     * 
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * get updater
     * 
     * @return
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * set updater
     * 
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * get deleter
     * 
     * @return
     */
    public String getDeleteBy() {
        return deleteBy;
    }

    /**
     * set deleter
     * 
     * @param deleteBy
     */
    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    /**
     * get language list
     * 
     * @return
     */
    public List<TermLanguageDto> getTermLanguageList() {
        return termLanguageList;
    }

    /**
     * set language list
     * 
     * @param termLanguageList
     */
    public void setTermLanguageList(List<TermLanguageDto> termLanguageList) {
        this.termLanguageList = termLanguageList;
    }

    /**
     * get url
     * 
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * set url
     * 
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get name
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  hand
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get termValue
     * @return int
     * @author hand
     */
    public int getTermValue() {
        return termValue;
    }

    /**
     * Set termValue
     * @param   termValue
     *          type int
     * @return
     * @author  hand
     */
    public void setTermValue(int termValue) {
        this.termValue = termValue;
    }

    /**
     * Get termType
     * @return String
     * @author hand
     */
    public String getTermType() {
        return termType;
    }

    /**
     * Set termType
     * @param   termType
     *          type String
     * @return
     * @author  hand
     */
    public void setTermType(String termType) {
        this.termType = termType;
    }

    /**
     * Get locale
     * @return Locale
     * @author hand
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Set locale
     * @param   locale
     *          type Locale
     * @return
     * @author  hand
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Get termName
     * @return String
     * @author hand
     */
    public String getTermName() {
        return termName;
    }

    /**
     * Set termName
     * @param   termName
     *          type String
     * @return
     * @author  hand
     */
    public void setTermName(String termName) {
        this.termName = termName;
    }

    /**
     * @return the loanTerm
     */
    public boolean isLoanTerm() {
        return loanTerm;
    }

    /**
     * @param loanTerm the loanTerm to set
     */
    public void setLoanTerm(boolean loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public Long getFromValue() {
		return fromValue;
	}

	public void setFromValue(Long fromValue) {
		this.fromValue = fromValue;
	}

	public Long getToValue() {
		return toValue;
	}

	public void setToValue(Long toValue) {
		this.toValue = toValue;
	}

}
