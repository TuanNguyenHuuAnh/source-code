package vn.com.unit.ep2p.admin.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.core.service.JcaItemService;
import vn.com.unit.ep2p.admin.dto.ItemManagementSearchDto;

public interface ItemManagementService extends JcaItemService{

    PageWrapper<JcaItemDto> search(int page, int pageSize, ItemManagementSearchDto searchDto) ;
  
    JcaItemDto findItemByFunctionCode(ItemManagementSearchDto searchDto);

    void doSaveItemManagement(JcaItemDto jcaItemDto) throws Exception;

    void getConstantDisplay(ModelAndView mav, String lang);

    void doDeleteItemManagement(Long id);

    Integer getMaxDisplayOrder();
    
    JcaItemDto findByFunctionCodeAndCompanyId(String functionCode, Long companyId);
    
    /**
     * autoCreateItem
     * 
     * @param itemCode
     * @param itemName
     * @param suffix
     * @param businessCode
     * @param description
     * @param companyId
     * @param subType
     * @param functionType
     * @return
     * @author HungHT
     */
    JcaItem autoCreateItem(String itemCode, String itemName, String suffix, String businessCode, String description, Long companyId,
            String subType, String functionType);
    
    JcaItemDto findById(Long id);
    
    /**
     * findByBusinessId
     * @param keySearch
     * @param companyId
     * @param businessId
     * @param isPaging
     * @param functionCodeAsId
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findByBusinessId(String keySearch, Long companyId, Long businessId, boolean isPaging, boolean functionCodeAsId);
    
    /**
     * getBusinessSelect2ByTermAndCompanyId
     * @param term
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<Select2Dto> getBusinessSelect2ByTermAndCompanyId(String term, Long companyId);
    
    /**
     * getSelect2ByCondition
     * @param keySearch
     * @param companyId
     * @param functionTypes
     * @param subType
     * @param isPaging
     * @return
     * @author trieuvd
     */
    
    List<Select2Dto> getSelect2ByCondition(String keySearch, Long companyId, List<String> functionTypes, String subType, boolean isPaging);
    
    /**
     * findByFunctionCodeFunctionTypeAndSubType
     * @param functionCode
     * @param functionType
     * @param subType
     * @param companyId
     * @return
     * @author trieuvd
     */
    JcaItemDto findByFunctionCodeFunctionTypeAndSubType(String functionCode, String functionType, String subType, Long companyId);
    
    /**
     * findByProcessIdFunctionTypeAndSubType
     * @param processId
     * @param functionType
     * @param subType
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<JcaItemDto> findByProcessIdFunctionTypeAndSubType(Long processId, String functionType, String subType, Long companyId);
    
    void setFunctionType(ModelAndView mav, String lang, PageWrapper<JcaItemDto> pageWrapper);
}
