package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.SlaConfigAddReq;
import vn.com.unit.ep2p.dto.req.SlaConfigUpdateReq;
import vn.com.unit.ep2p.service.SlaConfigEnterpriseService;
import vn.com.unit.sla.dto.SlaConfigDto;
import vn.com.unit.sla.entity.SlaConfig;
import vn.com.unit.sla.service.SlaConfigService;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmSlaConfigSearchDto;
import vn.com.unit.workflow.entity.JpmSlaConfig;
import vn.com.unit.workflow.service.JpmSlaConfigService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaConfigEnterpriseServiceImpl extends AbstractCommonService implements SlaConfigEnterpriseService{
    
    @Autowired
    private SlaConfigService slaConfigService;
    
    @Autowired
    private JpmSlaConfigService jpmSlaConfigService;
    
    private static final String LANG_DEFAULT = "EN";
    
    @Override
    public JpmSlaConfigDto getJpmSlaConfigDtoByConfigId(Long id) {
        return jpmSlaConfigService.getJpmSlaConfigDtoById(id, LANG_DEFAULT);
        //return slaConfigService.getSlaConfigById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmSlaConfigDto createJpmSlaConfigDto(SlaConfigAddReq slaConfigReq) throws DetailException {
        JpmSlaConfigDto JpmSlaConfigDto = objectMapper.convertValue(slaConfigReq, JpmSlaConfigDto.class);
        return this.save(JpmSlaConfigDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmSlaConfigDto updateJpmSlaConfigDto(SlaConfigUpdateReq slaConfigReq) throws DetailException {
        JpmSlaConfigDto JpmSlaConfigDto = objectMapper.convertValue(slaConfigReq, JpmSlaConfigDto.class);
        return this.save(JpmSlaConfigDto);
    }

    @Override
    public ObjectDataRes<JpmSlaConfigDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<JpmSlaConfigDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, SlaConfig.class, SlaConfigEnterpriseService.TABLE_SLA_CONFIG);
            /** init param search repository */
            JpmSlaConfigSearchDto searchDto = this.buildJpmSlaConfigSearchDto(commonSearch);
            
            int totalData = jpmSlaConfigService.countBySearchCondition(searchDto);
            List<JpmSlaConfigDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jpmSlaConfigService.getJpmSlaConfigDtoListByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024001_APPAPI_SLA_CONFIG_LIST_ERROR);
        }
        return resObj;
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmSlaConfigDto save(JpmSlaConfigDto objectDto) throws DetailException {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long jpmSlaConfigId = objectDto.getJpmSlaConfigId();
        SlaConfigDto slaConfigDto = objectMapper.convertValue(objectDto, SlaConfigDto.class);
        //JpmSlaConfig JpmSlaConfig = objectMapper.convertValue(objectDto, JpmSlaConfig.class);
        JpmSlaConfig jpmSlaConfig = new JpmSlaConfig();
        
        if (null != jpmSlaConfigId) {
            jpmSlaConfig = jpmSlaConfigService.findOne(jpmSlaConfigId);
            if (null != jpmSlaConfig) {
                jpmSlaConfig.setId(jpmSlaConfigId);
                jpmSlaConfig.setBusinessId(objectDto.getBusinessId());
                jpmSlaConfig.setProcessDeployId(objectDto.getProcessDeployId());
                jpmSlaConfig.setStepDeployId(objectDto.getStepDeployId());
            } else {
                jpmSlaConfigId = null;
                jpmSlaConfig = objectMapper.convertValue(objectDto, JpmSlaConfig.class);
            }
        }
        Long slaConfigId = slaConfigService.saveSlaConfigDto(slaConfigDto).getId();
        //jca sla
        jpmSlaConfig.setSlaConfigId(slaConfigId);
        if(null == jpmSlaConfigId) {
            jpmSlaConfig.setCreatedId(userId);
            jpmSlaConfig.setCreatedDate(sysDate);
            jpmSlaConfig.setUpdatedId(userId);
            jpmSlaConfig.setUpdatedDate(sysDate);
            jpmSlaConfigId = jpmSlaConfigService.create(jpmSlaConfig).getId();
        } else {
            jpmSlaConfig.setUpdatedId(userId);
            jpmSlaConfig.setUpdatedDate(sysDate);
            jpmSlaConfigService.update(jpmSlaConfig);
        }
        
        objectDto.setId(slaConfigId);
        objectDto.setJpmSlaConfigId(jpmSlaConfigId);
        return objectDto;
        //return slaConfigService.createSlaConfig(objectDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        boolean delete = slaConfigService.deleteById(id);
        if (delete) {
            Long jpmSlaConfigId = jpmSlaConfigService.getJpmSlaConfigDtoById(id, LANG_DEFAULT).getId();
            JpmSlaConfig JpmSlaConfig = jpmSlaConfigService.findOne(jpmSlaConfigId);
            JpmSlaConfig.setDeletedId(userId);
            JpmSlaConfig.setDeletedDate(sysDate);
            jpmSlaConfigService.update(JpmSlaConfig);
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4024002_APPAPI_SLA_CONFIG_DELETE_ERROR);
        }
    }

    @Override
    public JpmSlaConfigDto detail(Long id) throws DetailException {
        return this.getJpmSlaConfigDtoByConfigId(id);
    }

    private JpmSlaConfigSearchDto buildJpmSlaConfigSearchDto(MultiValueMap<String, String> commonSearch) {
        JpmSlaConfigSearchDto jpmSlaConfigSearchDto = new JpmSlaConfigSearchDto();
//        String slaType = null != commonSearch.getFirst("slaType") ? commonSearch.getFirst("slaType") : AppApiConstant.EMPTY;
        Long businessId = null != commonSearch.getFirst("businessId") ? Long.valueOf(commonSearch.getFirst("businessId")) : null;
        Long processDeployId = null != commonSearch.getFirst("processDeployId") ? Long.valueOf(commonSearch.getFirst("processDeployId")) : null;
        Long stepDeployId = null != commonSearch.getFirst("stepDeployId") ? Long.valueOf(commonSearch.getFirst("stepDeployId")) : null;
        jpmSlaConfigSearchDto.setBusinessId(businessId);
        jpmSlaConfigSearchDto.setProcessDeployId(processDeployId);
        jpmSlaConfigSearchDto.setStepDeployId(stepDeployId);
        
        return jpmSlaConfigSearchDto;
    }
}
