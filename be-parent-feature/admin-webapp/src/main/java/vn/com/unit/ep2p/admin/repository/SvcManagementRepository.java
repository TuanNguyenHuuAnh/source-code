/*******************************************************************************
 * Class        :SvcManagementRepository
 * Created date :2019/04/21
 * Lasted date  :2019/04/21
 * Author       :HungHT
 * Change log   :2019/04/21:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SvcManagementDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementSearchDto;

/**
 * SvcManagementRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface SvcManagementRepository extends DbRepository<Object, Long> {

	/**
     * countSvcManagementList
     * @param search
     * @return
     * @author HungHT
     */
    int countSvcManagementList(@Param("search") SvcManagementSearchDto search);

	/**
     * getSvcManagementList
     * @param search
     * @param offset
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    List<SvcManagementDto> getSvcManagementList(@Param("search") SvcManagementSearchDto search, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("languageCode") String langCode);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    SvcManagementDto findById(@Param("id") Long id, @Param("languageCode") String langCode);
    
    /**
     * getBusinessList
     * 
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author HungHT
     */
    List<Select2Dto> getBusinessList(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("isPaging") boolean isPaging);

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    SvcManagementDto findByProperties1(@Param("properties1") String properties1);
    
    /**
     * getJpmSvcManagementList
     * @param search
     * @param offset
     * @param sizeOfPage
     * @param langCode
     * @return
     * @author trieuvd
     */
    List<SvcManagementDto> getJpmSvcManagementList(@Param("search") SvcManagementSearchDto search, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("languageCode") String langCode);
    
    /**
     * findJpmSvcById
     * @param id
     * @param langCode
     * @return
     * @author trieuvd
     */
    SvcManagementDto findJpmSvcById(@Param("id") Long id, @Param("languageCode") String langCode);
}