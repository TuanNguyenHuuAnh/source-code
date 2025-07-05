/*******************************************************************************
 * Class        DistrictLanguageDto
 * Created date 2017/03/13
 * Lasted date  2017/03/13
 * Author       TranLTH
 * Change log   2017/03/1301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

/**
 * DistrictLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class DistrictLanguageDto {

    private Long districtLanguageId;
    
    private Long mDistrictId;
    
    private String mLanguageCode;
    
    private String districtName;

    /**
     * Get districtLanguageId
     * @return Long
     * @author TranLTH
     */
    public Long getDistrictLanguageId() {
        return districtLanguageId;
    }

    /**
     * Set districtLanguageId
     * @param   districtLanguageId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setDistrictLanguageId(Long districtLanguageId) {
        this.districtLanguageId = districtLanguageId;
    }
    
    /**
     * Get mDistrictId
     * @return Long
     * @author TranLTH
     */
    public Long getmDistrictId() {
        return mDistrictId;
    }

    /**
     * Set mDistrictId
     * @param   mDistrictId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmDistrictId(Long mDistrictId) {
        this.mDistrictId = mDistrictId;
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
     * Get districtName
     * @return String
     * @author TranLTH
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * Set districtName
     * @param   districtName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
        
}
