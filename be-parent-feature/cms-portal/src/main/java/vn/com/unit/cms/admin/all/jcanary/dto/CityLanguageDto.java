/*******************************************************************************
 * Class        CityLanguageDto
 * Created date 2017/03/13
 * Lasted date  2017/03/13
 * Author       TranLTH
 * Change log   2017/03/1301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

/**
 * CityLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class CityLanguageDto {
    
    private Long cityLanguageId;
    
    private Long mCityId;
        
    private String mLanguageCode;
        
    private String cityName;

    /**
     * Get cityLanguageId
     * @return Long
     * @author TranLTH
     */
    public Long getCityLanguageId() {
        return cityLanguageId;
    }

    /**
     * Set cityLanguageId
     * @param   cityLanguageId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setCityLanguageId(Long cityLanguageId) {
        this.cityLanguageId = cityLanguageId;
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
     * Get cityName
     * @return String
     * @author TranLTH
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Set cityName
     * @param   cityName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }    
}
