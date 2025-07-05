/*******************************************************************************
 * Class        :DeviceToken
 * Created date :2019/07/08
 * Lasted date  :2019/07/08
 * Author       :HungHT
 * Change log   :2019/07/08:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

/**
 * DeviceToken
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Table(name = "JCA_API_DEVICE_TOKEN")
public class DeviceToken {

	/** Column: ID type NUMBER(22,20) NOT NULL */
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_API_DEVICE_TOKEN")
	private Long id;

	/** Column: ACCOUNT_ID type NUMBER(22,20) NULL */
	@Column(name = "ACCOUNT_ID")
	private Long accountId;

	/** Column: DEVICE_TOKEN type VARCHAR2(4000) NOT NULL */
	@Column(name = "DEVICE_TOKEN")
	private String deviceToken;
	
	/** Column: LANG_CODE type VARCHAR2(255) NULL */
	@Column(name = "LANG_CODE")
	private String langCode;

	/** Column: CREATED_BY type VARCHAR2(1020) NULL */
	@Column(name = "CREATED_BY")
	private String createdBy;

	/** Column: CREATED_DATE type DATE(7) NULL */
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	/** Column: UPDATED_BY type VARCHAR2(1020) NULL */
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	/** Column: UPDATED_DATE type DATE(7) NULL */
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	/**
	* Set id
	* @param id
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setId(Long id) {
		this.id = id;
	}

	/**
	* Get id
	* @return Long
	* @author HungHT
	*/
	public Long getId() {
		return id;
	}

	/**
	* Set accountId
	* @param accountId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	* Get accountId
	* @return Long
	* @author HungHT
	*/
	public Long getAccountId() {
		return accountId;
	}

	/**
	* Set deviceToken
	* @param deviceToken
	*        type String
	* @return
	* @author HungHT
	*/
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	/**
	* Get deviceToken
	* @return String
	* @author HungHT
	*/
	public String getDeviceToken() {
		if( !StringUtils.isEmpty(deviceToken) ) {
			deviceToken = deviceToken.trim();
		}
		
		return deviceToken;
	}

	/**
	* Set createdBy
	* @param createdBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	* Get createdBy
	* @return String
	* @author HungHT
	*/
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	* Set createdDate
	* @param createdDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	* Get createdDate
	* @return Date
	* @author HungHT
	*/
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	* Set updatedBy
	* @param updatedBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	* Get updatedBy
	* @return String
	* @author HungHT
	*/
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	* Set updatedDate
	* @param updatedDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	* Get updatedDate
	* @return Date
	* @author HungHT
	*/
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}