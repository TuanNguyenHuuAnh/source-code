/*******************************************************************************
 * Class        ：BusinessServiceImpl
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.BusinessInfoReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.BusinessAddReq;
import vn.com.unit.ep2p.dto.req.BusinessUpdateReq;
import vn.com.unit.ep2p.service.BusinessService;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmBusinessSearchDto;
import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.service.JpmBusinessService;

/**
 * <p>
 * BusinessServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BusinessServiceImpl extends AbstractCommonService implements BusinessService {

    @Autowired
    private JpmBusinessService jpmBusinessService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmBusinessDto create(BusinessAddReq reqBusinessAddDto) throws DetailException {
        JpmBusinessDto businessDto = new JpmBusinessDto();
        try {
            businessDto = convertReqObjToJpmObj(reqBusinessAddDto);
            this.setBusinessCode(reqBusinessAddDto.getBusinessCode(), businessDto);
            // Validate
            String errorCode = this.validate(businessDto);
            if (CommonStringUtil.isNotBlank(errorCode)) {
                throw new DetailException(errorCode, true);
            }
            this.save(businessDto);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021301_APPAPI_BUSINESS_ADD_ERROR);
        }
        return businessDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BusinessUpdateReq reqBusinessUpdateDto) throws DetailException {
        try {
            Long businessId = reqBusinessUpdateDto.getBusinessId();
            if (Objects.isNull(businessId)) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021305_APPAPI_BUSINESS_NOT_FOUND, true);
            }

            JpmBusinessDto businessDto = convertReqObjToJpmObj(reqBusinessUpdateDto);
            businessDto.setBusinessId(businessId);
            
            // Validate
            String errorCode = this.validate(businessDto);
            if (CommonStringUtil.isNotBlank(errorCode)) {
                throw new DetailException(errorCode, false);
            }
            
            this.save(businessDto);
        }  catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021302_APPAPI_BUSINESS_UPDATE_INFO_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long businessId) throws DetailException {
        try {
            if (!jpmBusinessService.deleteById(businessId)) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021305_APPAPI_BUSINESS_NOT_FOUND, true);
            }
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021303_APPAPI_BUSINESS_DELETE_ERROR, true);
        }
    }

    @Override
    public JpmBusinessDto detail(Long businessId) throws DetailException {
        JpmBusinessDto businessDto = jpmBusinessService.getJpmBusinessDtoById(businessId);
        if (null == businessDto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021305_APPAPI_BUSINESS_NOT_FOUND, true);
        }
        return businessDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmBusinessDto save(JpmBusinessDto objectDto) throws DetailException {
        JpmBusiness jpmBusiness = jpmBusinessService.saveJpmBusinessDto(objectDto);
        objectDto.setBusinessId(jpmBusiness.getId());
        return objectDto;
    }
    
    private void setBusinessCode(String businessCode ,JpmBusinessDto businessDto) throws DetailException {
        JpmBusinessDto dto =  jpmBusinessService.getJpmBusinessDtoByBusinessCode(businessCode);
        if(null != dto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021306_APPAPI_BUSINESS_CODE_DUPLICATED, true);
        }else {
            businessDto.setBusinessCode(businessCode);
        }
    }

    private JpmBusinessDto convertReqObjToJpmObj(BusinessInfoReq reqBusinessDto) {
        JpmBusinessDto businessDto = new JpmBusinessDto();
        
        businessDto.setBusinessName(reqBusinessDto.getBusinessName());
        businessDto.setDescription(reqBusinessDto.getDescription());
        businessDto.setCompanyId(reqBusinessDto.getCompanyId());
        businessDto.setAuthority(reqBusinessDto.isAuthority());
        businessDto.setProcessType(reqBusinessDto.getProcessType());
        businessDto.setActived(reqBusinessDto.isActived());

        return businessDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<JpmBusinessDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<JpmBusinessDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JpmBusiness.class, JpmBusinessService.TABLE_ALIAS_JPM_BUSINESS);
            /** init param search repository */
            JpmBusinessSearchDto searchDto = this.buildJpmBusinessSearchDto(commonSearch);

            int totalData = jpmBusinessService.countBySearchCondition(searchDto);
            List<JpmBusinessDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jpmBusinessService.getBusinessDtosByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021300_APPAPI_BUSINESS_LIST_ERROR);
        }
        return resObj;
    }

    /**
     * <p>
     * Builds the jca account search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaAccountSearchDto}
     * @author taitt
     */
    private JpmBusinessSearchDto buildJpmBusinessSearchDto(MultiValueMap<String, String> commonSearch) {
        JpmBusinessSearchDto businessSearchDto = new JpmBusinessSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        String processType = null != commonSearch.getFirst("processType") ? commonSearch.getFirst("processType") : null;

        businessSearchDto.setCompanyId(companyId);
        businessSearchDto.setKeySearch(keySearch);
        businessSearchDto.setBusinessCode(CommonStringUtil.trimToNull(keySearch));
        businessSearchDto.setBusinessName(CommonStringUtil.trimToNull(keySearch));
        businessSearchDto.setProcessType(processType);

        return businessSearchDto;
    }

    /**
     * <p>
     * Validate.
     * </p>
     *
     * @param businessDto
     *            type {@link JpmBusinessDto}
     * @return {@link String} null if valid, error code
     * @author KhuongTH
     */
    private String validate(JpmBusinessDto businessDto) {
        String res = null;
        Long businessId = businessDto.getBusinessId();

        if (Objects.isNull(businessId)) {
            Long companyId = businessDto.getCompanyId();
            String businessCode = businessDto.getBusinessCode();
            JpmBusinessDto validateObj = jpmBusinessService.getBusinessDtoByCodeAndCompanyId(businessCode, companyId);
            if (Objects.nonNull(validateObj)) {
                res = AppApiExceptionCodeConstant.E4021306_APPAPI_BUSINESS_ALREADY_EXISTS;
            }
        }

        return res;
    }
}
