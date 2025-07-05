/*******************************************************************************
 * Class        RegionLanguageDto
 * Created date 2017/03/13
 * Lasted date  2017/03/13
 * Author       TranLTH
 * Change log   2017/03/1301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

/**
 * RegionLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class RegionLanguageDto {
    
    private Long regionLanguageId;
       
    private Long mRegionId;
        
    private String mLanguageCode;
        
    private String regionName;

    /**
     * Get regionLanguageId
     * @return Long
     * @author TranLTH
     */
    public Long getRegionLanguageId() {
        return regionLanguageId;
    }

    /**
     * Set regionLanguageId
     * @param   regionLanguageId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setRegionLanguageId(Long regionLanguageId) {
        this.regionLanguageId = regionLanguageId;
    }
    
    /**
     * Get mRegionId
     * @return Long
     * @author TranLTH
     */
    public Long getmRegionId() {
        return mRegionId;
    }

    /**
     * Set mRegionId
     * @param   mRegionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmRegionId(Long mRegionId) {
        this.mRegionId = mRegionId;
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
     * Get regionName
     * @return String
     * @author TranLTH
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Set regionName
     * @param   regionName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
        
}
