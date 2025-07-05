
package vn.com.unit.cms.admin.all.jcanary.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.PopupLanguageDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageSearchDto;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.jcanary.dto.PopupLanguageDto;
//import vn.com.unit.jcanary.dto.ProductLanguageSearchDto;

/**
 * CommonRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author nhutnn
 */
public interface CommonRepository extends DbRepository<String, Long> {

    String getMaxCode(@Param("tableName") String tableName, @Param("prefix") String prefix);

    /**
     * getListProductMicrosite
     *
     * @param languageCode
     * @author nhutnn
     */
    List<ProductLanguageSearchDto> getListProductMicrosite(@Param("languageCode") String languageCode);
    
    /**
     * searchPopupByCondition
     *26/04/2019
     * @param PopupLanguageDto
     * @return
     * @author TranLTH
     */
    List<PopupLanguageDto> searchPopupByCondition(@Param("languageCode") String languageCode, @Param("expiryDate") Date expiryDate);

}
