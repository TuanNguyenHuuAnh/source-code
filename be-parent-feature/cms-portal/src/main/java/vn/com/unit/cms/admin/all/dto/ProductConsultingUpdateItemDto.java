package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import vn.com.unit.cms.admin.all.entity.ProductConsultingUpdateItem;
import vn.com.unit.cms.admin.all.enumdef.ProductConsultingStatusEnum;
import vn.com.unit.core.security.UserProfileUtils;

public class ProductConsultingUpdateItemDto {

	private Long id;
	
	private Long productConsultingInforId;
	
	private String commentName;
	
	private String commentCode;
	
	private String status;
	
	private String statusTitle;
	
	private String processedUser;
	
	private String fullNameProcessedUser;
	
	private Date createDate;
	
	private String createBy;
	
	public ProductConsultingUpdateItemDto createEntity() {
		ProductConsultingUpdateItemDto entity = new ProductConsultingUpdateItemDto();
		entity.setId(this.id);
		entity.setProductConsultingInforId(this.productConsultingInforId);
		entity.setCommentName(this.commentName);
		entity.setCommentCode(this.commentCode);
		entity.setStatus(this.status);
		entity.setProcessedUser(this.processedUser);
		entity.setCreateDate(new Date());
		entity.setCreateBy(UserProfileUtils.getUserNameLogin());
		return entity;
	}

	public ProductConsultingUpdateItemDto() {

	}

	public ProductConsultingUpdateItemDto(ProductConsultingUpdateItem entity) {
		this.id = entity.getId();
		this.productConsultingInforId = entity.getProductConsultingInforId();
		this.commentName = entity.getCommentName();
		this.commentCode = entity.getCommentCode();
		this.status = entity.getStatus();
	}

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the productConsultingInforId
	 */
	public Long getProductConsultingInforId() {
		return productConsultingInforId;
	}
	/**
	 * @return the commentName
	 */
	public String getCommentName() {
		return commentName;
	}
	/**
	 * @return the commentCode
	 */
	public String getCommentCode() {
		return commentCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the statusTitle
	 */
	public String getStatusTitle() {
		return statusTitle;
	}
	/**
	 * @return the processedUser
	 */
	public String getProcessedUser() {
		return processedUser;
	}
	/**
	 * @return the fullNameProcessedUser
	 */
	public String getFullNameProcessedUser() {
		return fullNameProcessedUser;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param productConsultingInforId the productConsultingInforId to set
	 */
	public void setProductConsultingInforId(Long productConsultingInforId) {
		this.productConsultingInforId = productConsultingInforId;
	}
	/**
	 * @param commentName the commentName to set
	 */
	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}
	/**
	 * @param commentCode the commentCode to set
	 */
	public void setCommentCode(String commentCode) {
		this.commentCode = commentCode;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
		for(ProductConsultingStatusEnum en : ProductConsultingStatusEnum.class.getEnumConstants()){
			if(en.getStatusName().equals(this.status)){
				this.statusTitle = en.getStatusAlias();
				break;
			}
		}
	}
	/**
	 * @param statusTitle the statusTitle to set
	 */
	public void setStatusTitle(String statusTitle) {
		this.statusTitle = statusTitle;
	}
	/**
	 * @param processedUser the processedUser to set
	 */
	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
	}
	/**
	 * @param fullNameProcessedUser the fullNameProcessedUser to set
	 */
	public void setFullNameProcessedUser(String fullNameProcessedUser) {
		this.fullNameProcessedUser = fullNameProcessedUser;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
}
