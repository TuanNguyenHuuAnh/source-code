/*******************************************************************************
 * Class        ：AbtractImportExcelEntity
 * Created date ：2019/02/15
 * Lasted date  ：2019/02/15
 * Author       ：VinhLT
 * Change log   ：2019/02/15：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.Transient;

/**
 * AbtractImportExcelEntity
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public abstract class AbtractImportExcelEntity {

	@Column(name = "SESSION_KEY")
	private String sessionKey;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "MESSAGE_TYPE")
	private Integer messageType;
	
	@Column(name = "MESSAGE_ERROR_EN")
	private String messageErrorEn;
	
	@Column(name = "MESSAGE_ERROR_VI")
	private String messageErrorVi;

	@Column(name = "MESSAGE_ERROR_CODE")
	private String messageErrorCode;
	
	@Column(name = "DELETE_FLAG")
    private int deleteFlag = 0;
	
	@Transient
	private String unknowParam;
	
	@Transient
	private String unknowParam2;
	
	@Transient
	private String unknowParam3;
	
	public String getMessageErrorCode() {
		return messageErrorCode;
	}

	public void setMessageErrorCode(String messageErrorCode) {
		this.messageErrorCode = messageErrorCode;
	}

	/**
	 * Get sessionKey
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getSessionKey() {
		return sessionKey;
	}

	/**
	 * Set sessionKey
	 * 
	 * @param sessionKey type String
	 * @return
	 * @author VinhLT
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * getCreatedBy
	 *
	 * @return
	 * @author VinhLT
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * setCreatedBy
	 *
	 * @param createdBy
	 * @author VinhLT
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * getCreatedDate
	 *
	 * @return
	 * @author VinhLT
	 */
	public Date getCreatedDate() {
		return createdDate != null ? (Date) createdDate.clone() : null;
	}

	/**
	 * setCreatedDate
	 *
	 * @param createdDate
	 * @author VinhLT
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate != null ? (Date) createdDate.clone() : null;
	}

	public String getMessageErrorEn() {
		return messageErrorEn;
	}

	public void setMessageErrorEn(String messageErrorEn) {
		this.messageErrorEn = messageErrorEn;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		if(this.messageType == null || this.messageType != 2) {
			this.messageType = messageType;
		}
	}

	public String getMessageErrorVi() {
		return messageErrorVi;
	}

	public void setMessageErrorVi(String messageErrorVi) {
		this.messageErrorVi = messageErrorVi;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Transient
	public String getUnknowParam() {
		return unknowParam;
	}
	@Transient
	public void setUnknowParam(String unknowParam) {
		this.unknowParam = unknowParam;
	}
	@Transient
	public String getUnknowParam2() {
		return unknowParam2;
	}
	@Transient
	public void setUnknowParam2(String unknowParam2) {
		this.unknowParam2 = unknowParam2;
	}
	@Transient
	public String getUnknowParam3() {
		return unknowParam3;
	}

	public void setUnknowParam3(String unknowParam3) {
		this.unknowParam3 = unknowParam3;
	}
	
}
