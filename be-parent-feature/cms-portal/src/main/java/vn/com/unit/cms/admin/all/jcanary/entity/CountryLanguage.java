/*******************************************************************************
 * Class        CountryLanguage
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
 * CountryLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "JCA_M_COUNTRY_LANGUAGE") // DatabaseConstant.TABLE_COUNTRY_LANGUAGE
public class CountryLanguage extends AbstractTracking {
    
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_COUNTRY_LANGUAGE")
    public Long id;
    
    /** Column: m_country_id type BIGINT(11) */
    @Column(name = "m_country_id")
    public Long mCountryId;
    
    /** Column: m_language_code type VARCHAR (30) */
    @Column(name = "m_language_code")
    public String mLanguageCode; 
    
    /** Column: country_name type VARCHAR(255) */
    @Column(name = "country_name")   
    public String countryName;
    
    /** Column: local_name type VARCHAR(255) */
    @Column(name = "local_name")   
    public String localName;

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
     * Get mCountryId
     * @return Long
     * @author TranLTH
     */
    public Long getmCountryId() {
        return mCountryId;
    }

    /**
     * Set mCountryId
     * @param   mCountryId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmCountryId(Long mCountryId) {
        this.mCountryId = mCountryId;
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
     * Get countryName
     * @return String
     * @author TranLTH
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set countryName
     * @param   countryName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Get localName
     * @return String
     * @author TranLTH
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Set localName
     * @param   localName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }       
}
