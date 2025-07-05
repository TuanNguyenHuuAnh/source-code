/*******************************************************************************
 * Class        Country
 * Created date 2017/02/23
 * Lasted date  2017/02/23
 * Author       TranLTH
 * Change log   2017/02/2301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import vn.com.unit.cms.core.utils.CmsUtils;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
//import vn.com.unit.jcanary.constant.DatabaseConstant;
//import vn.com.unit.util.Util;

/**
 * Country
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "JCA_M_COUNTRY") // DatabaseConstant.TABLE_COUNTRY
public class Country extends AbstractTracking {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_COUNTRY")
	public Long id;

	/** Column: code type VARCHAR(3) */
	@Column(name = "code")
	public String code;

	/** Column: latitude type DOUBLE */
	@Column(name = "latitude")
	public Double latitude;

	/** Column: longtitude type DOUBLE */
	@Column(name = "longtitude")
	public Double longtitude;

	/** Column: web_code type VARCHAR(2) */
	@Column(name = "web_code")
	public String webCode;

	/** Column: phone_code type VARCHAR(255) */
	@Column(name = "phone_code")
	public String phoneCode;

	/** Column: note type TEXT(0) */
	@Column(name = "note")
	private String note;

	/** Column: description type TEXT(0) */
	@Column(name = "description")
	private String description;

	@Column(name = "active_flag")
	private String activeFlag;

	public Country() {
	}

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author TranLTH
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id type Long
	 * @return
	 * @author TranLTH
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get code
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * 
	 * @param code type String
	 * @return
	 * @author TranLTH
	 */
	public void setCode(String code) {
		this.code = CmsUtils.toUppercase(code);
	}

	/**
	 * Get latitude
	 * 
	 * @return Double
	 * @author TranLTH
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Set latitude
	 * 
	 * @param latitude type Double
	 * @return
	 * @author TranLTH
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Get longtitude
	 * 
	 * @return Double
	 * @author TranLTH
	 */
	public Double getLongtitude() {
		return longtitude;
	}

	/**
	 * Set longtitude
	 * 
	 * @param longtitude type Double
	 * @return
	 * @author TranLTH
	 */
	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

	/**
	 * Get webCode
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getWebCode() {
		return webCode;
	}

	/**
	 * Set webCode
	 * 
	 * @param webCode type String
	 * @return
	 * @author TranLTH
	 */
	public void setWebCode(String webCode) {
		this.webCode = CmsUtils.toUppercase(webCode);
	}

	/**
	 * Get phoneCode
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getPhoneCode() {
		return phoneCode;
	}

	/**
	 * Set phoneCode
	 * 
	 * @param phoneCode type String
	 * @return
	 * @author TranLTH
	 */
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = CmsUtils.toUppercase(phoneCode);
	}

	/**
	 * Get note
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Set note
	 * 
	 * @param note type String
	 * @return
	 * @author TranLTH
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Get description
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description
	 * 
	 * @param description type String
	 * @return
	 * @author TranLTH
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}
