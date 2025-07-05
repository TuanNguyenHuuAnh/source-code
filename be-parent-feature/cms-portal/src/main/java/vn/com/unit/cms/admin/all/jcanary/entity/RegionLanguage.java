/*******************************************************************************
 * Class        RegionLanguage
 * Created date 2017/03/13
 * Lasted date  2017/03/13
 * Author       TranLTH
 * Change log   2017/03/1301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
//import vn.com.unit.jcanary.constant.DatabaseConstant;

/**
 * RegionLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "JCA_M_REGION_LANGUAGE") // DatabaseConstant.TABLE_REGION_LANGUAGE
public class RegionLanguage extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_REGION_LANGUAGE")
	public Long id;

	/** Column: m_region_id type BIGINT(11) */
	@Column(name = "m_region_id")
	public Long mRegionId;

	/** Column: m_language_code type VARCHAR (30) */
	@Column(name = "m_language_code")
	public String mLanguageCode;

	/** Column: name type VARCHAR(255) */
	@Column(name = "name")
	public String name;

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
	 * Get mRegionId
	 * 
	 * @return Long
	 * @author TranLTH
	 */
	public Long getmRegionId() {
		return mRegionId;
	}

	/**
	 * Set mRegionId
	 * 
	 * @param mRegionId type Long
	 * @return
	 * @author TranLTH
	 */
	public void setmRegionId(Long mRegionId) {
		this.mRegionId = mRegionId;
	}

	/**
	 * Get mLanguageCode
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getmLanguageCode() {
		return mLanguageCode;
	}

	/**
	 * Set mLanguageCode
	 * 
	 * @param mLanguageCode type String
	 * @return
	 * @author TranLTH
	 */
	public void setmLanguageCode(String mLanguageCode) {
		this.mLanguageCode = mLanguageCode;
	}

	/**
	 * Get name
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * 
	 * @param name type String
	 * @return
	 * @author TranLTH
	 */
	public void setName(String name) {
		this.name = name;
	}

}
