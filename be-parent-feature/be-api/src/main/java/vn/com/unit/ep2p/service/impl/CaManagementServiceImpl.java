/*******************************************************************************
 * Class        ：CaManagementServiceImpl
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
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
import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.core.enumdef.param.JcaCaManagementSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaCaManagementService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.utils.DtsStringUtil;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.CaManagementAddReq;
import vn.com.unit.ep2p.dto.req.CaManagementUpdateReq;
import vn.com.unit.ep2p.dto.res.CaManagementInfoRes;
import vn.com.unit.ep2p.service.CaManagementService;

/**
 * <p>
 * CaManagementServiceImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CaManagementServiceImpl extends AbstractCommonService implements CaManagementService {

    /** The jca ca management service. */
    @Autowired
    private JcaCaManagementService jcaCaManagementService;
    
    @Autowired
    private MasterCommonService masterCommonService;

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.CaManagementService#update(vn.com.unit.mbal.api.req.dto.CaManagementUpdateReq)
     */
    @Override
    public void update(CaManagementUpdateReq caManagementUpdateReq) throws Exception {
        Long id = caManagementUpdateReq.getCaManagementId();

        JcaCaManagementDto jcaCaManagement = jcaCaManagementService.getJcaCaManagementDtoById(id);
        if (null != jcaCaManagement) {
            try {
                if (null != caManagementUpdateReq.getCompanyId()) {
                    jcaCaManagement.setCompanyId(caManagementUpdateReq.getCompanyId());
                }
                if (null != caManagementUpdateReq.getAccountId()) {
                    jcaCaManagement.setAccountId(caManagementUpdateReq.getAccountId());
                }
                if (DtsStringUtil.isNotBlank(caManagementUpdateReq.getCaName())) {
                    jcaCaManagement.setCaName(caManagementUpdateReq.getCaName());
                }
                if (DtsStringUtil.isNotBlank(caManagementUpdateReq.getCaPassword())) {
                    jcaCaManagement.setCaPassword(caManagementUpdateReq.getCaPassword());
                }
                if (DtsStringUtil.isNotBlank(caManagementUpdateReq.getCaSlot())) {
                    jcaCaManagement.setCaSlot(caManagementUpdateReq.getCaSlot());
                }
                if (DtsStringUtil.isNotBlank(caManagementUpdateReq.getCaLabel())) {
                    jcaCaManagement.setCaLabel(caManagementUpdateReq.getCaLabel());
                }
                jcaCaManagement.setCaDefault(caManagementUpdateReq.isCaDefault());
                this.save(jcaCaManagement);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022203_APPAPI_CA_MANAGEMENT_UPDATE_INFO_ERROR);
            }
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.CaManagementService#create(vn.com.unit.mbal.api.req.dto.CaManagementAddReq)
     */
    @Override
    public CaManagementInfoRes create(CaManagementAddReq caManagementAddReq) throws Exception {
        JcaCaManagementDto jcaCaManagement = new JcaCaManagementDto();
        try {
            if (null != caManagementAddReq.getCompanyId()) {
                jcaCaManagement.setCompanyId(caManagementAddReq.getCompanyId());
            }
            if (null != caManagementAddReq.getAccountId()) {
                jcaCaManagement.setAccountId(caManagementAddReq.getAccountId());
            }
            if (null != caManagementAddReq.getCaName()) {
                jcaCaManagement.setCaName(caManagementAddReq.getCaName());
            }
            if (null != caManagementAddReq.getCaPassword()) {
                jcaCaManagement.setCaPassword(caManagementAddReq.getCaPassword());
            }
            if (null != caManagementAddReq.getCaSlot()) {
                jcaCaManagement.setCaSlot(caManagementAddReq.getCaSlot());
            }
            if (null != caManagementAddReq.getCaLabel()) {
                jcaCaManagement.setCaLabel(caManagementAddReq.getCaLabel());
            }
            jcaCaManagement.setCaDefault(caManagementAddReq.isCaDefault());
            jcaCaManagement = this.save(jcaCaManagement);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402802_APPAPI_ACCOUNT_ADD_ERROR);
        }
        return this.mapperJcaCaManagementDtoToCaManagementInfoRes(jcaCaManagement);
    }


    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.CaManagementService#getCaManagementInfoResById(java.lang.Long)
     */
    @Override
    public CaManagementInfoRes getCaManagementInfoResById(Long id) throws Exception {
        JcaCaManagementDto jcaCaManagementDto = this.detail(id);
        return objectMapper.convertValue(jcaCaManagementDto, CaManagementInfoRes.class);
    }


    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#save(java.lang.Object)
     */
    @Override
    public JcaCaManagementDto save(JcaCaManagementDto objectDto) throws DetailException {
        JcaCaManagement jcaCaManagement = jcaCaManagementService.saveJcaCaManagementDto(objectDto);
        objectDto.setCaManagementId(jcaCaManagement.getId());
        return objectDto;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
     */
    @Override
    public void delete(Long id) throws DetailException {
        JcaCaManagement jcaCaManagement = jcaCaManagementService.getJcaCaManagementById(id);
        if (null != jcaCaManagement) {
            try {
                // hardcode
                jcaCaManagement.setDeletedId(BigDecimal.ONE.longValue());
                jcaCaManagement.setDeletedDate(commonService.getSystemDate());
                jcaCaManagementService.saveJcaCaManagement(jcaCaManagement);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022204_APPAPI_CA_MANAGEMENT_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022206_APPAPI_CA_MANAGEMENT_NOT_FOUND);
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
     */
    @Override
    public JcaCaManagementDto detail(Long id) throws DetailException {
        JcaCaManagementDto jcaCaManagementDto = jcaCaManagementService.getJcaCaManagementDtoById(id);
        if (null == jcaCaManagementDto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022206_APPAPI_CA_MANAGEMENT_NOT_FOUND);
        }
        return jcaCaManagementDto;
    }

    /**
     * <p>
     * Mapper jca ca management dto to ca management info res.
     * </p>
     *
     * @param jcaCaManagementDto
     *            type {@link JcaCaManagementDto}
     * @return {@link CaManagementInfoRes}
     * @author taitt
     */
    private CaManagementInfoRes mapperJcaCaManagementDtoToCaManagementInfoRes(JcaCaManagementDto jcaCaManagementDto) {
        return objectMapper.convertValue(jcaCaManagementDto, CaManagementInfoRes.class);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap, org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<JcaCaManagementDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<JcaCaManagementDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable,JcaCaManagement.class, JcaCaManagementService.TABLE_ALIAS_JCA_M_ACCOUNT_CA);
            /** init param search repository */
            JcaCaManagementSearchDto searchDto = this.buildJcaCaManagementSearchDto(commonSearch);
            
            int totalData = jcaCaManagementService.countCaManagementDtoByCondition(searchDto);
            List<JcaCaManagementDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jcaCaManagementService.getCaManagementDtoByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022201_APPAPI_CA_MANAGEMENT_LIST_ERROR);
        }
        return resObj;
    }
    
    /**
     * <p>
     * Builds the jca ca management search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaCaManagementSearchDto}
     * @author taitt
     */
    private JcaCaManagementSearchDto buildJcaCaManagementSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaCaManagementSearchDto jcaCaManagementSearchDto = new JcaCaManagementSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        
        jcaCaManagementSearchDto.setCompanyId(companyId);

        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaCaManagementSearchEnum.valueOf(enumValue)) {
                case CA_NAME:
                    jcaCaManagementSearchDto.setCaName(keySearch);
                    break;
                case CA_SLOT:
                    jcaCaManagementSearchDto.setCaSlot(keySearch);
                    break;
                    
                case ACCOUNT_NAME:
                    jcaCaManagementSearchDto.setAccountName(keySearch);
                    break;

                default:
                    jcaCaManagementSearchDto.setCaName(keySearch);
                    jcaCaManagementSearchDto.setCaSlot(keySearch);
                    jcaCaManagementSearchDto.setAccountName(keySearch);
                    break;
                }
            }
        }else {
            jcaCaManagementSearchDto.setCaName(keySearch);
            jcaCaManagementSearchDto.setCaSlot(keySearch);
            jcaCaManagementSearchDto.setAccountName(keySearch);
        }
        
        return jcaCaManagementSearchDto;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaCaManagementSearchEnum.values()); 
    }
}
