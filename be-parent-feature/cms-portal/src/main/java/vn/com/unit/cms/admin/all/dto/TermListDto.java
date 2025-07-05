package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import vn.com.unit.cms.admin.all.enumdef.TermTypeEnum;

public class TermListDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String title;
    
    private String status;

    /** description */
    private String description;

    /** create date */
    private Date createDate;

    /** creator */
    private String createBy;

    /** termValue */
    private int termValue;

    /** termType */
    private String termType;

    /** termTypeName */
    private String termTypeName;
    
    /** loanTerm */
    private boolean loanTerm;
    
    /** unitType */
    private String unitType;
    
    /** unitValue */
    private String unitValue;

    /**
     * get id
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
     * get title
     * 
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * set title
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
     * Get termValue
     * 
     * @return int
     * @author hand
     */
    public int getTermValue() {
        return termValue;
    }

    /**
     * Set termValue
     * 
     * @param termValue
     *            type int
     * @return
     * @author hand
     */
    public void setTermValue(int termValue) {
        this.termValue = termValue;
    }

    /**
     * Get termType
     * 
     * @return String
     * @author hand
     */
    public String getTermType() {
        return termType;
    }

    /**
     * Set termType
     * 
     * @param termType
     *            type String
     * @return
     * @author hand
     */
    public void setTermType(String termType) {
        this.termType = termType;
    }

    /**
     * Get termTypeName
     * 
     * @return String
     * @author hand
     */
    public String getTermTypeName() {
        // set termTypeName
        for (TermTypeEnum termEnum : TermTypeEnum.values()) {
            if (StringUtils.equals(termType, termEnum.toString())) {
                termTypeName = termEnum.getName();
                break;
            }
        }

        return termTypeName;
    }

    /**
     * Set termTypeName
     * 
     * @param termTypeName
     *            type String
     * @return
     * @author hand
     */
    public void setTermTypeName(String termTypeName) {
        this.termTypeName = termTypeName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

}
