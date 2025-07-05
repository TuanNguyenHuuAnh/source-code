/*******************************************************************************
 * Class        ：ProductEditDtol
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.ep2p.dto.JProcessStepDto;
//import vn.com.unit.util.Util;

/**
 * ProductEditDtol
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductEditDto extends DocumentActionReq {

    /** id */
    private Long id;

    /** typeId */
    @NotNull
    private Long typeId;

    /** categoryId */
    private Long categoryId;

    /** code */
    // @NotEmpty
    // @Size(min = 1, max = 30)
    private String code;

    /** name */
    // @NotEmpty
    // @Size(min = 1, max = 255)
    private String name;

    /** description */
    private String description;

    /** note */
    private String note;

    /** imageUrl */
    private String imageUrl;

    /** views */
    private Long views;

    /** enabled */
    private boolean enabled;

    /** physicalImg */
    private String physicalImg;

    /** icon name */
    private String iconImg;

    /** physical icon */
    private String physicalIcon;

    /** sort */
    private Long sort;

    /** categoryJsonHidden */
    private String categoryJsonHidden;

    /** categorySubJsonHidden */
    private String categorySubJsonHidden;

    /** beforeIdsJsonHidden */
    private String beforeIdsJsonHidden;

    /** typeName */
    private String typeName;

    /** categoryName */
    private String categoryName;

    /** processId */
    private Long processId;

    /** status */
    private Integer status;

    /** url */
    private String url;

    /** referenceId */
    private Long referenceId;

    /** referenceType */
    private String referenceType;

    /** comment */
    private String comment;

    /** requestToken */
    private String requestToken;

    /** productLanguageList */
    @Valid
    private List<ProductLanguageDto> productLanguageList;

    /** product detail */
    @Valid
    private List<ProductDetaiLanguageDto> productDetailLanguageList;

    @Valid
    private List<HistoryApproveDto> historyApproveDtos;

    /** category sub id */
    private Long categorySubId;

    /** category sub name */
    private String categorySubName;

    /** interestRates */
    private float interestRates;

    /** maxLoanAmount */
    private BigDecimal maxLoanAmount;

    /** maxLoanAmount string */
    private String maxLoanAmountStr;

    /** termCode */
    private String termCode;

    /** termValue */
    private int termValue;

    /** termType */
    private String termType;

    /** termName */
    private String termName;

    /** bannerDesktop */
    private Long bannerDesktop;

    /** bannerMobile */
    private Long bannerMobile;

    /** showForm */
    private boolean showForm;

    private boolean microsite;

    /** lending */
    private Boolean lending;

    /** linkAlias */
    private String linkAlias;

    private String createBy;

    private String approvedBy;

    private Date approvedDate;

    private String publishedBy;

    private Date publishedDate;

    private Boolean isHighlights;

    private Boolean isPriority;

    private String productComment;

    private Long beforeId;

    private String codeLanguage;

    private List<MathExpressionDto> listMathExpressionLending;

    private List<MathExpressionDto> listMathExpressionDeposits;

    private Long mathExpression;

    private boolean highlightsMathExpress;

    private float rate;

    private Integer indexLangActive;

    private String searchDto;

    /** Customer Alias */
    private String customerAlias;

    /** Status name */
    private String statusName;

    private List<JProcessStepDto> stepBtnList;

    /** button action */
    private String buttonAction;

    private String currItem;

    private String statusCode;

    private Integer oldStatus;

    private Date updateDate;

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
     * Get typeId
     * 
     * @return Long
     * @author hand
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * Get categoryId
     * 
     * @return Long
     * @author hand
     */
    public Long getCategoryId() {
        return categoryId;
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
     * Get name
     * 
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
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
     * Get note
     * 
     * @return String
     * @author hand
     */
    public String getNote() {
        return note;
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
     * Get views
     * 
     * @return Long
     * @author hand
     */
    public Long getViews() {
        return views;
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
     * Get productLanguageList
     * 
     * @return List<ProductLanguageDto>
     * @author hand
     */
    public List<ProductLanguageDto> getProductLanguageList() {
        return productLanguageList;
    }

    /**
     * Set id
     * 
     * @param id type Long
     * @return
     * @author hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set typeId
     * 
     * @param typeId type Long
     * @return
     * @author hand
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * Set categoryId
     * 
     * @param categoryId type Long
     * @return
     * @author hand
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Set code
     * 
     * @param code type String
     * @return
     * @author hand
     */
    public void setCode(String code) {
        this.code = CmsUtils.toUppercase(code);
    }

    /**
     * Set name
     * 
     * @param name type String
     * @return
     * @author hand
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set description
     * 
     * @param description type String
     * @return
     * @author hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set note
     * 
     * @param note type String
     * @return
     * @author hand
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Set imageUrl
     * 
     * @param imageUrl type String
     * @return
     * @author hand
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Set views
     * 
     * @param views type Long
     * @return
     * @author hand
     */
    public void setViews(Long views) {
        this.views = views;
    }

    /**
     * Set enabled
     * 
     * @param enabled type boolean
     * @return
     * @author hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Set productLanguageList
     * 
     * @param productLanguageList type List<ProductLanguageDto>
     * @return
     * @author hand
     */
    public void setProductLanguageList(List<ProductLanguageDto> productLanguageList) {
        this.productLanguageList = productLanguageList;
    }

    /**
     * Get categoryJsonHidden
     * 
     * @return String
     * @author hand
     */
    public String getCategoryJsonHidden() {
        return categoryJsonHidden;
    }

    /**
     * Set categoryJsonHidden
     * 
     * @param categoryJsonHidden type String
     * @return
     * @author hand
     */
    public void setCategoryJsonHidden(String categoryJsonHidden) {
        this.categoryJsonHidden = categoryJsonHidden;
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
     * @param physicalImg type String
     * @return
     * @author hand
     */
    public void setPhysicalImg(String physicalImg) {
        this.physicalImg = physicalImg;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
     * @param processId type Long
     * @return
     * @author hand
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get url
     * 
     * @return String
     * @author hand
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * 
     * @param url type String
     * @return
     * @author hand
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get sort
     * 
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set sort
     * 
     * @param sort type Long
     * @return
     * @author hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get referenceId
     * 
     * @return Long
     * @author TranLTH
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * 
     * @param referenceId type Long
     * @return
     * @author TranLTH
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get referenceType
     * 
     * @return String
     * @author TranLTH
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Set referenceType
     * 
     * @param referenceType type String
     * @return
     * @author TranLTH
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * Get historyApproveDtos
     * 
     * @return List<HistoryApproveDto>
     * @author TranLTH
     */
    public List<HistoryApproveDto> getHistoryApproveDtos() {
        return historyApproveDtos;
    }

    /**
     * Set historyApproveDtos
     * 
     * @param historyApproveDtos type List<HistoryApproveDto>
     * @return
     * @author TranLTH
     */
    public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
        this.historyApproveDtos = historyApproveDtos;
    }

    /**
     * Get comment
     * 
     * @return String
     * @author TranLTH
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     * 
     * @param comment type String
     * @return
     * @author TranLTH
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get productDetailLanguageList
     * 
     * @return List<ProductDetaiLanguageDto>
     * @author hand
     */
    public List<ProductDetaiLanguageDto> getProductDetailLanguageList() {
        return productDetailLanguageList;
    }

    /**
     * Set productDetailLanguageList
     * 
     * @param productDetailLanguageList type List<ProductDetaiLanguageDto>
     * @return
     * @author hand
     */
    public void setProductDetailLanguageList(List<ProductDetaiLanguageDto> productDetailLanguageList) {
        this.productDetailLanguageList = productDetailLanguageList;
    }

    /**
     * Get requestToken
     * 
     * @return String
     * @author hand
     */
    public String getRequestToken() {
        return requestToken;
    }

    /**
     * Set requestToken
     * 
     * @param requestToken type String
     * @return
     * @author hand
     */
    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
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
     * Get categorySubName
     * 
     * @return String
     * @author hand
     */
    public String getCategorySubName() {
        return categorySubName;
    }

    /**
     * Set categorySubId
     * 
     * @param categorySubId type Long
     * @return
     * @author hand
     */
    public void setCategorySubId(Long categorySubId) {
        this.categorySubId = categorySubId;
    }

    /**
     * Set categorySubName
     * 
     * @param categorySubName type String
     * @return
     * @author hand
     */
    public void setCategorySubName(String categorySubName) {
        this.categorySubName = categorySubName;
    }

    /**
     * Get categorySubJsonHidden
     * 
     * @return String
     * @author hand
     */
    public String getCategorySubJsonHidden() {
        return categorySubJsonHidden;
    }

    /**
     * Set categorySubJsonHidden
     * 
     * @param categorySubJsonHidden type String
     * @return
     * @author hand
     */
    public void setCategorySubJsonHidden(String categorySubJsonHidden) {
        this.categorySubJsonHidden = categorySubJsonHidden;
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
     * @param interestRates type float
     * @return
     * @author hand
     */
    public void setInterestRates(float interestRates) {
        this.interestRates = interestRates;
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
     * @param maxLoanAmount type BigDecimal
     * @return
     * @author hand
     */
    public void setMaxLoanAmount(BigDecimal maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    /**
     * Get maxLoanAmountStr
     * 
     * @return String
     * @author hand
     */
    public String getMaxLoanAmountStr() {
        return maxLoanAmountStr;
    }

    /**
     * Set maxLoanAmountStr
     * 
     * @param maxLoanAmountStr type String
     * @return
     * @author hand
     */
    public void setMaxLoanAmountStr(String maxLoanAmountStr) {
        this.maxLoanAmountStr = maxLoanAmountStr;
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
     * @param termCode type String
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
     * @param termValue type int
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
     * @param termType type String
     * @return
     * @author hand
     */
    public void setTermType(String termType) {
        this.termType = termType;
    }

    /**
     * Get termName
     * 
     * @return String
     * @author hand
     */
    public String getTermName() {
        return termName;
    }

    /**
     * Set termName
     * 
     * @param termName type String
     * @return
     * @author hand
     */
    public void setTermName(String termName) {
        this.termName = termName;
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
     * Get bannerMobile
     * 
     * @return Long
     * @author hand
     */
    public Long getBannerMobile() {
        return bannerMobile;
    }

    /**
     * Set bannerDesktop
     * 
     * @param bannerDesktop type Long
     * @return
     * @author hand
     */
    public void setBannerDesktop(Long bannerDesktop) {
        this.bannerDesktop = bannerDesktop;
    }

    /**
     * Set bannerMobile
     * 
     * @param bannerMobile type Long
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
     * @param showForm type boolean
     * @return
     * @author hand
     */
    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
    }

    public Boolean getLending() {
        return lending;
    }

    public void setLending(Boolean lending) {
        this.lending = lending;
    }

    /**
     * @return the linkAlias
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * @param linkAlias the linkAlias to set
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

    public Boolean getIsHighlights() {
        return isHighlights;
    }

    public void setIsHighlights(Boolean isHighlights) {
        this.isHighlights = isHighlights;
    }

    public Boolean getIsPriority() {
        return isPriority;
    }

    public void setIsPriority(Boolean isPriority) {
        this.isPriority = isPriority;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(String codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public String getBeforeIdsJsonHidden() {
        return beforeIdsJsonHidden;
    }

    public void setBeforeIdsJsonHidden(String beforeIdsJsonHidden) {
        this.beforeIdsJsonHidden = beforeIdsJsonHidden;
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

    public List<MathExpressionDto> getListMathExpression() {
        return listMathExpressionLending;
    }

    public List<MathExpressionDto> getListMathExpressionDeposits() {
        return listMathExpressionDeposits;
    }

    public void setListMathExpressionDeposits(List<MathExpressionDto> listMathExpressionDeposits) {
        this.listMathExpressionDeposits = listMathExpressionDeposits;
    }

    public List<MathExpressionDto> getListMathExpressionLending() {
        return listMathExpressionLending;
    }

    public void setListMathExpressionLending(List<MathExpressionDto> listMathExpressionLending) {
        this.listMathExpressionLending = listMathExpressionLending;
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
     * @return float
     * @author taitm
     */
    public float getRate() {
        return rate;
    }

    /**
     * Set rate
     * 
     * @param rate type float
     * @return
     * @author taitm
     */
    public void setRate(float rate) {
        this.rate = rate;
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
     * @param searchDto type String
     * @return
     * @author taitm
     */
    public void setSearchDto(String searchDto) {
        this.searchDto = searchDto;
    }

    /**
     * @return the customerAlias
     * @author taitm
     */
    public String getCustomerAlias() {
        return customerAlias;
    }

    /**
     * @param customerAlias the customerAlias to set
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
     * @param statusName the statusName to set
     * @author taitm
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the status
     * @author taitm
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     * @author taitm
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the stepBtnList
     * @author taitm
     */
    public List<JProcessStepDto> getStepBtnList() {
        return stepBtnList;
    }

    /**
     * @param stepBtnList the stepBtnList to set
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
     * @param buttonAction the buttonAction to set
     * @author taitm
     */
    public void setButtonAction(String buttonAction) {
        this.buttonAction = buttonAction;
    }

    /**
     * @return the currItem
     * @author taitm
     */
    public String getCurrItem() {
        return currItem;
    }

    /**
     * @param currItem the currItem to set
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
     * @param oldStatus the oldStatus to set
     * @author taitm
     */
    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
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

}
