/*******************************************************************************
 * Class        ：JpmSlaConfigServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.sla.service.SlaService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmSlaConfigSearchDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.entity.JpmSlaConfig;
import vn.com.unit.workflow.entity.JpmSlaInfo;
import vn.com.unit.workflow.repository.JpmSlaConfigRepository;
import vn.com.unit.workflow.service.JpmSlaConfigService;
import vn.com.unit.workflow.service.JpmSlaInfoService;
import vn.com.unit.workflow.service.JpmStepDeployService;

/**
 * <p>
 * JpmSlaConfigServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmSlaConfigServiceImpl implements JpmSlaConfigService {

    /** The jpm sla config repository. */
    @Autowired
    private JpmSlaConfigRepository jpmSlaConfigRepository;

    @Autowired
    private JpmSlaInfoService jpmSlaInfoService;

    @Autowired
    private JpmStepDeployService jpmStepDeployService;

    @Autowired
    private SlaService slaCommonService;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public DbRepository<JpmSlaConfig, Long> initRepo() {
        return jpmSlaConfigRepository;
    }

    @Override
    public JpmSlaConfigDto getJpmSlaConfigDtoById(Long id, String lang) {
        return jpmSlaConfigRepository.getJpmSlaConfigDtoById(id, lang);
    }

    @Override
    @Transactional
    public JpmSlaConfig saveJpmSlaConfig(JpmSlaConfig jpmSlaConfig) {
        Long id = jpmSlaConfig.getId();
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = commonService.getSystemDate();
        if (null != id) {
            jpmSlaConfig.setUpdatedId(userId);
            jpmSlaConfig.setUpdatedDate(sysDate);
            JpmSlaConfig jpmSlaConfigOld = jpmSlaConfigRepository.findOne(id);
            CommonObjectUtil.copyPropertiesNonNull(jpmSlaConfig, jpmSlaConfigOld);
            jpmSlaConfigRepository.update(jpmSlaConfigOld);
        } else {
            jpmSlaConfig.setCreatedId(userId);
            jpmSlaConfig.setCreatedDate(sysDate);
            jpmSlaConfig.setUpdatedId(userId);
            jpmSlaConfig.setUpdatedDate(sysDate);
            jpmSlaConfigRepository.create(jpmSlaConfig);
        }
        return jpmSlaConfig;
    }

    @Override
    @Transactional
    public JpmSlaConfigDto saveJpmSlaConfigDto(JpmSlaConfigDto jpmSlaConfigDto) {
        JpmSlaConfig jpmSlaConfig = objectMapper.convertValue(jpmSlaConfigDto, JpmSlaConfig.class);
        jpmSlaConfig.setId(jpmSlaConfigDto.getJpmSlaConfigId());
        jpmSlaConfig.setSlaConfigId(jpmSlaConfigDto.getId());
        jpmSlaConfig.setSlaCalendarTypeId(jpmSlaConfigDto.getCalendarTypeId());
        jpmSlaConfigDto.setJpmSlaConfigId(this.saveJpmSlaConfig(jpmSlaConfig).getId());
        return jpmSlaConfigDto;
    }

    @Override
    public List<JpmSlaConfigDto> getJpmSlaConfigDtoListBySearchDto(JpmSlaConfigSearchDto searchDto) {
        return jpmSlaConfigRepository.getJpmSlaConfigDtoListBySearchDto(searchDto);
    }

    @Override
    public int countBySearchCondition(JpmSlaConfigSearchDto searchDto) {
        return jpmSlaConfigRepository.countBySearchCondition(searchDto);
    }

    @Override
    public List<JpmSlaConfigDto> getJpmSlaConfigDtoListByCondition(JpmSlaConfigSearchDto searchDto, Pageable pageable) {
        return jpmSlaConfigRepository.getJpmSlaConfigDtoListByCondition(searchDto, pageable).getContent();
    }

    @Override
    public JpmSlaConfigDto getJpmSlaConfigDtoByProcessDeployIdAndStepDeployId(Long processDepoyId, Long stepDeployId) {
        return jpmSlaConfigRepository.getJpmSlaConfigDtoByProcessDeployIdAndStepDeployId(processDepoyId, stepDeployId);
    }

    @Override
    public void cloneJpmSlaConfig(Long oldSlaInfoId, Long slaInfoId) throws DetailException {
        JpmSlaInfo jpmSlaInfo = jpmSlaInfoService.findOne(slaInfoId);
        List<JpmSlaConfigDto> jpmSlaConfigDtoList = jpmSlaConfigRepository.getJpmSlaConfigDtoListByJpmSlaInfoId(oldSlaInfoId);
        if (null != jpmSlaInfo && CommonCollectionUtil.isNotEmpty(jpmSlaConfigDtoList)) {
            Long businessId = jpmSlaInfo.getBusinessId();
            Long processDeployId = jpmSlaInfo.getProcessDeployId();
            // clone slaConfig
            for (JpmSlaConfigDto jpmSlaConfigDto : jpmSlaConfigDtoList) {
                Long slaConfigId = jpmSlaConfigDto.getId();
                JpmStepDeployDto jpmStepDeployDto = jpmStepDeployService.getJpmStepDeployDtoByProcessDeployIdAndStepCode(processDeployId, jpmSlaConfigDto.getStepDeployCode());
                if (null != jpmStepDeployDto) {
                    Long stepDeployId = jpmStepDeployDto.getStepDeployId();
                    Long slaConfigIdNew = slaCommonService.cloneSlaConfig(slaConfigId);
                    jpmSlaConfigDto.setJpmSlaInfoId(slaInfoId);
                    jpmSlaConfigDto.setId(slaConfigIdNew);
                    jpmSlaConfigDto.setJpmSlaConfigId(null);
                    jpmSlaConfigDto.setBusinessId(businessId);
                    jpmSlaConfigDto.setProcessDeployId(processDeployId);
                    jpmSlaConfigDto.setStepDeployId(stepDeployId);
                    this.saveJpmSlaConfigDto(jpmSlaConfigDto);
                }
            }
        }
    }

}
