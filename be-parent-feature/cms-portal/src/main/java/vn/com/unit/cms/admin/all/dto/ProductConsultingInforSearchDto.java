/*******************************************************************************
 * Class        ：FaqsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：hand
 * Change log   ：2017/02/23：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.entity.ProductConsultingInfor;
import vn.com.unit.cms.admin.all.enumdef.ProductConsultingStatusEnum;

/**
 * FaqsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductConsultingInforSearchDto {

	/** id */
	private Long id;

	/** fullname */
	private String fullname;

	/** address */
	private String address;

	/** phoneNumber */
	private String phoneNumber;

	/** productName */
	private String productName;

	/** createDate */
	private Date createDate;

	/** url */
	private String pageUrl;

	/** languageCode */
	private String languageCode;

	/** gender */
	private String gender;

	/** describeRequest */
	private String describeRequest;

	/** businesName */
	private String businesName;

	/** customerId */
	private Long customerId;

	/** customerName */
	private String customerName;

	/** productId */
	private Long productId;

	private Integer pageSize;

	private String province;

	private String district;

	private String note;

	private String commentName;

	private String commentCode;

	private String processingStatus;

	private String processedUser;

	private String statusName;

	private List<ProductConsultingUpdateItemDto> updateHistory;

	private String comment;
	 
	private Date updateDate;
	
	private String provinceId;

	private String districtId;
	
	private Date fromDate;
	
	private Date toDate;
	
	public ProductConsultingInforSearchDto() {

	}
	
	public ProductConsultingInforSearchDto(ProductConsultingInfor entity) {
		this.id = entity.getId();
		this.productId = entity.getProductId();
		this.districtId = entity.getDistrict();
		this.fullname = entity.getFullname();
		this.gender =  entity.getGender();
		this.phoneNumber = entity.getPhoneNumber();
		this.businesName = entity.getBusinesName();
		this.describeRequest = entity.getDescribeRequest();
		this.setProcessedUser(entity.getProcessedUser());
		this.comment = entity.getComment();
		this.commentCode = entity.getCommentCode();
		this.createDate = entity.getCreateDate();
		this.updateDate = entity.getUpdateDate();
		this.customerId = entity.getCustomerId();
		
	
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

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
	 * @param id type Long
	 * @return
	 * @author hand
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get fullname
	 * 
	 * @return String
	 * @author hand
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Set fullname
	 * 
	 * @param fullname type String
	 * @return
	 * @author hand
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Get address
	 * 
	 * @return String
	 * @author hand
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set address
	 * 
	 * @param address type String
	 * @return
	 * @author hand
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get phoneNumber
	 * 
	 * @return String
	 * @author hand
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Set phoneNumber
	 * 
	 * @param phoneNumber type String
	 * @return
	 * @author hand
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Get productName
	 * 
	 * @return String
	 * @author hand
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Set productName
	 * 
	 * @param productName type String
	 * @return
	 * @author hand
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
	 * @param createDate type Date
	 * @return
	 * @author hand
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Get pageUrl
	 * 
	 * @return String
	 * @author hand
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * Set pageUrl
	 * 
	 * @param pageUrl type String
	 * @return
	 * @author hand
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * Get languageCode
	 * 
	 * @return String
	 * @author hand
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Set languageCode
	 * 
	 * @param languageCode type String
	 * @return
	 * @author hand
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Get gender
	 * 
	 * @return String
	 * @author hand
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Set gender
	 * 
	 * @param gender type String
	 * @return
	 * @author hand
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Get describeRequest
	 * 
	 * @return String
	 * @author hand
	 */
	public String getDescribeRequest() {
		return describeRequest;
	}

	/**
	 * Get businesName
	 * 
	 * @return String
	 * @author hand
	 */
	public String getBusinesName() {
		return businesName;
	}

	/**
	 * Get customerId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * Set describeRequest
	 * 
	 * @param describeRequest type String
	 * @return
	 * @author hand
	 */
	public void setDescribeRequest(String describeRequest) {
		this.describeRequest = describeRequest;
	}

	/**
	 * Set businesName
	 * 
	 * @param businesName type String
	 * @return
	 * @author hand
	 */
	public void setBusinesName(String businesName) {
		this.businesName = businesName;
	}

	/**
	 * Set customerId
	 * 
	 * @param customerId type Long
	 * @return
	 * @author hand
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * Get customerName
	 * 
	 * @return String
	 * @author hand
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Set customerName
	 * 
	 * @param customerName type String
	 * @return
	 * @author hand
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Get productId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * Set productId
	 * 
	 * @param productId type Long
	 * @return
	 * @author hand
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the processingStatus
	 */
	public String getProcessingStatus() {
		return processingStatus;
	}

	/**
	 * @param processingStatus the processingStatus to set
	 */
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
		for (ProductConsultingStatusEnum en : ProductConsultingStatusEnum.class.getEnumConstants()) {
			if (en.getStatusName().equals(this.processingStatus)) {
				this.statusName = en.getStatusAlias();
				break;
			}
		}

	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * @return the processedUser
	 */
	public String getProcessedUser() {
		return processedUser;
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
	 * @param processedUser the processedUser to set
	 */
	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
	}

	/**
	 * @return the updateHistory
	 */
	public List<ProductConsultingUpdateItemDto> getUpdateHistory() {
		return updateHistory;
	}

	/**
	 * @param updateHistory the updateHistory to set
	 */
	public void setUpdateHistory(List<ProductConsultingUpdateItemDto> updateHistory) {
		this.updateHistory = updateHistory;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @return the districtId
	 */
	public String getDistrictId() {
		return districtId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	/**
	 * @return the fromDate
	 * @author taitm
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 * @author taitm
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 * @author taitm
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 * @author taitm
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}
