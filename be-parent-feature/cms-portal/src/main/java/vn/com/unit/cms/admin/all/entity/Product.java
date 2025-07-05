/*******************************************************************************
 * Class        ：Product
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractProcessTracking;

/**
 * Product
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_product")
public class Product extends AbstractProcessTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerTypeId;

    @Column(name = "m_product_category_sub_id")
    private Long categorySubId;

    @Column(name = "m_product_category_id")
    private Long productCategoryId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "views")
    private Long views;

    @Column(name = "tags")
    private String tags;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "process_instance_id")
    private Long processInstanceId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "owner_branch_id")
    private Long ownerBranchId;

    @Column(name = "owner_section_id")
    private Long ownerSectionId;

    @Column(name = "assigner_id")
    private Long assignerId;

    @Column(name = "assigner_branch_id")
    private Long assignerBranchId;

    @Column(name = "assigner_section_id")
    private Long assignerSectionId;

    @Column(name = "physical_img")
    private String physicalImg;

    @Column(name = "interest_rates")
    private float interestRates;

    @Column(name = "max_loan_amount")
    private BigDecimal maxLoanAmount;

    @Column(name = "m_term_code")
    private String termCode;

    @Column(name = "term_value")
    private int termValue;

    @Column(name = "term_type")
    private String termType;

    @Column(name = "banner_desktop")
    private Long bannerDesktop;

    @Column(name = "banner_mobile")
    private Long bannerMobile;

    @Column(name = "show_form")
    private boolean showForm;

    @Column(name = "is_lending")
    private boolean lending;

    @Column(name = "is_microsite")
    private boolean microsite;

    @Column(name = "link_alias")
    private String linkAlias;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "published_by")
    private String publishedBy;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "is_highlights")
    private Boolean highlights;

    @Column(name = "is_priority")
    private Boolean priority;

    @Column(name = "product_comment")
    private String productComment;

    @Column(name = "before_id")
    private Long beforeId;

    @Column(name = "icon_img")
    private String iconImg;

    @Column(name = "physical_icon")
    private String physicalIcon;

    @Column(name = "math_expression")
    private Long mathExpression;

    @Column(name = "is_highlights_math_express")
    private boolean highlightsMathExpress;
    
    @Column(name = "rate")
    private float rate;

    /**
     * Get id
     * 
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * 
     * @param id
     *            type Long
     * @return
     * @author hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get customerTypeId
     * 
     * @return Long
     * @author hand
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * Set customerTypeId
     * 
     * @param customerTypeId
     *            type Long
     * @return
     * @author hand
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    /**
     * Get productCategoryId
     * 
     * @return Long
     * @author hand
     */
    public Long getProductCategoryId() {
        return productCategoryId;
    }

    /**
     * Set productCategoryId
     * 
     * @param productCategoryId
     *            type Long
     * @return
     * @author hand
     */
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    /**
     * Get code
     * 
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * 
     * @param code
     *            type String
     * @return
     * @author hand
     */
    public void setCode(String code) {
        this.code = code;
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
     * Get description
     * 
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get note
     * 
     * @return String
     * @author hand
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * 
     * @param note
     *            type String
     * @return
     * @author hand
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get Sort
     * 
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set Sort
     * 
     * @param Sort
     *            type Long
     * @return
     * @author hand
     */
    public void setSort(Long Sort) {
        this.sort = Sort;
    }

    /**
     * Get enabled
     * 
     * @return boolean
     * @author hand
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * 
     * @param enabled
     *            type boolean
     * @return
     * @author hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get imageUrl
     * 
     * @return String
     * @author hand
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Set imageUrl
     * 
     * @param imageUrl
     *            type String
     * @return
     * @author hand
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Get imageName
     * 
     * @return String
     * @author hand
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Set imageName
     * 
     * @param imageName
     *            type String
     * @return
     * @author hand
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Get views
     * 
     * @return Long
     * @author hand
     */
    public Long getViews() {
        return views;
    }

    /**
     * Set views
     * 
     * @param views
     *            type Long
     * @return
     * @author hand
     */
    public void setViews(Long views) {
        this.views = views;
    }

    /**
     * Get tags
     * 
     * @return String
     * @author hand
     */
    public String getTags() {
        return tags;
    }

    /**
     * Set tags
     * 
     * @param tags
     *            type String
     * @return
     * @author hand
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * Get processId
     * 
     * @return Long
     * @author hand
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * 
     * @param processId
     *            type Long
     * @return
     * @author hand
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get status
     * 
     * @return Integer
     * @author hand
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type Integer
     * @return
     * @author hand
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Get processInstanceId
     * 
     * @return Long
     * @author hand
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Set processInstanceId
     * 
     * @param processInstanceId
     *            type Long
     * @return
     * @author hand
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * Get ownerId
     * 
     * @return Long
     * @author hand
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Set ownerId
     * 
     * @param ownerId
     *            type Long
     * @return
     * @author hand
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get ownerBranchId
     * 
     * @return Long
     * @author hand
     */
    public Long getOwnerBranchId() {
        return ownerBranchId;
    }

    /**
     * Set ownerBranchId
     * 
     * @param ownerBranchId
     *            type Long
     * @return
     * @author hand
     */
    public void setOwnerBranchId(Long ownerBranchId) {
        this.ownerBranchId = ownerBranchId;
    }

    /**
     * Get ownerSectionId
     * 
     * @return Long
     * @author hand
     */
    public Long getOwnerSectionId() {
        return ownerSectionId;
    }

    /**
     * Set ownerSectionId
     * 
     * @param ownerSectionId
     *            type Long
     * @return
     * @author hand
     */
    public void setOwnerSectionId(Long ownerSectionId) {
        this.ownerSectionId = ownerSectionId;
    }

    /**
     * Get assignerId
     * 
     * @return Long
     * @author hand
     */
    public Long getAssignerId() {
        return assignerId;
    }

    /**
     * Set assignerId
     * 
     * @param assignerId
     *            type Long
     * @return
     * @author hand
     */
    public void setAssignerId(Long assignerId) {
        this.assignerId = assignerId;
    }

    /**
     * Get assignerBranchId
     * 
     * @return Long
     * @author hand
     */
    public Long getAssignerBranchId() {
        return assignerBranchId;
    }

    /**
     * Set assignerBranchId
     * 
     * @param assignerBranchId
     *            type Long
     * @return
     * @author hand
     */
    public void setAssignerBranchId(Long assignerBranchId) {
        this.assignerBranchId = assignerBranchId;
    }

    /**
     * Get assignerSectionId
     * 
     * @return Long
     * @author hand
     */
    public Long getAssignerSectionId() {
        return assignerSectionId;
    }

    /**
     * Set assignerSectionId
     * 
     * @param assignerSectionId
     *            type Long
     * @return
     * @author hand
     */
    public void setAssignerSectionId(Long assignerSectionId) {
        this.assignerSectionId = assignerSectionId;
    }

    /**
     * Get physicalImg
     * 
     * @return String
     * @author hand
     */
    public String getPhysicalImg() {
        return physicalImg;
    }

    /**
     * Set physicalImg
     * 
     * @param physicalImg
     *            type String
     * @return
     * @author hand
     */
    public void setPhysicalImg(String physicalImg) {
        this.physicalImg = physicalImg;
    }

    /**
     * Get categorySubId
     * 
     * @return Long
     * @author hand
     */
    public Long getCategorySubId() {
        return categorySubId;
    }

    /**
     * Set categorySubId
     * 
     * @param categorySubId
     *            type Long
     * @return
     * @author hand
     */
    public void setCategorySubId(Long categorySubId) {
        this.categorySubId = categorySubId;
    }

    /**
     * Get maxLoanAmount
     * 
     * @return BigDecimal
     * @author hand
     */
    public BigDecimal getMaxLoanAmount() {
        return maxLoanAmount;
    }

    /**
     * Set maxLoanAmount
     * 
     * @param maxLoanAmount
     *            type BigDecimal
     * @return
     * @author hand
     */
    public void setMaxLoanAmount(BigDecimal maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    /**
     * Get interestRates
     * 
     * @return float
     * @author hand
     */
    public float getInterestRates() {
        return interestRates;
    }

    /**
     * Set interestRates
     * 
     * @param interestRates
     *            type float
     * @return
     * @author hand
     */
    public void setInterestRates(float interestRates) {
        this.interestRates = interestRates;
    }

    /**
     * Get termCode
     * 
     * @return String
     * @author hand
     */
    public String getTermCode() {
        return termCode;
    }

    /**
     * Set termCode
     * 
     * @param termCode
     *            type String
     * @return
     * @author hand
     */
    public void setTermCode(String termCode) {
        this.termCode = termCode;
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
     * Get bannerDesktop
     * 
     * @return Long
     * @author hand
     */
    public Long getBannerDesktop() {
        return bannerDesktop;
    }

    /**
     * Set bannerDesktop
     * 
     * @param bannerDesktop
     *            type Long
     * @return
     * @author hand
     */
    public void setBannerDesktop(Long bannerDesktop) {
        this.bannerDesktop = bannerDesktop;
    }

    /**
     * Get bannerMobile
     * 
     * @return Long
     * @author hand
     */
    public Long getBannerMobile() {
        return bannerMobile;
    }

    /**
     * Set bannerMobile
     * 
     * @param bannerMobile
     *            type Long
     * @return
     * @author hand
     */
    public void setBannerMobile(Long bannerMobile) {
        this.bannerMobile = bannerMobile;
    }

    /**
     * Get showForm
     * 
     * @return boolean
     * @author hand
     */
    public boolean isShowForm() {
        return showForm;
    }

    /**
     * Set showForm
     * 
     * @param showForm
     *            type boolean
     * @return
     * @author hand
     */
    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
    }

    public boolean getLending() {
        return lending;
    }

    public void setLending(boolean lending) {
        this.lending = lending;
    }

    /**
     * @return the linkAlias
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * @param linkAlias
     *            the linkAlias to set
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Boolean getHighlights() {
        return highlights;
    }

    public void setHighlights(Boolean highlights) {
        this.highlights = highlights;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public String getProductComment() {
        return productComment;
    }

    public void setProductComment(String productComment) {
        this.productComment = productComment;
    }

    public Long getBeforeId() {
        return beforeId;
    }

    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getPhysicalIcon() {
        return physicalIcon;
    }

    public void setPhysicalIcon(String physicalIcon) {
        this.physicalIcon = physicalIcon;
    }

    public Long getMathExpression() {
        return mathExpression;
    }

    public void setMathExpression(Long mathExpression) {
        this.mathExpression = mathExpression;
    }

    public boolean isMicrosite() {
        return microsite;
    }

    public void setMicrosite(boolean microsite) {
        this.microsite = microsite;
    }

    public boolean isHighlightsMathExpress() {
        return highlightsMathExpress;
    }

    public void setHighlightsMathExpress(boolean highlightsMathExpress) {
        this.highlightsMathExpress = highlightsMathExpress;
    }

    /**
     * Get rate
     * 
     * @return double
     * @author taitm
     */
    public float getRate() {
        return rate;
    }

    /**
     * Set rate
     * 
     * @param rate
     *            type double
     * @return
     * @author taitm
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

}