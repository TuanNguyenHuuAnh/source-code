/*******************************************************************************
 * Class        ItemServiceImpl
 * Created date 2020/12/07
 * Lasted date  2020/12/07
 * Author       MinhNV
 * Change log   2020/12/07 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.dto.JcaItemSearchDto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.core.enumdef.param.JcaItemSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaItemService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.utils.DtsStringUtil;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.ItemAddReq;
import vn.com.unit.ep2p.dto.req.ItemUpdateReq;
import vn.com.unit.ep2p.dto.res.ItemInfoRes;
import vn.com.unit.ep2p.service.ItemService;

/**
 * ItemServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ItemServiceImpl extends AbstractCommonService implements ItemService {

    @Autowired
    private JcaItemService jcaItemService;
    
    @Autowired
    private MasterCommonService masterCommonService;

    private ItemInfoRes mapperJcaItemDtoToItemInfoRes(JcaItemDto jcaItemDto){
        return objectMapper.convertValue(jcaItemDto, ItemInfoRes.class);
    }
    
    @Override
    public ObjectDataRes<JcaItemDto> search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException {
        ObjectDataRes<JcaItemDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaItem.class,JcaItemService.TABLE_ALIAS_JCA_ITEM);
            /** init param search repository */
            JcaItemSearchDto reqSearch = this.buildJcaItemSearchDto(commonSearch);
            
            int totalData = this.countJcaItemDtoByCondition(reqSearch);
            List<JcaItemDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getJcaItemDtoByCondition(reqSearch, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021100_APPAPI_ITEM_LIST_ERROR);
        }
        return resObj;
    }
    
    private JcaItemSearchDto buildJcaItemSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaItemSearchDto reqSearch = new JcaItemSearchDto();
        
        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Boolean displayFlag = null != commonSearch.getFirst("displayFlag") ? Boolean.valueOf(commonSearch.getFirst("displayFlag")) : null;
        
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        
       
        reqSearch.setDisplayFlag(displayFlag);
        reqSearch.setCompanyId(companyId);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
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
        }else {
            reqSearch.setFunctionCode(keySearch);
            reqSearch.setFunctionName(keySearch);
            reqSearch.setDescription(keySearch);
        }
        
        return  reqSearch;
    }
    
    private void setFunctionCode(String functionCode, JcaItemDto jcaItemDto) throws DetailException {
        int count = jcaItemService.countJcaItemDtoByFunctionCode(functionCode);
        if(count > 0) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021106_APPAPI_ITEM_FUNCTION_CODE_DUPLICATED,true);
        }else {
            jcaItemDto.setFunctionCode(functionCode);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ItemInfoRes create(ItemAddReq reqItemAddDto) throws DetailException {
        ItemInfoRes resObj = new ItemInfoRes();
        JcaItemDto jcaItemDto = new JcaItemDto();
        try {
            if (null != reqItemAddDto.getFunctionType()) {
                jcaItemDto.setFunctionType(reqItemAddDto.getFunctionType());
            }
            if (null != reqItemAddDto.getFunctionName()) {
                jcaItemDto.setFunctionName(reqItemAddDto.getFunctionName());
            }
            if (null != reqItemAddDto.getFunctionCode()) {
                setFunctionCode(reqItemAddDto.getFunctionCode(), jcaItemDto);
            }
            if (null != reqItemAddDto.getDescription()) {
                jcaItemDto.setDescription(reqItemAddDto.getDescription());
            }
            
            jcaItemDto.setCompanyId(reqItemAddDto.getCompanyId());
            jcaItemDto.setDisplayOrder(reqItemAddDto.getDisplayOrder());
            jcaItemDto = this.save(jcaItemDto);
            resObj = this.mapperJcaItemDtoToItemInfoRes(jcaItemDto);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021101_APPAPI_ITEM_ADD_ERROR);
        }
        return resObj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ItemUpdateReq reqItemUpdateDto) throws DetailException {
        JcaItemDto item = jcaItemService.getJcaItemDtoById(reqItemUpdateDto.getItemId());
        if (null != item) {
            try {
                if (DtsStringUtil.isNotBlank(reqItemUpdateDto.getFunctionType())) {
                    item.setFunctionType(reqItemUpdateDto.getFunctionType());
                }
                if (DtsStringUtil.isNotBlank(reqItemUpdateDto.getFunctionName())) {
                    item.setFunctionName(reqItemUpdateDto.getFunctionName());
                }
               
                if (DtsStringUtil.isNotBlank(reqItemUpdateDto.getDescription())) {
                    item.setDescription(reqItemUpdateDto.getDescription());
                }
                if (reqItemUpdateDto.getDisplayOrder() != BigDecimal.ZERO.intValue()) {
                    item.setDisplayOrder(reqItemUpdateDto.getDisplayOrder());
                }
                item.setCompanyId(reqItemUpdateDto.getCompanyId());
                this.save(item);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021102_APPAPI_ITEM_UPDATE_INFO_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021105_APPAPI_ITEM_NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {
        JcaItemDto jcaItemDto = jcaItemService.getJcaItemDtoById(id);
        if (null != jcaItemDto) {
            try {
                jcaItemService.deletedJcaItemById(id);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021103_APPAPI_ITEM_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021105_APPAPI_ITEM_NOT_FOUND);
        }
    }

    @Override
    public JcaItemDto save(JcaItemDto objectDto) throws DetailException {
        JcaItem jcaItem = jcaItemService.saveJcaItemDto(objectDto);
        objectDto.setItemId(jcaItem.getId());
        return objectDto;
    }

    @Override
    public JcaItemDto detail(Long id) throws DetailException{
        JcaItemDto item = jcaItemService.getJcaItemDtoById(id);
        if (null == item) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021105_APPAPI_ITEM_NOT_FOUND);
        }
        return item;
    }

    @Override
    public int countJcaItemDtoByCondition(JcaItemSearchDto jcaItemSearchDto) {
        return jcaItemService.countJcaItemDtoByCondition(jcaItemSearchDto);
    }

    @Override
    public List<JcaItemDto> getJcaItemDtoByCondition(JcaItemSearchDto jcaItemSearchDto, Pageable pagable) {
        return jcaItemService.getJcaItemDtoByCondition(jcaItemSearchDto, pagable);
    }


    @Override
    public ItemInfoRes getItemInfoById(Long id) throws DetailException{
        JcaItemDto item = this.detail(id);
        return this.mapperJcaItemDtoToItemInfoRes(item);
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaItemSearchEnum.values()); 
    }

}
