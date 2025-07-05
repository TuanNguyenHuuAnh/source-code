/*******************************************************************************
 * Class        ：SystemConfigServiceImpl
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.dto.JcaSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.core.enumdef.param.JcaSystemConfigSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
import vn.com.unit.ep2p.dto.req.SystemConfigAddReq;
import vn.com.unit.ep2p.dto.req.SystemConfigUpdateReq;
import vn.com.unit.ep2p.dto.res.SystemConfigInfoRes;
import vn.com.unit.ep2p.service.SystemConfigService;

/**
 * SystemConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
public class SystemConfigServiceImpl extends AbstractCommonService implements SystemConfigService {

    @Autowired
    @Qualifier("appSystemConfigServiceImpl")
    JcaSystemConfigService jcaSystemConfigService;

    @Autowired
    MasterCommonService masterCommonService;

    @Override
    public JcaSystemConfigDto save(JcaSystemConfigDto objectDto) throws DetailException {
        return objectMapper.convertValue(jcaSystemConfigService.saveJcaSystemConfigDto(objectDto), JcaSystemConfigDto.class);
    }

    public JcaSystemConfigDto detail(String key) throws DetailException {
        JcaSystemConfigDto jcaSystemConfigDto = jcaSystemConfigService.getJcaSystemConfigDtoByKey(key);
       
        if (null == jcaSystemConfigDto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022004_APPAPI_GROUP_SYSTEM_SETTING_NOT_FOUND);
        }
        return jcaSystemConfigDto;

    }

    private List<JcaSystemConfigDto> getSystemConfigDtoByCondition(JcaSystemConfigSearchDto reqSearch,Pageable pageable) {
        return jcaSystemConfigService.getSystemConfigDtoByCondition(reqSearch,pageable);
    }

    private int countSystemConfigDtoByCondition(JcaSystemConfigSearchDto reqSearch) {
        return jcaSystemConfigService.countSystemConfigDtoByCondition(reqSearch);
    }

    @Override
    public SystemConfigInfoRes create(SystemConfigAddReq systemConfigAddReq) throws DetailException {
        JcaSystemConfigDto newJcaSystemConfig = new JcaSystemConfigDto();
        try {
            JcaSystemConfigDto jcaSystemConfig = objectMapper.convertValue(systemConfigAddReq, JcaSystemConfigDto.class);
            jcaSystemConfig.setCreatedDate(commonService.getSystemDate());
            jcaSystemConfig.setCreatedId(1L);
            jcaSystemConfig.setUpdatedDate(commonService.getSystemDate());
            jcaSystemConfig.setUpdatedId(1L);
            newJcaSystemConfig = this.save(jcaSystemConfig);
        } catch (Exception e) {
            handlerCastException.castException(e,AppApiExceptionCodeConstant.E4022002_APPAPI_GROUP_SYSTEM_SETTING_ADD_ERROR);
            }
        
        return mapperJcaSystemConfigDtoToSystemConfigInfoRes(newJcaSystemConfig);
    }

    private SystemConfigInfoRes mapperJcaSystemConfigDtoToSystemConfigInfoRes(JcaSystemConfigDto jcaSystemConfigDto) {
        return objectMapper.convertValue(jcaSystemConfigDto, SystemConfigInfoRes.class);
    }

    @Override
    public void delete(Long configId) throws DetailException {
        JcaSystemConfig jcaSystemConfig = jcaSystemConfigService.getSystemConfigById(configId);
        if (null != jcaSystemConfig) {
            try {
                // hardcode
                //TODO tantm SQL delete physical
//                jcaSystemConfig.setDeletedId(1L);
//                jcaSystemConfig.setDeletedDate(commonService.getSystemDate());
                jcaSystemConfigService.saveJcaSystemConfig(jcaSystemConfig);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022003_APPAPI_GROUP_SYSTEM_SETTING_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022004_APPAPI_GROUP_SYSTEM_SETTING_NOT_FOUND);
        }
    }

    @Override
    public void update(SystemConfigUpdateReq systemConfigUpdateReq) throws DetailException {
        if (null != systemConfigUpdateReq.getId()) {
            JcaSystemConfig jcaSystemConfig = jcaSystemConfigService.getSystemConfigById(systemConfigUpdateReq.getId());
            if (null != jcaSystemConfig) {
                try {
                        jcaSystemConfig.setSettingKey(systemConfigUpdateReq.getSettingKey());
                    
                        jcaSystemConfig.setSettingValue(systemConfigUpdateReq.getSettingValue());
                    
                        jcaSystemConfig.setCompanyId(systemConfigUpdateReq.getCompanyId());
                    
                        jcaSystemConfig.setGroupCode(systemConfigUpdateReq.getGroupCode());
                    
                    jcaSystemConfig.setUpdatedId(1L);
                    jcaSystemConfig.setUpdatedDate(commonService.getSystemDate());
                    jcaSystemConfigService.update(jcaSystemConfig);
                } catch (Exception e) {
                    
                    handlerCastException.castException(e,AppApiExceptionCodeConstant.E4022005_APPAPI_GROUP_SYSTEM_SETTING_UPDATE_INFO_ERROR);
                    }
                
            }
        }
    }

    @Override
    public SystemConfigInfoRes getSystemConfigInfoResById(Long id) throws DetailException {
        JcaSystemConfigDto jcaSystemConfigDto = this.detail(id);
        return objectMapper.convertValue(jcaSystemConfigDto, SystemConfigInfoRes.class);
    }


    @Override
    public ObjectDataRes<JcaSystemConfigDto> search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException {
        ObjectDataRes<JcaSystemConfigDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaSystemConfig.class,JcaSystemConfigService.TABLE_ALIAS_JCA_SYSTEM_SETTING);
            /** init param search repository */
            JcaSystemConfigSearchDto reqSearch = this.buildJcaSystemConfigSearchDto(commonSearch);
            
            int totalData = this.countSystemConfigDtoByCondition(reqSearch);
            List<JcaSystemConfigDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getSystemConfigDtoByCondition(reqSearch,pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022001_APPAPI_GROUP_SYSTEM_SETTING_LIST_ERROR);
        }
        return resObj;
    }
    private JcaSystemConfigSearchDto buildJcaSystemConfigSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaSystemConfigSearchDto reqSearch = new JcaSystemConfigSearchDto();
        
        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        String groupCode = null != commonSearch.getFirst("groupCode") ? String.valueOf(commonSearch.getFirst("groupCode")) : null;

        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        
        reqSearch.setCompanyId(companyId);
        reqSearch.setGroupCode(groupCode);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaSystemConfigSearchEnum.valueOf(enumValue)) {
                case KEY:
                    reqSearch.setKey(keySearch);
                    break;
                case VALUE:
                    reqSearch.setValue(keySearch);
                    break;
                default:
                    reqSearch.setKey(keySearch);
                    reqSearch.setValue(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setKey(keySearch);
            reqSearch.setValue(keySearch);
        }
        
        return  reqSearch;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaSystemConfigSearchEnum.values());
    }

    @Override
    public JcaSystemConfigDto detail(Long id) throws DetailException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JcaSystemConfigDto getConfigByKey(String settingKey, Long companyId) {

        JcaSystemConfig config = jcaSystemConfigService.getJcaSystemConfigByCompanyAndKey(companyId, settingKey);
        JcaSystemConfigDto res = new JcaSystemConfigDto();
        try {
            NullAwareBeanUtils.copyPropertiesWONull(config, res);
        } catch (Exception e) {
        }
        return res;
    }
}
