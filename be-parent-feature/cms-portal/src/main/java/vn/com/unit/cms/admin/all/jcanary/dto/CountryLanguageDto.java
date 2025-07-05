/*******************************************************************************
 * Class        CountryLanguageDto
 * Created date 2017/03/13
 * Lasted date  2017/03/13
 * Author       TranLTH
 * Change log   2017/03/1301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

/**
 * CountryLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class CountryLanguageDto {

    private Long countryLanguageId;
            
    private Long mCountryId;
            
    private String mLanguageCode;
            
    private String countryName;
    
    private String localName;

    /**
     * Get countryLanguageId
     * @return Long
     * @author TranLTH
     */
    public Long getCountryLanguageId() {
        return countryLanguageId;
    }

    /**
     * Set countryLanguageId
     * @param   countryLanguageId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setCountryLanguageId(Long countryLanguageId) {
        this.countryLanguageId = countryLanguageId;
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
