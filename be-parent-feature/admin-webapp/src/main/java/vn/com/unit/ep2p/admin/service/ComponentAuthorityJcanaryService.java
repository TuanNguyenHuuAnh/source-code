package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

public interface ComponentAuthorityJcanaryService {   
    
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
    List<Select2Dto> getItemListByForm(String keySearch, Long companyid, boolean isPaging, boolean functionCodeAsId, Long mode);

    /**
     * getComponentAuthorityList
     * 
     * @param itemId
     * @param formId
     * @return
     * @author HungHT
     */
//    List<ComponentAuthorityDto> getComponentAuthorityList(Long itemId, Long formId);
    
    /**
     * saveComponentAuthority
     * 
     * @param componentAuthority
     * @author HungHT
     */
//    void saveComponentAuthority(ComponentAuthsorityListDto componentAuthority);
    
    /**
     * getCompListByCurrentUserAndFormId
     * 
     * @param formId
     * @return
     * @author HungHT
     */
//    List<ComponentByAuthority> getCompListByCurrentUserAndFormId(Long formId);
}