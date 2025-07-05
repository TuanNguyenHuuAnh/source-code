/*******************************************************************************
 * Class        ：MathExpression
 * Created date ：2017/06/21
 * Lasted date  ：2017/06/21
 * Author       ：thuydtn
 * Change log   ：2017/06/21：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.dto.MathExpressionDto;
import vn.com.unit.cms.core.entity.AbstractTracking;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.constant.Utils;
//import vn.com.unit.jcanary.utils.Utils;
//import vn.com.unit.util.Util;

/**
 * MathExpression
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_math_expression")
public class MathExpression extends AbstractTracking {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_MATH_EXPRESSION")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "expression")
	private String expression;

	@Column(name = "description")
	private String description;

//    @Column(name = "limit_from")
//    private Double limitFrom;
//    @Column(name = "limit_to")
//    private Double limitTo;
	@Column(name = "m_customer_type_id")
	private String customerTypeId;

	@Column(name = "available_date_from")
	private Date availableDateFrom;

	@Column(name = "available_date_to")
	private Date availableDateTo;

	@Column(name = "expression_type")
	private String expressionType;

	@Column(name = "process_id")
	private Long processId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "process_intance_id")
	private Long processIntanceId;

	@Column(name = "owner_id")
	private Long ownerId;

	@Column(name = "owner_section_id")
	private Long ownerSectionId;

	@Column(name = "owner_branch_id")
	private Long ownerBranchId;

	@Column(name = "assigner_id")
	private Long assignerId;

	@Column(name = "assigner_section_id")
	private Long assignerSectionId;

	@Column(name = "assigner_branch_id")
	private Long assignerBranchId;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "approved_date")
	private Date approvedDate;

	@Column(name = "published_by")
	private String publishedBy;

	@Column(name = "published_date")
	private Date publishedDate;

	@Column(name = "math_expresss_comment")
	private String comment;

	@Column(name = "max_loan_amount")
	private BigDecimal maxLoanAmount;

	@Column(name = "term_value")
	private Integer termValue;

	@Column(name = "term_type")
	private String termType;

	@Column(name = "is_highlights")
	private Boolean highlights;

	@Column(name = "link_alias")
	private String linkAlias;

	@Column(name = "icon")
	private String icon;

	@Column(name = "physical_icon")
	private String physicalIcon;

	/**
	 * Add column keywords
	 * 
	 * @author ThuyNTB
	 */
	@Column(name = "keywords")
	private String keywords;

	/**
	 * Add column MAX_INTEREST_RATE
	 * 
	 * @author ThuyNTB
	 */
	@Column(name = "max_interest_rate")
	private Double maxInterestRate;

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

	/**
	 * Get id
	 * 
	 * @return Integer
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
	 * Get customerTypeId
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * Set customerTypeId
	 * 
	 * @param customerTypeId type Long
	 * @return
	 * @author thuydtn
	 */
	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * @param expressionModel
	 * @throws IOException
	 */
	public void copyDtoProperties(MathExpressionDto expressionModel) throws IOException {
		this.expression = expressionModel.getExpression();
		this.name = expressionModel.getName();
		this.description = expressionModel.getDescription();
		this.customerTypeId = expressionModel.getStrCustomerTypeId();
		this.availableDateFrom = expressionModel.getAvailableDateFrom();
		this.availableDateTo = expressionModel.getAvailableDateTo();
		this.expressionType = expressionModel.getExpressionType();
		this.comment = "";
//		if(expressionModel.getProcessId() != null){
//			this.processId = expressionModel.getProcessId();
//		}
//		if(expressionModel.getProcessInstanceId() != null){
//			this.processIntanceId = expressionModel.getProcessInstanceId();
//		}
		this.status = expressionModel.getStatus();
		this.maxLoanAmount = CmsUtils.convertStringToBigDcimal(expressionModel.getMaxLoanAmountStr(), Utils.PATTERN_MONEY);
		this.termType = expressionModel.getTermType();
		this.termValue = expressionModel.getTermValue();
		this.highlights = expressionModel.getIsHighlights();
		this.linkAlias = expressionModel.getLinkAlias();
		this.icon = expressionModel.getIcon();

		// physical file template
		String physicalIcon = expressionModel.getPhysicalIcon();
		// upload images
		if (StringUtils.isNotEmpty(physicalIcon)) {
			String newPhiscalNameMobile = CmsUtils.moveTempToUploadFolder(physicalIcon, AdminConstant.NEWS_FOLDER);
			this.physicalIcon = newPhiscalNameMobile;
		} else {
			this.physicalIcon = null;
		}
		this.keywords = expressionModel.getKeywords();
		if(null != expressionModel.getMaxInterestRateStr()) {
			this.maxInterestRate = Double.parseDouble(expressionModel.getMaxInterestRateStr());
		}
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param subType
	 */
	public void setName(String subType) {
		this.name = subType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Double getLimitFrom() {
//		return limitFrom;
//	}
//
//	public void setLimitFrom(Double limitFrom) {
//		this.limitFrom = limitFrom;
//	}
//
//	public Double getLimitTo() {
//		return limitTo;
//	}
//
//	public void setLimitTo(Double limitTo) {
//		this.limitTo = limitTo;
//	}

	public String getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(String expressionType) {
		this.expressionType = expressionType;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getProcessIntanceId() {
		return processIntanceId;
	}

	public void setProcessIntanceId(Long processIntanceId) {
		this.processIntanceId = processIntanceId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerSectionId() {
		return ownerSectionId;
	}

	public void setOwnerSectionId(Long ownerSectionId) {
		this.ownerSectionId = ownerSectionId;
	}

	public Long getOwnerBranchId() {
		return ownerBranchId;
	}

	public void setOwnerBranchId(Long ownerBranchId) {
		this.ownerBranchId = ownerBranchId;
	}

	public Long getAssignerId() {
		return assignerId;
	}

	public void setAssignerId(Long assignerId) {
		this.assignerId = assignerId;
	}

	public Long getAssignerSectionId() {
		return assignerSectionId;
	}

	public void setAssignerSectionId(Long assignerSectionId) {
		this.assignerSectionId = assignerSectionId;
	}

	public Long getAssignerBranchId() {
		return assignerBranchId;
	}

	public void setAssignerBranchId(Long assignerBranchId) {
		this.assignerBranchId = assignerBranchId;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BigDecimal getMaxLoanAmount() {
		return maxLoanAmount;
	}

	public void setMaxLoanAmount(BigDecimal maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
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

	public Boolean getHighlights() {
		return highlights;
	}

	public void setHighlights(Boolean highlights) {
		this.highlights = highlights;
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

}
