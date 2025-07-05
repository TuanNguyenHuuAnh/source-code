/*******************************************************************************
 * Class        CityLanguage
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
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;
//import vn.com.unit.jcanary.constant.DatabaseConstant;

/**
 * CityLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "JCA_M_CITY_LANGUAGE") // DatabaseConstant.TABLE_CITY_LANGUAGE
public class CityLanguage extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_CITY_LANGUAGE")
    public Long id;
    
    /** Column: m_city_id type BIGINT(11) */
    @Column(name = "m_city_id")
    public Long mCityId;
    
    /** Column: m_language_code type VARCHAR (30) */
    @Column(name = "m_language_code")
    public String mLanguageCode; 
    
    /** Column: name type VARCHAR(255) */
    @Column(name = "name")   
    public String name;

    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get mCityId
     * @return Long
     * @author TranLTH
     */
    public Long getmCityId() {
        return mCityId;
    }

    /**
     * Set mCityId
     * @param   mCityId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmCityId(Long mCityId) {
        this.mCityId = mCityId;
    }
    /**
     * Get mLanguageCode
     * @return String
     * @author TranLTH
     */
    public String getmLanguageCode() {
        return mLanguageCode;
    }

    /**
     * Set mLanguageCode
     * @param   mLanguageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setmLanguageCode(String mLanguageCode) {
        this.mLanguageCode = mLanguageCode;
    }

    /**
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }
        
}
