package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_term")
public class Term extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_TERM")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "term_value")
    private int termValue;

    @Column(name = "term_type")
    private String termType;

    @Column(name = "description")
    private String description;

    @Column(name = "sort_order")
    private int sortOrder;

    @Column(name = "is_loan_term")
    private boolean loanTerm;
    
    @Column(name = "term_comment")
    private String termComment;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "approve_by")
    private String approveBy;
    
    @Column(name = "approve_date")
    private Date approveDate;
    
    @Column(name = "publish_by")
    private String publishBy;
    
    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "from_value")
    private Long fromValue;
    
    @Column(name = "to_value")
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

    public String getDescription() {
        return description;
    }

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
     * Get name
     * 
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * 
     * @param name
     *            type String
     * @return
     * @author hand
     */
    public void setName(String name) {
        this.name = name;
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

    public String getTermComment() {
        return termComment;
    }

    public void setTermComment(String termComment) {
        this.termComment = termComment;
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
