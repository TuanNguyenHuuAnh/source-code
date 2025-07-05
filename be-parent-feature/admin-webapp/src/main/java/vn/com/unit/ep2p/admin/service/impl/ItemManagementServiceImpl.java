package vn.com.unit.ep2p.admin.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.dto.JcaItemSearchDto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.core.enumdef.param.JcaItemSearchEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaItemService;
import vn.com.unit.core.service.impl.JcaItemServiceImpl;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.ItemManagementSearchDto;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.admin.repository.ItemManagementRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ItemManagementService;

/**
 * Created by quangnd on 7/27/2018.
 */
@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ItemManagementServiceImpl extends JcaItemServiceImpl implements ItemManagementService, AbstractCommonService {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ItemManagementRepository itemManagementRepository;
    
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    JCommonService comService;
    
    @Autowired
    private JcaConstantService jcaConstantService;
    
    
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(ItemManagementServiceImpl.class);

  
  
    @Override
    public PageWrapper<JcaItemDto> search(int page, int pageSize, ItemManagementSearchDto searchDto) {
        // Init PageWrapper
        PageWrapper<JcaItemDto> pageWrapper = new PageWrapper<JcaItemDto>();
        int sizeOfPage = systemConfig.settingPageSizeList(pageSize, pageWrapper, page);

        /** init pageable */
        Pageable pageableAfterBuild = null;
        try {
            pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaItem.class,
                    JcaItemService.TABLE_ALIAS_JCA_ITEM);
        } catch (DetailException e1) {
            e1.printStackTrace();
        }

        List<String> values = searchDto.getFieldValues();
        /** init param search repository */
        if (CollectionUtils.isNotEmpty(searchDto.getFieldValues())) {
            searchDto.setStrFieldValues(String.join(",", searchDto.getFieldValues()));
            searchDto.setFieldValues(null);
        } else {
            searchDto.setFieldValues(null);
        }
        
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaItemSearchDto reqSearch = this.buildJcaItemSearchDto(commonSearch);
        searchDto.setFieldValues(values);
        
        try {
            // set SearchParm
            int count = this.countJcaItemDtoByCondition(reqSearch);
            if (count > 0) {
                List<JcaItemDto> result = this.getJcaItemDtoByCondition(reqSearch, pageableAfterBuild);
                pageWrapper.setDataAndCount(result, count);
            }
        } catch (Exception e) {
            logger.error("##search##", e);
            pageWrapper.setDataAndCount(null, 0);
            throw e;
        }
        return pageWrapper;
    }
    
    private JcaItemSearchDto buildJcaItemSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaItemSearchDto reqSearch = new JcaItemSearchDto();

        String keySearch = null != commonSearch.getFirst("fieldSearch") ? commonSearch.getFirst("fieldSearch")
                : DtsConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId"))
                : null;
        Boolean displayFlag = null != commonSearch.getFirst("displayFlag")
                ? Boolean.valueOf(commonSearch.getFirst("displayFlag"))
                : null;

        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("strFieldValues"))
                ? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("strFieldValues"), ","))
                : null;

        reqSearch.setDisplayFlag(displayFlag);
        reqSearch.setCompanyId(companyId);
        reqSearch.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());

        if (CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaItemSearchEnum.valueOf(enumValue)) {
                case FUNCTION_CODE:
                    reqSearch.setFunctionCode(keySearch);
                    break;
                case FUNCTION_NAME:
                    reqSearch.setFunctionName(keySearch);
                    break;

                case DESCRIPTION:
                    reqSearch.setDescription(keySearch);
                    break;

                default:
                    reqSearch.setFunctionCode(keySearch);
                    reqSearch.setFunctionName(keySearch);
                    reqSearch.setDescription(keySearch);
                    break;
                }
            }
        } else {
            reqSearch.setFunctionCode(keySearch);
            reqSearch.setFunctionName(keySearch);
            reqSearch.setDescription(keySearch);
        }

        return reqSearch;
    }

    @Override
    public  JcaItemDto findItemByFunctionCode(ItemManagementSearchDto searchDto) {
        return itemManagementRepository.findItemByFunctionCode(searchDto);
    }

    @Override
    public void doSaveItemManagement(JcaItemDto jcaItemDto) throws Exception {
        Long userId = UserProfileUtils.getAccountId();
        Long id = jcaItemDto.getItemId();

        JcaItem item = null;
        if (null != id) {
            item = itemManagementRepository.findOne(id);
            if (null == item) {
                throw new BusinessException("Not found item by id= " + id);
            }
            item.setUpdatedId(userId);
            item.setUpdatedDate(CommonDateUtil.getSystemDateTime());
        } else {
            item = new JcaItem();
            item.setCreatedId(userId);
            item.setCreatedDate(CommonDateUtil.getSystemDateTime());
        }

        item.setFunctionCode(jcaItemDto.getFunctionCode());
        item.setFunctionName(jcaItemDto.getFunctionName());
        item.setCompanyId(jcaItemDto.getCompanyId());
        item.setDescription(jcaItemDto.getDescription());
        item.setDisplayOrder(jcaItemDto.getDisplayOrder());
//        item.setActived(jcaItemDto.getActived());
        item.setFunctionType(jcaItemDto.getFunctionType());
//        item.setBusinessCode(itemManagementDto.getBusinessCode());
//        item.setSubType(itemManagementDto.getSubType());
//        item.setMenuType(itemManagementDto.getMenuType());

//        if (ConstantCore.STR_THREE.equals(itemManagementDto.getFunctionType())) {
//            item.setBusinessId(Long.parseLong(itemManagementDto.getBusinessCode()));
// }
        if (item.getId() != null) {
            itemManagementRepository.update(item);
        } else {
            itemManagementRepository.create(item);
        }
        jcaItemDto.setItemId(item.getId());
    }

    @Override
    public void getConstantDisplay(ModelAndView mav, String lang) {
        //List<ConstantDisplay> lstFunctionType = constantDisplayService.findByType("ITEM_TYPE", lang);
        List<JcaConstantDto> lstFunctionType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_ITEM", "ITEM_TYPE", lang);
        
//       List<ConstantDisplay> lstSubType = constantDisplayService.findByType("ITEM_SUB_TYPE");
//        List<ConstantDisplay> lstMenuType = constantDisplayService.findByType("M07");
//        List<ConstantDisplay> lstDisplayFlag = constantDisplayService.findByType("DISPLAY");
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        mav.addObject("lstFunctionType",lstFunctionType);
//        mav.addObject("lstSubType",lstSubType);
//        mav.addObject("lstMenuType",lstMenuType);
//        mav.addObject("lstDisplayFlag",lstDisplayFlag);
        
//        boolean companyAdmin = UserProfileUtils.isCompanyAdmin();
//        Long companyId = UserProfileUtils.getCompanyId();
//        mav.addObject("lstBusiness", businessDefinitionService.findBusinessDefinitionListByCompanyAdminAndCompanyId(companyAdmin, companyId));
    }

    @Override
    public void doDeleteItemManagement(Long id) {
        itemManagementRepository.delete(id);
    }

    @Override
    public Integer getMaxDisplayOrder() {
        return itemManagementRepository.getMaxDisplayOrder();
    }

//    private void constantDisplayFindByType(List<JcaItemDto> result){
//        List<JcaConstantDto> lstFunctionType = jcaConstantService.getListJcaConstantDtoByKind("ITEM_TYPE", UserProfileUtils.getLanguage());
//        List<JcaConstantDto> lstSubType = jcaConstantService.getListJcaConstantDtoByKind("ITEM_SUB_TYPE", UserProfileUtils.getLanguage());
//        List<JcaConstantDto> lstMenuType = jcaConstantService.getListJcaConstantDtoByKind("MENU_TYPE", UserProfileUtils.getLanguage());
//
//        for(JcaItemDto item : result){
//            List<JcaConstantDto> functionType = StreamUtils.searchElementByKey(lstFunctionType,JcaConstantDto::getCode,item.getFunctionType());
//            if(CollectionUtils.isNotEmpty(functionType)){
//                item.setFunctionType(functionType.get(0).getName());
//            }
////            List<ConstantDisplay> subType = StreamUtils.searchElementByKey(lstSubType,ConstantDisplay::getCode,item.getSubType());
////            if(CollectionUtils.isNotEmpty(subType)){
////                item.setSubType(subType.get(0).getCatOfficialName());
////            }
////            List<ConstantDisplay> menuType = StreamUtils.searchElementByKey(lstMenuType,ConstantDisplay::getCat,item.getMenuType());
////            if(CollectionUtils.isNotEmpty(menuType)){
////                item.setMenuType(menuType.get(0).getCatOfficialName());
////            }
//        }
//
//    }

//    private void setSearchParm(ItemManagementSearchDto searchDto) {
//        if (null == searchDto.getFieldValues()) {
//            searchDto.setFieldValues(new String());
//        }
//
//        if (searchDto.getFieldValues().isEmpty()) {
//            searchDto.setFunctionCode(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
//            searchDto.setFunctionName(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
//            searchDto.setDescription(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
//        } else {
//            String field =searchDto.getFieldValues(); 
//                if (StringUtils.equals(field, ItemManagementSearchEnum.FUNCTION_CODE.name())) {
//                    searchDto.setFunctionCode(searchDto.getFieldSearch().trim());
//                    
//                }else
//                    if (StringUtils.equals(field, ItemManagementSearchEnum.FUNCTION_NAME.name())) {
//                    searchDto.setFunctionName(searchDto.getFieldSearch().trim());
//                   
//                }else
//                if (StringUtils.equals(field, ItemManagementSearchEnum.DESCRIPTION.name())) {
//                    searchDto.setDescription(searchDto.getFieldSearch().trim());
//                }
//            
//        }
//
//        // Add company_id
//        //searchDto.setCompanyId(UserProfileUtils.getCompanyId());
////        searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
////        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
//    }


    @Override
    public JcaItemDto findByFunctionCodeAndCompanyId(String functionCode, Long companyId) {
        return itemManagementRepository.findByFunctionCodeAndCompanyId(functionCode, companyId);
    }
    
    /**
     * autoCreateItem
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
    public JcaItem autoCreateItem(String itemCode, String itemName, String suffix, String businessCode, String description, Long companyId,
            String subType, String functionType) {
        JcaItem item = null;
        String code = itemCode;
        String name = itemName.concat(suffix);
        int index = 0;
        do {
            index += 1;
            if (index > 10) {
                return null;
            }
            // Check exists item
            JcaItemDto itemCheck = findByFunctionCodeAndCompanyId(code, companyId);
            if (itemCheck == null) {
                item = new JcaItem();
                item.setFunctionCode(code);
                item.setFunctionName(name);
                item.setFunctionType("2");
                item.setCompanyId(UserProfileUtils.getCompanyId());
                item.setDescription(description);
//                item.setBusinessCode(businessCode);
//                item.setSubType(subType);
               
                item.setDisplayOrder(getMaxDisplayOrder());
                item.setCreatedId(UserProfileUtils.getAccountId());
                item.setCreatedDate(CommonDateUtil.getSystemDateTime());
                item.setCompanyId(companyId);
                if (item.getId() != null) {
                    itemManagementRepository.update(item);
                } else {
                    itemManagementRepository.create(item);
                }
                break;
            } else {
                code = itemCode.concat(String.valueOf(index));
                name = itemName.concat(String.valueOf(index)).concat(suffix);
            }
        } while (item == null);
        return item;
    }


    @Override
    public JcaItemDto findById(Long id) {
        return itemManagementRepository.findById(id);
    }

    @Override
    public List<Select2Dto> findByBusinessId(String keySearch, Long companyId, Long businessId, boolean isPaging, boolean functionCodeAsId) {
        return itemManagementRepository.findByBusinessId(keySearch, companyId, businessId, isPaging, functionCodeAsId);
    }

    @Override
    public List<Select2Dto> getBusinessSelect2ByTermAndCompanyId(String term, Long companyId) {
//        return null;
        return itemManagementRepository.getBusinessSelect2ByTermAndCompanyId(term, companyId, UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
    }

    @Override
    public List<Select2Dto> getSelect2ByCondition(String keySearch, Long companyId, List<String> functionTypes, String subType, boolean isPaging) {
        return itemManagementRepository.getSelect2ByCondition(keySearch, companyId, functionTypes, subType, isPaging);
    }

    @Override
    public JcaItemDto findByFunctionCodeFunctionTypeAndSubType(String functionCode, String functionType, String subType,
            Long companyId) {
        return itemManagementRepository.findByFunctionCodeFunctionTypeAndSubType(functionCode, functionType, subType, companyId);
    }

    @Override
    public List<JcaItemDto> findByProcessIdFunctionTypeAndSubType(Long processId, String functionType, String subType,
            Long companyId) {
        return itemManagementRepository.findByProcessIdFunctionTypeAndSubType(processId, functionType, subType, companyId);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.admin.service.AbstractCommonService#getCommonService()
     */
    @Override
    public JCommonService getCommonService() {
        return comService;
    }

    @Override
    public void setFunctionType(ModelAndView mav, String lang, PageWrapper<JcaItemDto> pageWrapper) {
        List<JcaConstantDto> lstFunctionType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_ITEM", "ITEM_TYPE", lang);

        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        if (pageWrapper.getCountAll() > 0) {
            for (JcaItemDto jcaItemDto : pageWrapper.getData()) {
                if ("1".equals(jcaItemDto.getFunctionType())) {
                    jcaItemDto.setFunctionType("System");
                } else {
                    for (JcaConstantDto jcaConstantLangDto : lstFunctionType) {
                        if (jcaItemDto.getFunctionType().equals(jcaConstantLangDto.getCode())) {
                            jcaItemDto.setFunctionType(jcaConstantLangDto.getName());
                        }
                    }
                }
            }

    }
    }
  
    
}
