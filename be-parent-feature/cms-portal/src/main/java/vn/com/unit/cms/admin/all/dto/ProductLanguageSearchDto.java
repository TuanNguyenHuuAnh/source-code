/*******************************************************************************
 * Class        ：ProductLanguageSearchDto
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

//import org.apache.commons.lang.StringUtils;

//import vn.com.unit.cms.admin.all.enumdef.ProductProcessEnum;

/**
 * ProductLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductLanguageSearchDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String name;

    /** subTitle */
    private String subTitle;

    /** imageUrl */
    private String imageUrl;

    /** physicalImg */
    private String physicalImg;

    /** keyWord */
    private String keyWord;

    /** headlines */
    private Long headlines;

    /** enabled */
    private Long enabled;

    /** title */
    private String title;

    /** shortContent */
    private String shortContent;

    /** categoryName */
    private String categoryName;

    /** typeName */
    private String typeName;

    /** processId */
    private Long processId;

    /** status */
    private Integer status;

    /** description */
    private String description;

    /** createDate */
    private Date createDate;

    /** status */
    private String statusName;

    /** status */
    private String categorySubName;

    /** createBy */
    private String createBy;

    /** approvedBy */
    private String approvedBy;

    /** approvedDate */
    private Date approvedDate;

    /** publishedBy */
    private String publishedBy;

    /** publishedDate */
    private Date publishedDate;

    private Integer numberFaqs;

    private boolean microsite;

    private boolean highlights;

    private boolean lending;

    private boolean priority;

    private boolean highlightsMathExpress;

    private String toolName;

    private boolean showForm;

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
     * Get subTitle
     * 
     * @return String
     * @author hand
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * Set subTitle
     * 
     * @param subTitle
     *            type String
     * @return
     * @author hand
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
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
     * Get keyWord
     * 
     * @return String
     * @author hand
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Set keyWord
     * 
     * @param keyWord
     *            type String
     * @return
     * @author hand
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Get headlines
     * 
     * @return Long
     * @author hand
     */
    public Long getHeadlines() {
        return headlines;
    }

    /**
     * Set headlines
     * 
     * @param headlines
     *            type Long
     * @return
     * @author hand
     */
    public void setHeadlines(Long headlines) {
        this.headlines = headlines;
    }

    /**
     * Get enabled
     * 
     * @return Long
     * @author hand
     */
    public Long getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * 
     * @param enabled
     *            type Long
     * @return
     * @author hand
     */
    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    /**
     * Get title
     * 
     * @return String
     * @author hand
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * 
     * @param title
     *            type String
     * @return
     * @author hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get shortContent
     * 
     * @return String
     * @author hand
     */
    public String getShortContent() {
        return shortContent;
    }

    /**
     * Set shortContent
     * 
     * @param shortContent
     *            type String
     * @return
     * @author hand
     */
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    /**
     * Get categoryName
     * 
     * @return String
     * @author hand
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
     * @author hand
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Get typeName
     * 
     * @return String
     * @author hand
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Set typeName
     * 
     * @param typeName
     *            type String
     * @return
     * @author hand
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
     * @return the processId
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * @param processId
     *            the processId to set
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * Get createDate
     * 
     * @return Date
     * @author hand
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * 
     * @param createDate
     *            type Date
     * @return
     * @author hand
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get statusName
     * 
     * @return String
     * @author hand
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Set statusName
     * 
     * @param statusName
     *            type String
     * @return
     * @author hand
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Get categorySubName
     * 
     * @return String
     * @author hand
     */
    public String getCategorySubName() {
        return categorySubName;
    }

    /**
     * Set categorySubName
     * 
     * @param categorySubName
     *            type String
     * @return
     * @author hand
     */
    public void setCategorySubName(String categorySubName) {
        this.categorySubName = categorySubName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     * Get numberFaqs
     * 
     * @return Integer
     * @author taitm
     */
    public Integer getNumberFaqs() {
        return numberFaqs;
    }

    /**
     * Set numberFaqs
     * 
     * @param numberFaqs
     *            type Integer
     * @return
     * @author taitm
     */
    public void setNumberFaqs(Integer numberFaqs) {
        this.numberFaqs = numberFaqs;
    }

    public boolean isMicrosite() {
        return microsite;
    }

    public void setMicrosite(boolean microsite) {
        this.microsite = microsite;
    }

    /**
     * Get highlights
     * 
     * @return boolean
     * @author taitm
     */
    public boolean isHighlights() {
        return highlights;
    }

    /**
     * Set highlights
     * 
     * @param highlights
     *            type boolean
     * @return
     * @author taitm
     */
    public void setHighlights(boolean highlights) {
        this.highlights = highlights;
    }

    /**
     * Get lending
     * 
     * @return boolean
     * @author taitm
     */
    public boolean isLending() {
        return lending;
    }

    /**
     * Set lending
     * 
     * @param lending
     *            type boolean
     * @return
     * @author taitm
     */
    public void setLending(boolean lending) {
        this.lending = lending;
    }

    /**
     * Get priority
     * 
     * @return boolean
     * @author taitm
     */
    public boolean isPriority() {
        return priority;
    }

    /**
     * Set priority
     * 
     * @param priority
     *            type boolean
     * @return
     * @author taitm
     */
    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    /**
     * Get highlightsMathExpress
     * 
     * @return boolean
     * @author taitm
     */
    public boolean isHighlightsMathExpress() {
        return highlightsMathExpress;
    }

    /**
     * Set highlightsMathExpress
     * 
     * @param highlightsMathExpress
     *            type boolean
     * @return
     * @author taitm
     */
    public void setHighlightsMathExpress(boolean highlightsMathExpress) {
        this.highlightsMathExpress = highlightsMathExpress;
    }

    /**
     * Get toolName
     * 
     * @return String
     * @author taitm
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Set toolName
     * 
     * @param toolName
     *            type String
     * @return
     * @author taitm
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    /**
     * Get showForm
     * 
     * @return boolean
     * @author taitm
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
     * @author taitm
     */
    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
    }

}
