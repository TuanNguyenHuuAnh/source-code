/*******************************************************************************
 * Class        ：ServiceDetailLanguageDto
 * Created date ：2017/05/29
 * Lasted date  ：2017/05/29
 * Author       ：tungns <tungns@unit.com.vn>
 * Change log   ：2017/05/29：01-00 tungns <tungns@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * ServiceDetailLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns <tungns@unit.com.vn>
 */
public class ServiceDetailLanguageDto {
     
    private String mLanguageCode;
    private List<ServiceDetailDto> serviceDetailDtoList;
    /**
     * Get mLanguageCode
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getmLanguageCode() {
        return mLanguageCode;
    }
    /**
     * Set mLanguageCode
     * @param   mLanguageCode
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setmLanguageCode(String mLanguageCode) {
        this.mLanguageCode = mLanguageCode;
    }
    /**
     * Get serviceDetailDtoList
     * @return List<ServiceDetailDto>
     * @author tungns <tungns@unit.com.vn>
     */
    public List<ServiceDetailDto> getServiceDetailDtoList() {
        return serviceDetailDtoList;
    }
    /**
     * Set serviceDetailDtoList
     * @param   serviceDetailDtoList
     *          type List<ServiceDetailDto>
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setServiceDetailDtoList(
            List<ServiceDetailDto> serviceDetailDtoList) {
        this.serviceDetailDtoList = serviceDetailDtoList;
    }
  
}
