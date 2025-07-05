/*******************************************************************************
 * Class        ：MathExpressionDto
 * Created date ：2017/06/21
 * Lasted date  ：2017/06/21
 * Author       ：thuydtn
 * Change log   ：2017/06/21：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import vn.com.unit.cms.admin.all.entity.MathExpression;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.constant.Utils;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;
//import vn.com.unit.util.Util;

/**
 * MathExpressionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@SuppressWarnings("deprecation")
public class MathExpressionDto extends DocumentActionReq {

	private Long id;

	private String code;

	@NotEmpty
	private String name;

//	@NotEmpty
	private String expression;

	private String description;

	private String url;

	private String customerTypeName;

	private List<Long> lstCustomerTypeId;

	private String strCustomerTypeId;

	private Date availableDateFrom;

	private String strAvailableDateFrom;

	private Date availableDateTo;

	// private String strAvailableDateTo;
	private String expressionType;

	private String expressionTypeNameMessageKey;

	private Date createDate;

	private String statusName;

	private String statusText;
	
	private String statusCode;

	private Integer status;

	private Long processId;

	private Long processInstanceId;

	private String referenceType;

	private Long referenceId;

	private String comment;

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

	/** maxLoanAmount string */
	private String maxLoanAmountStr;

	private Integer termValue;

	private String termType;

	private Boolean isHighlights;

	private String linkAlias;

	private String icon;

	private String physicalIcon;

	private String searchDto;

	private List<JProcessStepDto> stepBtnList;

//	private String buttonId;

	private String customerAlias;

	private String buttonAction;

	private Integer stt;

	private BigDecimal maxLoanAmount;

	private String mathExpresssComment;

	private Double maxInterestRate;

	private String keywords;

	private String currItem;

	private Integer oldStatus;

	private String isHighlightsString;

	private Date updateDate;

	private Long customerId;

	private String maxInterestRateStr;

	/**
	 * @param entity
	 */
	public MathExpressionDto(MathExpression entity) {
		this.id = entity.getId();
		this.code = entity.getCode();
		this.expression = entity.getExpression();
		this.description = entity.getDescription();
		this.name = entity.getName();
		this.expressionType = entity.getExpressionType();
		// this.setLimitFrom(entity.getLimitFrom());
		// this.setLimitTo(entity.getLimitTo());
		this.setStrCustomerTypeId(entity.getCustomerTypeId());
		this.availableDateFrom = entity.getAvailableDateFrom();
		this.availableDateTo = entity.getAvailableDateTo();
		this.expressionType = entity.getExpressionType();
		this.processId = entity.getProcessId();
		this.processInstanceId = entity.getProcessIntanceId();
		this.status = entity.getStatus();

		this.createDate = entity.getCreateDate();
		this.createBy = entity.getCreateBy();
		this.approvedDate = entity.getApprovedDate();
		this.approvedBy = entity.getApprovedBy();
		this.publishedDate = entity.getPublishedDate();
		this.publishedBy = entity.getPublishedBy();

		this.comment = entity.getComment();
		this.maxLoanAmountStr = CmsUtils.convertBigDcimalToString(entity.getMaxLoanAmount(), Utils.PATTERN_MONEY);
		this.termType = entity.getTermType();
		this.termValue = entity.getTermValue();
		this.isHighlights = entity.getHighlights();

		this.linkAlias = entity.getLinkAlias();
		this.icon = entity.getIcon();
		this.physicalIcon = entity.getPhysicalIcon();
		this.keywords = entity.getKeywords();
		this.maxInterestRate = entity.getMaxInterestRate();
	}

	/**
	 * 
	 */
	public MathExpressionDto() {
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
	 * Set id
	 * 
	 * @param id type Integer
	 * @return
	 * @author thuydtn
	 */
	public void setId(Long id) {
		this.id = id;
		if (id != null) {
			this.referenceId = id.longValue();
		}
	}

	/**
	 * Get expression
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Set expression
	 * 
	 * @param expression type String
	 * @return
	 * @author thuydtn
	 */
	public void setExpression(String expression) {
		this.expression = expression;
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
	 * @param url type String
	 * @return
	 * @author thuydtn
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return
	 */
	public MathExpression createEntity() {
		MathExpression entity = new MathExpression();
		entity.setId(this.id);
		entity.setCode(this.code);
		entity.setName(this.name);
		entity.setExpression(this.expression);
		entity.setCustomerTypeId(this.strCustomerTypeId);
		entity.setDescription(this.description);
		// entity.setLimitFrom(this.limitFrom);
		// entity.setLimitTo(this.limitTo);
		// entity.setLimitFrom(this.limitFrom.doubleValue());
		// entity.setLimitTo(this.limitTo.doubleValue());
		entity.setAvailableDateFrom(this.availableDateFrom);
		entity.setAvailableDateTo(this.availableDateTo);
		entity.setExpressionType(this.expressionType);
//		if (this.processId != null) {
//			entity.setProcessId(this.processId);
//		}
//		if (this.processInstanceId != null) {
//			entity.setProcessIntanceId(this.processInstanceId);
//		}
		entity.setProcessId(this.processId);
		entity.setStatus(this.status);
		entity.setMaxLoanAmount(CmsUtils.convertStringToBigDcimal(this.maxLoanAmountStr, Utils.PATTERN_MONEY));
		entity.setTermType(this.termType);
		entity.setTermValue(this.termValue);
		entity.setHighlights(this.isHighlights);

		entity.setLinkAlias(this.linkAlias);
		entity.setIcon(this.icon);
		entity.setPhysicalIcon(this.physicalIcon);
		entity.setKeywords(this.keywords);
		if (null != this.getMaxInterestRateStr()) {
			entity.setMaxInterestRate(Double.parseDouble(this.getMaxInterestRateStr()));
		}
		return entity;
	}

	/**
	 * Get customerTypeName
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getCustomerTypeName() {
		return customerTypeName;
	}

	/**
	 * Set customerTypeName
	 * 
	 * @param customerTypeName type String
	 * @return
	 * @author thuydtn
	 */
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	/**
	 * Get lstCustomerTypeId
	 * 
	 * @return List<Long>
	 * @author thuydtn
	 */
	public List<Long> getLstCustomerTypeId() {
		return lstCustomerTypeId;
	}

	/**
	 * Set lstCustomerTypeId
	 * 
	 * @param lstCustomerTypeId type List<Long>
	 * @return
	 * @author thuydtn
	 */
	public void setLstCustomerTypeId(List<Long> lstCustomerTypeId) {
		this.lstCustomerTypeId = lstCustomerTypeId;
		this.strCustomerTypeId = this.lstCustomerTypeId.stream().map(i -> i.toString())
				.collect(Collectors.joining(","));

	}

	/**
	 * Get strCustomerTypeId
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getStrCustomerTypeId() {
		return strCustomerTypeId;
	}

	/**
	 * Set strCustomerTypeId
	 * 
	 * @param strCustomerTypeId type String
	 * @return
	 * @author thuydtn
	 */
	public void setStrCustomerTypeId(String strCustomerTypeId) {
		this.strCustomerTypeId = strCustomerTypeId;
		if (!StringUtils.isEmpty(strCustomerTypeId)) {
			this.lstCustomerTypeId = Stream.of(strCustomerTypeId.split(",")).map(Long::parseLong)
					.collect(Collectors.toList());
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = CmsUtils.toUppercase(code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getAvailableDateFrom() {
		return availableDateFrom;
	}

	public void setAvailableDateFrom(Date availableDateFrom) {
		this.availableDateFrom = availableDateFrom;
	}

	public Date getAvailableDateTo() {
		return availableDateTo;
	}

	public void setAvailableDateTo(Date availableDateTo) {
		this.availableDateTo = availableDateTo;
	}

	public String getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(String expressionType) {
		this.expressionType = expressionType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStrAvailableDateFrom() {
		return strAvailableDateFrom;
	}

	public void setStrAvailableDateFrom(String strAvailableDateFrom) {
		this.strAvailableDateFrom = strAvailableDateFrom;
	}

	public String getExpressionTypeNameMessageKey() {
		return expressionTypeNameMessageKey;
	}

	public void setExpressionTypeNameMessageKey(String expressionTypeName) {
		this.expressionTypeNameMessageKey = expressionTypeName;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getMaxLoanAmountStr() {
		return maxLoanAmountStr;
	}

	public void setMaxLoanAmountStr(String maxLoanAmountStr) {
		this.maxLoanAmountStr = maxLoanAmountStr;
	}

	public Integer getTermValue() {
		return termValue;
	}

	public void setTermValue(Integer termValue) {
		this.termValue = termValue;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Boolean getIsHighlights() {
		return isHighlights;
	}

	public void setIsHighlights(Boolean isHighlights) {
		this.isHighlights = isHighlights;
	}

	/**
	 * Get linkAlias
	 * 
	 * @return String
	 * @author taitm
	 */
	public String getLinkAlias() {
		return linkAlias;
	}

	/**
	 * Set linkAlias
	 * 
	 * @param linkAlias type String
	 * @return
	 * @author taitm
	 */
	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
	}

	/**
	 * Get icon
	 * 
	 * @return String
	 * @author taitm
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Set icon
	 * 
	 * @param icon type String
	 * @return
	 * @author taitm
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Get physicalIcon
	 * 
	 * @return String
	 * @author taitm
	 */
	public String getPhysicalIcon() {
		return physicalIcon;
	}

	/**
	 * Set physicalIcon
	 * 
	 * @param physicalIcon type String
	 * @return
	 * @author taitm
	 */
	public void setPhysicalIcon(String physicalIcon) {
		this.physicalIcon = physicalIcon;
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
	 * @return the statusText
	 */
	public String getStatusText() {
		return statusText;
	}

	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the stepBtnList
	 */
	public List<JProcessStepDto> getStepBtnList() {
		return stepBtnList;
	}

	/**
	 * @param stepBtnList the stepBtnList to set
	 */
	public void setStepBtnList(List<JProcessStepDto> stepBtnList) {
		this.stepBtnList = stepBtnList;
	}

//	/**
//	 * @return the buttonId
//	 */
//	public String getButtonId() {
//		return buttonId;
//	}
//
//	/**
//	 * @param buttonId the buttonId to set
//	 */
//	public void setButtonId(String buttonId) {
//		this.buttonId = buttonId;
//	}

	/**
	 * @return the customerAlias
	 */
	public String getCustomerAlias() {
		return customerAlias;
	}

	/**
	 * @param customerAlias the customerAlias to set
	 */
	public void setCustomerAlias(String customerAlias) {
		this.customerAlias = customerAlias;
	}

	/**
	 * @return the buttonAction
	 */
	public String getButtonAction() {
		return buttonAction;
	}

	/**
	 * @param buttonAction the buttonAction to set
	 */
	public void setButtonAction(String buttonAction) {
		this.buttonAction = buttonAction;
	}

	/**
	 * @return the stt
	 */
	public Integer getStt() {
		return stt;
	}

	/**
	 * @param stt the stt to set
	 */
	public void setStt(Integer stt) {
		this.stt = stt;
	}

	/**
	 * @return the maxLoanAmount
	 */
	public BigDecimal getMaxLoanAmount() {
		return maxLoanAmount;
	}

	/**
	 * @param maxLoanAmount the maxLoanAmount to set
	 */
	public void setMaxLoanAmount(BigDecimal maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}

	/**
	 * @return the mathExpresssComment
	 */
	public String getMathExpresssComment() {
		return mathExpresssComment;
	}

	/**
	 * @param mathExpresssComment the mathExpresssComment to set
	 */
	public void setMathExpresssComment(String mathExpresssComment) {
		this.mathExpresssComment = mathExpresssComment;
	}

	/**
	 * @return the maxInterestRate
	 */
	public Double getMaxInterestRate() {
		return maxInterestRate;
	}

	/**
	 * @param maxInterestRate the maxInterestRate to set
	 */
	public void setMaxInterestRate(Double maxInterestRate) {
		this.maxInterestRate = maxInterestRate;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public String getIsHighlightsString() {
		return isHighlightsString;
	}

	public void setIsHighlightsString(String isHighlightsString) {
		this.isHighlightsString = isHighlightsString;
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

	/**
	 * @return the customerId
	 * @author taitm
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 * @author taitm
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the maxInterestRateStr
	 */
	public String getMaxInterestRateStr() {
		return maxInterestRateStr;
	}

	/**
	 * @param maxInterestRateStr the maxInterestRateStr to set
	 */
	public void setMaxInterestRateStr(String maxInterestRateStr) {
		this.maxInterestRateStr = maxInterestRateStr;
	}

	/**
	 * @return the statusCode
	 * @author taitm
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 * @author taitm
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
