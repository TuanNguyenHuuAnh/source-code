/*******************************************************************************
 * Class        ：ProductConsultingInfor
 * Created date ：2017/09/01
 * Lasted date  ：2017/09/01
 * Author       ：hand
 * Change log   ：2017/09/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * ProductConsultingInfor
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_product_consulting_infor")
public class ProductConsultingInfor extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT_CONSULTING_INFOR")
	private Long id;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "gender")
	private String gender;

	@Column(name = "address")
	private String address;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "describe_request")
	private String describeRequest;

	@Column(name = "busines_name")
	private String businesName;

	@Column(name = "m_customer_id")
	private Long customerId;

	@Column(name = "province")
	private String province;

	@Column(name = "district")
	private String district;

	@Column(name = "note")
	private String note;

	@Column(name = "processing_status")
	private String processingStatus;

	@Column(name = "comment_name")
	private String comment;

	@Column(name = "comment_code")
	private String commentCode;

	@Column(name = "processed_user")
	private String processedUser;

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
	 * Get fullname
	 * 
	 * @return String
	 * @author hand
	 */
	public String getFullname() {
		return fullname;
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
	 * Get address
	 * 
	 * @return String
	 * @author hand
	 */
	public String getAddress() {
		return address;
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
	 * Get productId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getProductId() {
		return productId;
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
	 * Set productId
	 * 
	 * @param productId type Long
	 * @return
	 * @author hand
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
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
	 * @return the processingStatus
	 */
	public String getProcessingStatus() {
		return processingStatus;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
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
	 * @param processingStatus the processingStatus to set
	 */
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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

}
