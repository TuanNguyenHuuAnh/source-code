package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.dto.ComponentAuthorityDto;
import vn.com.unit.ep2p.dto.ComponentByAuthority;

/**
 * ComponentAuthorityRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface ComponentAuthorityJcanaryRepository { 
//extends DbRepository<ComponentAuthority, Long> {

    /**
     * getItemListByForm
     * 
     * @param keySearch
     * @param companyid
     * @param isPaging
     * @param functionCodeAsId
     * @param mode
     * @return
     * @author HungHT
     */
    List<Select2Dto> getItemListByForm(@Param("keySearch") String keySearch, @Param("companyid") Long companyid,
            @Param("isPaging") boolean isPaging, @Param("functionCodeAsId") boolean functionCodeAsId, @Param("mode") Long mode);

    /**
     * getComponentAuthorityList
     * @param itemId
     * @param formId
     * @return
     * @author HungHT
     */
    List<ComponentAuthorityDto> getComponentAuthorityList(@Param("itemId") Long itemId, @Param("formId") Long formId);

    /**
     * deleteByItemId
     * 
     * @param itemId
     * @author HungHT
     */
    @Modifying
    void deleteByItemId(@Param("itemId") Long itemId);
    
    /**
     * getComponentListByAuthority
     * @param formId
     * @return
     * @author HungHT
     */
    List<ComponentByAuthority> getComponentListByAuthority(@Param("formId") Long formId);
}