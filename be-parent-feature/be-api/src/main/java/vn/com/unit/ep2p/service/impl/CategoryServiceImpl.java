/*******************************************************************************
 * Class        ：CategoryServiceImpl
 * Created date ：2020/12/17
 * Lasted date  ：2020/12/17
 * Author       ：TrongNV
 * Change log   ：2020/12/17：01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.enumdef.param.JcaCategorySearchEnum;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoCategory;
import vn.com.unit.ep2p.core.efo.service.EfoCategoryService;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.CategoryAddReq;
import vn.com.unit.ep2p.dto.req.CategoryUpdateReq;
import vn.com.unit.ep2p.dto.res.CategoryInfoRes;
import vn.com.unit.ep2p.dto.res.CategoryListObjectRes;
import vn.com.unit.ep2p.service.CategoryService;

/**
 * <p>
 * CategoryServiceImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CategoryServiceImpl extends AbstractCommonService implements CategoryService {
    
    /** The efo category service. */
    @Autowired
    private EfoCategoryService efoCategoryService;
    
    @Autowired
    private MasterCommonService masterCommonService;

    /**
     * <p>
     * Mapper efo category dto to category info res.
     * </p>
     *
     * @param categoryDto
     *            type {@link EfoCategoryDto}
     * @return {@link CategoryInfoRes}
     * @author taitt
     */
    private CategoryInfoRes mapperEfoCategoryDtoToCategoryInfoRes(EfoCategoryDto categoryDto){
        return objectMapper.convertValue(categoryDto, CategoryInfoRes.class);
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#getAccountDtoByCondition(vn.com.unit.core.dto.JcaAccountSearchDto, org.springframework.data.domain.Pageable)
     */
    @Override
    public List<EfoCategoryDto> getCategoryDtoByCondition(EfoCategorySearchDto categorySearchDto, Pageable pagable) {
        return efoCategoryService.getCategoryDtoByCondition(categorySearchDto, pagable);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#countAccountDtoByCondition(vn.com.unit.core.dto.JcaAccountSearchDto)
     */
    @Override
    public int countCategoryDtoByCondition(EfoCategorySearchDto efoCategorySearchDto) {
        return efoCategoryService.countCategoryDtoByCondition(efoCategorySearchDto);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#create(vn.com.unit.mbal.api.req.dto.AccountAddReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryInfoRes create(CategoryAddReq categoryAddReq) throws Exception {
        EfoCategoryDto efoCategory = new EfoCategoryDto();
        try {
            efoCategory.setCompanyId(categoryAddReq.getCompanyId());
            efoCategory.setCategoryName(categoryAddReq.getCategoryName());
            efoCategory.setDescription(categoryAddReq.getDescription());
            efoCategory.setDisplayOrder(categoryAddReq.getDisplayOrder());
            efoCategory = this.save(efoCategory);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022702_APPAPI_CATEGORY_ADD_ERROR);
        }
        return this.mapperEfoCategoryDtoToCategoryInfoRes(efoCategory);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#update(vn.com.unit.mbal.api.req.dto.AccountUpdateReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CategoryUpdateReq categoryUpdateReq) throws Exception {
        Long id = categoryUpdateReq.getCategoryId();

        EfoCategoryDto efoCategory = efoCategoryService.getEfoCategoryDtoById(id);
        if (null != efoCategory) {
            try {
                efoCategory.setCategoryName(categoryUpdateReq.getCategoryName());
                efoCategory.setDescription(categoryUpdateReq.getDescription());
                efoCategory.setDisplayOrder(categoryUpdateReq.getDisplayOrder());
                this.save(efoCategory);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022703_APPAPI_CATEGORY_UPDATE_INFO_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022706_APPAPI_CATEGORY_NOT_FOUND, true);
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#save(java.lang.Object)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoCategoryDto save(EfoCategoryDto objectDto) throws DetailException{
        EfoCategory efoCategory = efoCategoryService.saveEfoCategoryDto(objectDto);
        objectDto.setCategoryId(efoCategory.getId());
        return objectDto;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
     */
    @Override
    public EfoCategoryDto detail(Long id) throws DetailException{
        return efoCategoryService.getEfoCategoryDtoById(id);
    }
    
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long categoryId) throws DetailException {
        EfoCategoryDto efoCategoryDto = efoCategoryService.getEfoCategoryDtoById(categoryId);
        if (null != efoCategoryDto) {
            try {
                efoCategoryService.deletedEfoCategoryById(categoryId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022704_APPAPI_CATEGORY_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022706_APPAPI_CATEGORY_NOT_FOUND);
        }
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#getAccountInfoResById(java.lang.Long)
     */
    @Override
    public CategoryInfoRes getCategoryInfoResById(Long id) throws Exception {
        EfoCategoryDto efoCategoryDto = this.detail(id);
        if(null != efoCategoryDto) {
            return objectMapper.convertValue(efoCategoryDto, CategoryInfoRes.class);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022706_APPAPI_CATEGORY_NOT_FOUND);
        }
        
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap, org.springframework.data.domain.Pageable)
     */
    @Override
    public CategoryListObjectRes search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException {
        CategoryListObjectRes resObj = new CategoryListObjectRes();

        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable);
            /** init param search repository */
            EfoCategorySearchDto reqSearch = this.buildEfoCategorySearchDto(commonSearch);
            
            int totalData = this.countCategoryDtoByCondition(reqSearch);
            List<EfoCategoryDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getCategoryDtoByCondition(reqSearch,pageableAfterBuild);
            }
            resObj = resObjectService.responseObjectMapAll(CategoryListObjectRes.class, datas, totalData);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022701_APPAPI_CATEGORY_LIST_ERROR);
        }
        return resObj;
    }

    /**
     * <p>
     * Builds the pageable.
     * </p>
     *
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Pageable}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    private Pageable buildPageable(Pageable pageable) throws DetailException {
        Sort sort = commonService.buildSortAlias(pageable.getSort(),EfoCategory.class, CoreConstant.ALIAS_EFO_CATEGORY);
        return PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), sort);
    }

    
    /**
     * <p>
     * Builds the efo category search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link EfoCategorySearchDto}
     * @author taitt
     */
    private EfoCategorySearchDto buildEfoCategorySearchDto(MultiValueMap<String, String> commonSearch) {
        EfoCategorySearchDto reqSearch = new EfoCategorySearchDto();
        
        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Boolean actived = null != commonSearch.getFirst("active") ? Boolean.valueOf(commonSearch.getFirst("active")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        
        reqSearch.setActived(actived);
        reqSearch.setCompanyId(companyId);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaCategorySearchEnum.valueOf(enumValue)) {
                case NAME:
                    reqSearch.setName(keySearch);
                    break;
                case DESCRIPTION:
                    reqSearch.setDescription(keySearch);
                    break;

                default:
                    reqSearch.setName(keySearch);
                    reqSearch.setDescription(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setName(keySearch);
            reqSearch.setDescription(keySearch);
        }
        
        return  reqSearch;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaCategorySearchEnum.values()); 
    }
   
}
