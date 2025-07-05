package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_product_consulting_infor_update_item")
public class ProductConsultingUpdateItem extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT_CONSULTING_INFOR_UPDATE_ITEM")
	private Long id;

	@Column(name = "m_product_consulting_infor_id")
	private Long productConsultingInforId;

	@Column(name = "comment_name")
	private String commentName;

	@Column(name = "comment_code")
	private String commentCode;

	@Column(name = "processing_status")
	private String status;

	@Column(name = "processed_user")
	private String processedUser;

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
	 * @return the processedUser
	 */
	public String getProcessedUser() {
		return processedUser;
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
	}

	/**
	 * @param processedUser the processedUser to set
	 */
	public void setProcessedUser(String processedUser) {
		this.processedUser = processedUser;
	}

}
