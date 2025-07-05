/*******************************************************************************
 * Class        JcaMRegion
 * Created date 2017/02/14
 * Lasted date  2017/02/17
 * Author       tranlth
 * Change log   2017/02/1401-00 tranlth create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
//import vn.com.unit.jcanary.constant.DatabaseConstant;
//import vn.com.unit.util.Util;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import vn.com.unit.cms.core.utils.CmsUtils;

/**
 * Jca_M_Region
 * 
 * @version 01-00
 * @since 01-00
 * @author tranlth
 */

@Table(name = "JCA_M_REGION") // DatabaseConstant.TABLE_REGION
public class Region extends AbstractTracking {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_REGION")
	public Long id;

	/** Column: code type VARCHAR(50) */
	@Column(name = "code")
	public String code;

	/** Column: m_country_id type BIGINT(11) */
	@Column(name = "m_country_id")
	public Long mCountryId;

	/** Column: note type TEXT(0) */
	@Column(name = "note")
	private String note;

	/** Column: description type TEXT(0) */
	@Column(name = "description")
	private String description;

	@Column(name = "active_flag")
	private String activeFlag;

	public Region() {
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
	 * Get mCountryId
	 * 
	 * @return Long
	 * @author TranLTH
	 */
	public Long getmCountryId() {
		return mCountryId;
	}

	/**
	 * Set mCountryId
	 * 
	 * @param mCountryId type Long
	 * @return
	 * @author TranLTH
	 */
	public void setmCountryId(Long mCountryId) {
		this.mCountryId = mCountryId;
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

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}