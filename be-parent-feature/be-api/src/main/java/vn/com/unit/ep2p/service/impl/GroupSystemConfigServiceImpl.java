/*******************************************************************************
 * Class        ：GroupSystemConfigServiceImpl
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaGroupSystemConfigDto;
import vn.com.unit.core.dto.JcaGroupSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaGroupSystemConfig;
import vn.com.unit.core.enumdef.param.JcaGroupSystemConfigSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaGroupSystemConfigService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.GroupSystemConfigAddReq;
import vn.com.unit.ep2p.dto.req.GroupSystemConfigUpdateReq;
import vn.com.unit.ep2p.dto.res.GroupSystemConfigInfoRes;
import vn.com.unit.ep2p.service.GroupSystemConfigService;

/**
 * GroupSystemConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
public class GroupSystemConfigServiceImpl extends AbstractCommonService implements GroupSystemConfigService {

    @Autowired
    private JcaGroupSystemConfigService jcaGroupSystemConfigService;
    
    @Autowired
    private MasterCommonService masterCommonService;

    private List<JcaGroupSystemConfigDto> getGroupSystemConfigDtoByCondition(JcaGroupSystemConfigSearchDto reqSearch, Pageable pageable) {
        return jcaGroupSystemConfigService.getGroupSystemConfigDtoByCondition(reqSearch, pageable);
    }

    private int countGroupSystemConfigDtoByCondition(JcaGroupSystemConfigSearchDto reqSearch) {
        return jcaGroupSystemConfigService.countGroupSystemConfigDtoByCondition(reqSearch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupSystemConfigInfoRes create(GroupSystemConfigAddReq groupSystemConfigAddReq) throws Exception {
        JcaGroupSystemConfigDto newJcaGroupSystemConfig = new JcaGroupSystemConfigDto();
        try {
            JcaGroupSystemConfigDto jcaGroupSystemConfig = objectMapper.convertValue(groupSystemConfigAddReq, JcaGroupSystemConfigDto.class);    
            jcaGroupSystemConfig.setCreatedDate(commonService.getSystemDate());
            jcaGroupSystemConfig.setCreatedId(1L);
            jcaGroupSystemConfig.setUpdatedDate(commonService.getSystemDate());
            jcaGroupSystemConfig.setUpdatedId(1L);
            newJcaGroupSystemConfig = this.save(jcaGroupSystemConfig);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022002_APPAPI_GROUP_SYSTEM_SETTING_ADD_ERROR);
            }
        
        return mapperJcaGroupConfigDtoToGroupSystemConfigInfoRes(newJcaGroupSystemConfig);
        
    }

    private GroupSystemConfigInfoRes mapperJcaGroupConfigDtoToGroupSystemConfigInfoRes(JcaGroupSystemConfigDto jcaGroupSystemConfigDto) {
        return objectMapper.convertValue(jcaGroupSystemConfigDto, GroupSystemConfigInfoRes.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GroupSystemConfigUpdateReq groupSystemConfigUpdateDtoReq) throws Exception {
        if (groupSystemConfigUpdateDtoReq.getId() != null) {
            JcaGroupSystemConfig jcaGroupSystemConfig = jcaGroupSystemConfigService
                    .getGroupSystemConfigById(groupSystemConfigUpdateDtoReq.getId());
            if (null != jcaGroupSystemConfig) {
                try {
                        jcaGroupSystemConfig.setGroupCode(groupSystemConfigUpdateDtoReq.getGroupCode());
                    
                        jcaGroupSystemConfig.setGroupName(groupSystemConfigUpdateDtoReq.getGroupName());
                        jcaGroupSystemConfig.setCompanyId(groupSystemConfigUpdateDtoReq.getCompanyId());
                    
                    jcaGroupSystemConfig.setUpdatedId(1L);
                    jcaGroupSystemConfig.setUpdatedDate(commonService.getSystemDate());
                    jcaGroupSystemConfigService.update(jcaGroupSystemConfig);

                } catch (Exception e) {
                    handlerCastException.castException(e,AppApiExceptionCodeConstant.E4022005_APPAPI_GROUP_SYSTEM_SETTING_UPDATE_INFO_ERROR);
                    }
                
            } else {
                throw new DetailException(AppApiExceptionCodeConstant.E4022004_APPAPI_GROUP_SYSTEM_SETTING_NOT_FOUND);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {
        JcaGroupSystemConfig jcaGroupSystemConfig = jcaGroupSystemConfigService.getGroupSystemConfigById(id);
        if (null != jcaGroupSystemConfig) {
            try {
                // hardcode
                jcaGroupSystemConfig.setDeletedId(1L);
                jcaGroupSystemConfig.setDeletedDate(commonService.getSystemDate());
                jcaGroupSystemConfigService.saveJcaGroupSystemConfig(jcaGroupSystemConfig);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022003_APPAPI_GROUP_SYSTEM_SETTING_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022004_APPAPI_GROUP_SYSTEM_SETTING_NOT_FOUND);
        }
    }

    @Override
    public GroupSystemConfigInfoRes getGroupSystemConfigInfoResById(Long groupId) throws Exception {
        JcaGroupSystemConfigDto jcaGroupSystemConfigDto = this.detail(groupId);
        return objectMapper.convertValue(jcaGroupSystemConfigDto, GroupSystemConfigInfoRes.class);
    }

    @Override
    public JcaGroupSystemConfigDto detail(Long id) throws DetailException {
        JcaGroupSystemConfigDto jcaGroupSystemConfigDto = jcaGroupSystemConfigService.getJcaGroupSystemConfigDtoById(id);
        if (null == jcaGroupSystemConfigDto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022004_APPAPI_GROUP_SYSTEM_SETTING_NOT_FOUND);
        }
        return jcaGroupSystemConfigDto;
    }


    @Override
    public JcaGroupSystemConfigDto save(JcaGroupSystemConfigDto objectDto) throws DetailException {

        return objectMapper.convertValue(jcaGroupSystemConfigService.saveJcaGroupSystemConfigDto(objectDto), JcaGroupSystemConfigDto.class);

    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<JcaGroupSystemConfigDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable)
            throws DetailException {
        ObjectDataRes<JcaGroupSystemConfigDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaGroupSystemConfig.class,
                    JcaGroupSystemConfigService.TABLE_ALIAS_JCA_GROUP_SYSTEM_SETTING);
            /** init param search repository */
            JcaGroupSystemConfigSearchDto reqSearch = this.buildJcaGroupSystemConfigSearchDto(commonSearch);

            int totalData = this.countGroupSystemConfigDtoByCondition(reqSearch);
            List<JcaGroupSystemConfigDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getGroupSystemConfigDtoByCondition(reqSearch, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022001_APPAPI_GROUP_SYSTEM_SETTING_LIST_ERROR);
        }
        return resObj;
    }

    /**
     * <p>
     * Builds the jca GroupSystemConfig search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaGroupSystemConfigSearchDto}
     * @author ngannh
     */
    private JcaGroupSystemConfigSearchDto buildJcaGroupSystemConfigSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaGroupSystemConfigSearchDto reqSearch = new JcaGroupSystemConfigSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;

        reqSearch.setCompanyId(companyId);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaGroupSystemConfigSearchEnum.valueOf(enumValue)) {
                case CODE:
                    reqSearch.setCode(keySearch);
                    break;
                case NAME:
                    reqSearch.setName(keySearch);
                    break;
                    
                default:
                    reqSearch.setCode(keySearch);
                    reqSearch.setName(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setCode(keySearch);
            reqSearch.setName(keySearch);
        }
        
        return reqSearch;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return  masterCommonService.getEnumsParamSearchResForEnumClass(JcaGroupSystemConfigSearchEnum.values());
    }
}