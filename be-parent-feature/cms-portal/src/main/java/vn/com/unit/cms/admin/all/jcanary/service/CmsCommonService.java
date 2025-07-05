package vn.com.unit.cms.admin.all.jcanary.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.admin.all.dto.PopupLanguageDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageSearchDto;
import vn.com.unit.core.service.CommonService;

//import vn.com.unit.jcanary.dto.PopupLanguageDto;
//import vn.com.unit.jcanary.dto.ProductLanguageSearchDto;

/**
 * CommonService
 * 
 * @version 01-00
 * @since 01-00
 * @author nhutnn
 */
public interface CmsCommonService extends CommonService {

    /** getMaxCode
     *
     * @param tableName
     * @param prefix
     * @return max code
     * @author nhutnn
     */
    String getMaxCode(String tableName, String prefix);
    
    /** getMaxCodeMMYY
    *
    * @param tableName
    * @param prefix
    * @return max code
    * @author longdch
    */
   String getMaxCodeYYMM(String tableName, String prefix);

    /** getListProductMicrosite
     *
     * @param languageCode
     * @author nhutnn
     */
    List<ProductLanguageSearchDto> getListProductMicrosite(String languageCode);
        
    /**
     * searchPopupByCondition
     *26/04/2019
     * @param PopupLanguageDto
     * @return
     * @author TranLTH
     */
    public List<PopupLanguageDto> searchPopupByCondition(String languageCode, Date expiryDate);
}
