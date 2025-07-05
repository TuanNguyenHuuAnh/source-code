/*******************************************************************************
 * Class        ：ShareHolder
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.admin.all.dto.IntroductionDto;
import vn.com.unit.cms.core.entity.AbstractTracking;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

@Table(name = "m_introduction")
public class Introduction extends AbstractTracking {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTRODUCTION")
	private Long id;
	@Column(name = "m_introduction_category_id")
	private Long categoryId;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
	private String name;
	@Column(name = "link_alias")
	private String linkAlias;
	@Column(name = "note")
	private String note;
	@Column(name = "description")
	private String description;
	@Column(name = "sub_title")
	private String subTitle;
	@Column(name = "image_url")
	private String imageUrl;
	@Column(name = "key_word")
	private String keyWord;
	@Column(name = "sort")
	private Long sort;
	@Column(name = "gender")
	private Integer gender;
	@Column(name = "views")
	private Long views;
	@Column(name = "enabled")
	private boolean enabled;
	@Column(name = "image_name")
	private String imageName;

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

	@Column(name = "introduction_physical_video")
	private String introductionPhysicalVideo;

	@Column(name = "introduction_title_video")
	private String introductionTitleVideo;

	@Column(name = "introduction_video")
	private String introductionVideo;
	
	@Column(name = "approved_by")
	private String approvedBy;
	
	@Column(name = "published_by")
	private String publishedBy;
	
	@Column(name = "introduction_comment")
	private String introductionComment;
	
	@Column(name = "customer_type_id")
	private Long customerTypeId;
	
	@Column(name = "before_id")
	private Long beforeId;

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
	 * @param id
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get categoryId
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Set categoryId
	 * 
	 * @param categoryId
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	 * Set code
	 * 
	 * @param code
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * Get note
	 * 
	 * @return String
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * Get subTitle
	 * 
	 * @return String
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	/**
	 * Get imageUrl
	 * 
	 * @return String
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Get keyWord
	 * 
	 * @return String
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * Get views
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setViews(Long views) {
		this.views = views;
	}

	/**
	 * Get enabled
	 * 
	 * @return boolean
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * copy necessary property from dto to entity object => save update data
	 * 
	 * @param updateDto
	 *            type IntroductionDto
	 * @return
	 * @author thuydtn
	 */
	public void copyDtoProperties(IntroductionDto updateDto) {
		this.code = updateDto.getCode();
		this.categoryId = updateDto.getCategoryId();
		this.name = updateDto.getName();
		this.linkAlias = updateDto.getLinkAlias();
		this.note = updateDto.getNote();
		this.description = updateDto.getDescription();
		this.subTitle = updateDto.getSubTitle();
		this.imageUrl = updateDto.getImageUrl();
		this.imageName = updateDto.getImageName();
		this.keyWord = updateDto.getKeyWord();
		this.views = updateDto.getViews();
		this.enabled = updateDto.isEnabled();
		this.sort = updateDto.getSort();
		this.gender = updateDto.getGender();
	}

	/**
	 * Get imageName
	 * 
	 * @return String
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * Get processId
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * Get status
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Set status
	 * 
	 * @param status
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Get processIntanceId
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getProcessIntanceId() {
		return processIntanceId;
	}

	/**
	 * Set processIntanceId
	 * 
	 * @param processIntanceId
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setProcessIntanceId(Long processIntanceId) {
		this.processIntanceId = processIntanceId;
	}

	/**
	 * Get ownerId
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Get ownerSectionId
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setOwnerSectionId(Long ownerSectionId) {
		this.ownerSectionId = ownerSectionId;
	}

	/**
	 * Get ownerBranchId
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setOwnerBranchId(Long ownerBranchId) {
		this.ownerBranchId = ownerBranchId;
	}

	/**
	 * Get assignerId
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setAssignerId(Long assignerId) {
		this.assignerId = assignerId;
	}

	/**
	 * Get assignerSectionId
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setAssignerSectionId(Long assignerSectionId) {
		this.assignerSectionId = assignerSectionId;
	}

	/**
	 * Get assignerBranchId
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setAssignerBranchId(Long assignerBranchId) {
		this.assignerBranchId = assignerBranchId;
	}

	/**
	 * Get sort
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * Set sort
	 * 
	 * @param sort
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getLinkAlias() {
		return linkAlias;
	}

	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
	}

	public String getIntroductionPhysicalVideo() {
		return introductionPhysicalVideo;
	}

	public void setIntroductionPhysicalVideo(String introductionPhysicalVideo) {
		this.introductionPhysicalVideo = introductionPhysicalVideo;
	}

	public String getIntroductionTitleVideo() {
		return introductionTitleVideo;
	}

	public void setIntroductionTitleVideo(String introductionTitleVideo) {
		this.introductionTitleVideo = introductionTitleVideo;
	}

	public String getIntroductionVideo() {
		return introductionVideo;
	}

	public void setIntroductionVideo(String introductionVideo) {
		this.introductionVideo = introductionVideo;
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

	public String getIntroductionComment() {
		return introductionComment;
	}

	public void setIntroductionComment(String introductionComment) {
		this.introductionComment = introductionComment;
	}

	/**
	 * @return the customerTypeId
	 * @author taitm
	 */
	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * @param customerTypeId
	 *            the customerTypeId to set
	 * @author taitm
	 */
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * @return the beforeId
	 * @author taitm
	 */
	public Long getBeforeId() {
		return beforeId;
	}

	/**
	 * @param beforeId
	 *            the beforeId to set
	 * @author taitm
	 */
	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

}
