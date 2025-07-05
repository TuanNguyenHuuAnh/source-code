/*******************************************************************************
* Class        JpmStepDeployServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.dto.JpmStepLangDeployDto;
import vn.com.unit.workflow.entity.JpmStepDeploy;
import vn.com.unit.workflow.repository.JpmStepDeployRepository;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;
import vn.com.unit.workflow.service.JpmStepDeployService;
import vn.com.unit.workflow.service.JpmStepLangDeployService;

/**
 * JpmStepDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStepDeployServiceImpl implements JpmStepDeployService {

    @Autowired
    private JpmStepDeployRepository jpmStepDeployRepository;

    @Autowired
    private JpmStepLangDeployService jpmStepLangDeployService;

    @Autowired
    private JpmButtonForStepDeployService jpmButtonForStepDeployService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmStepDeployDto getJpmStepDeployDtoById(Long id) {
        JpmStepDeployDto jpmStepDeployDto = new JpmStepDeployDto();
        if (null != id) {
            JpmStepDeploy jpmStepDeploy = jpmStepDeployRepository.findOne(id);
            if (Objects.nonNull(jpmStepDeploy) && 0L == jpmStepDeploy.getDeletedId()) {
                jpmStepDeployDto = objectMapper.convertValue(jpmStepDeploy, JpmStepDeployDto.class);
                jpmStepDeployDto.setStepDeployId(id);
                
                List<JpmStepLangDeployDto> stepLangDeployDtos = jpmStepLangDeployService.getStepLangDeployDtosByStepDeployId(id);
                List<JpmButtonForStepDeployDto> buttonForStepDeployDtos = jpmButtonForStepDeployService.getButtonForStepDeployDtosByStepDeployId(id);
                jpmStepDeployDto.setButtonForStepDtos(buttonForStepDeployDtos);
                jpmStepDeployDto.setStepLangs(stepLangDeployDtos);
            }
        }
        return jpmStepDeployDto;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmStepDeploy jpmStepDeploy = jpmStepDeployRepository.findOne(id);
            if (Objects.nonNull(jpmStepDeploy) && Objects.isNull(jpmStepDeploy.getDeletedId())) {
                jpmStepDeploy.setDeletedId(userId);
                jpmStepDeploy.setDeletedDate(sysDate);
                jpmStepDeployRepository.update(jpmStepDeploy);
                res = true;
            }
        }
        return res;
    }

    @Override
    public JpmStepDeploy saveJpmStepDeploy(JpmStepDeploy jpmStepDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmStepDeploy.getId();
        if (null != id) {
            JpmStepDeploy oldJpmStepDeploy = jpmStepDeployRepository.findOne(id);
            if (null != oldJpmStepDeploy) {
                jpmStepDeploy.setCreatedId(oldJpmStepDeploy.getCreatedId());
                jpmStepDeploy.setCreatedDate(oldJpmStepDeploy.getCreatedDate());
                jpmStepDeploy.setUpdatedId(userId);
                jpmStepDeploy.setUpdatedDate(sysDate);
                jpmStepDeployRepository.update(jpmStepDeploy);
            }
        } else {
            jpmStepDeploy.setCreatedId(userId);
            jpmStepDeploy.setCreatedDate(sysDate);
            jpmStepDeploy.setUpdatedId(userId);
            jpmStepDeploy.setUpdatedDate(sysDate);
            jpmStepDeployRepository.create(jpmStepDeploy);
        }
        return jpmStepDeploy;
    }

    @Override
    public JpmStepDeploy saveJpmStepDeployDto(JpmStepDeployDto jpmStepDeployDto) {
        JpmStepDeploy jpmStepDeploy = objectMapper.convertValue(jpmStepDeployDto, JpmStepDeploy.class);
        jpmStepDeploy = this.saveJpmStepDeploy(jpmStepDeploy);

        Long stepDeployId = jpmStepDeploy.getId();
        List<JpmStepLangDeployDto> stepLangs = jpmStepDeployDto.getStepLangs();
        jpmStepLangDeployService.saveJpmStepLangDeployDtos(stepLangs, stepDeployId);

        List<JpmButtonForStepDeployDto> buttonForStepDeployDtos = jpmStepDeployDto.getButtonForStepDtos();
        if (CommonCollectionUtil.isNotEmpty(buttonForStepDeployDtos)){
            Long processDeployId = jpmStepDeployDto.getProcessDeployId();
            String stepCode = jpmStepDeployDto.getStepCode();
            buttonForStepDeployDtos.stream().forEach(bfs -> {
                bfs.setProcessDeployId(processDeployId);
                bfs.setStepCode(stepCode);
            });
            jpmButtonForStepDeployService.saveJpmButtonForStepDeployDtos(buttonForStepDeployDtos, stepDeployId);
        }

        return jpmStepDeploy;
    }

    @Override
    public JpmStepDeployDto getJpmStepDeployDtoByProcessDeployIdAndStepCode(Long processDeployId, String stepCode) {
        return jpmStepDeployRepository.getJpmStepDeployDtoByProcessDeployIdAndStepCode(processDeployId, stepCode);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.service.JpmStepDeployService#getJpmStepDeployFirstByProcessDeployId(java.lang.Long)
     */
    @Override
    public JpmStepDeploy getJpmStepDeployFirstByProcessDeployId(Long processDeployId) {
        return jpmStepDeployRepository.getJpmStepDeployFirstByProcessDeployId(processDeployId);
    }

    @Override
    public Map<Long, Long> saveJpmStepDeployDtos(List<JpmStepDeployDto> stepDeployDtos, Long processDeployId) {
        Map<Long, Long> stepIdMap = new HashMap<>();
        if (CommonCollectionUtil.isNotEmpty(stepDeployDtos) && Objects.nonNull(processDeployId)) {
            for (JpmStepDeployDto stepDeployDto : stepDeployDtos) {
                stepDeployDto.setProcessDeployId(processDeployId);
                JpmStepDeploy stepDeploy = this.saveJpmStepDeployDto(stepDeployDto);

                Long stepId = stepDeploy.getStepId();
                Long stepDeployId = stepDeploy.getId();
                stepIdMap.put(stepId, stepDeployId);
            }
        }
        return stepIdMap;
    }

    @Override
    public List<JpmStepDeployDto> getStepDeployDtosByProcessDeployId(Long processDeployId) {
        List<JpmStepDeployDto> stepDeployDtos = jpmStepDeployRepository.getStepDeployDtosByProcessDeployId(processDeployId);
        if (CommonCollectionUtil.isNotEmpty(stepDeployDtos)) {
            List<JpmStepLangDeployDto> stepLangDeployDtos = jpmStepLangDeployService.getStepLangDeployDtosByProcessDeployId(processDeployId);
            if (CommonCollectionUtil.isNotEmpty(stepLangDeployDtos)) {
                Map<Long, List<JpmStepLangDeployDto>> stepLangDtosMap = stepLangDeployDtos.stream()
                        .collect(Collectors.groupingBy(JpmStepLangDeployDto::getStepDeployId));
                stepDeployDtos.forEach(stepDto -> stepDto.setStepLangs(stepLangDtosMap.get(stepDto.getStepDeployId())));
            }

            List<JpmButtonForStepDeployDto> buttonForStepDeployDtos = jpmButtonForStepDeployService.getButtonForStepDeployDtosByProcessDeployId(processDeployId);
            if (CommonCollectionUtil.isNotEmpty(buttonForStepDeployDtos)) {
                Map<Long, List<JpmButtonForStepDeployDto>> buttonForStepDtosMap = buttonForStepDeployDtos.stream()
                        .collect(Collectors.groupingBy(JpmButtonForStepDeployDto::getStepDeployId));
                stepDeployDtos.forEach(stepDto -> stepDto.setButtonForStepDtos(buttonForStepDtosMap.get(stepDto.getStepDeployId())));
            }
        }

        return stepDeployDtos;
    }

}