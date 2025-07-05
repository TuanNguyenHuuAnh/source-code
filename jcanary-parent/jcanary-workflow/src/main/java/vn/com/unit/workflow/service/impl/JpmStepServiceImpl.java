/*******************************************************************************
* Class        JpmStepServiceImpl
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
import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.dto.JpmStepLangDto;
import vn.com.unit.workflow.entity.JpmStep;
import vn.com.unit.workflow.repository.JpmStepRepository;
import vn.com.unit.workflow.service.JpmButtonForStepService;
import vn.com.unit.workflow.service.JpmHiStepService;
import vn.com.unit.workflow.service.JpmStepLangService;
import vn.com.unit.workflow.service.JpmStepService;

/**
 * JpmStepServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStepServiceImpl implements JpmStepService {

    @Autowired
    private JpmStepRepository jpmStepRepository;

    @Autowired
    private JpmStepLangService jpmStepLangService;

    @Autowired
    private JpmButtonForStepService jpmButtonForStepService;

    @Autowired
    private JpmHiStepService jpmHiStepService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmStepDto getJpmStepDtoById(Long id) {
        JpmStepDto jpmStepDto = new JpmStepDto();
        if (null != id) {
            JpmStep jpmStep = jpmStepRepository.findOne(id);
            if (null != jpmStep && jpmStep.getDeletedId() == 0L) {
                jpmStepDto = objectMapper.convertValue(jpmStep, JpmStepDto.class);
                jpmStepDto.setStepId(id);

                List<JpmStepLangDto> stepLangDtos = jpmStepLangService.getStepLangDtosByStepId(id);
                List<JpmButtonForStepDto> buttonForStepDtos = jpmButtonForStepService.getButtonForStepDtosByStepId(id);

                jpmStepDto.setButtonForStepDtos(buttonForStepDtos);
                jpmStepDto.setStepLangs(stepLangDtos);
            }
        }
        return jpmStepDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmStep jpmStep = jpmStepRepository.findOne(id);
            if (null != jpmStep) {
                jpmStep.setDeletedId(userId);
                jpmStep.setDeletedDate(sysDate);
                jpmStepRepository.update(jpmStep);
                res = true;
            }
            // delete step lang
            jpmStepLangService.deleteStepLangByStepId(id);
            // delete button for step
            jpmButtonForStepService.deleteButtonForStepByStepId(id);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmStep saveJpmStep(JpmStep jpmStep) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmStep.getId();
        if (null != id) {
            JpmStep oldJpmStep = jpmStepRepository.findOne(id);
            if (null != oldJpmStep) {
                jpmStep.setCreatedId(oldJpmStep.getCreatedId());
                jpmStep.setCreatedDate(oldJpmStep.getCreatedDate());
                jpmStep.setUpdatedId(userId);
                jpmStep.setUpdatedDate(sysDate);
                jpmStepRepository.update(jpmStep);
            }
        } else {
            jpmStep.setCreatedId(userId);
            jpmStep.setCreatedDate(sysDate);
            jpmStep.setUpdatedId(userId);
            jpmStep.setUpdatedDate(sysDate);
            jpmStepRepository.create(jpmStep);
        }

        // save history
        jpmHiStepService.saveJpmHiStep(jpmStep);

        return jpmStep;
    }

    @Override
    public JpmStep saveJpmStepDto(JpmStepDto jpmStepDto) {
        JpmStep jpmStep = objectMapper.convertValue(jpmStepDto, JpmStep.class);
        jpmStep.setId(jpmStepDto.getStepId());
        this.saveJpmStep(jpmStep);

        Long stepId = jpmStep.getId();
        jpmStepDto.setStepId(stepId);

        List<JpmStepLangDto> stepLangDtos = jpmStepDto.getStepLangs();
        if (CommonCollectionUtil.isNotEmpty(stepLangDtos)) {
            jpmStepLangService.saveStepLangDtosByStepId(jpmStepDto.getStepLangs(), stepId);
        }

        List<JpmButtonForStepDto> buttonForStepDtos = jpmStepDto.getButtonForStepDtos();
        if (CommonCollectionUtil.isNotEmpty(buttonForStepDtos)) {
            Long processId = jpmStepDto.getProcessId();
            String stepCode = jpmStepDto.getStepCode();
            buttonForStepDtos.stream().forEach(bfs -> {
                bfs.setProcessId(processId);
                bfs.setStepId(stepId);
                bfs.setStepCode(stepCode);
                bfs.setPermissionCode("Chua co, insert tam!");
            });
            jpmButtonForStepService.saveButtonForStepDtosByProcessId(buttonForStepDtos, stepId);
        }

        return jpmStep;
    }

    @Override
    public List<JpmStepDto> getStepDtosByProcessId(Long processId) {
        List<JpmStepDto> stepDtos = jpmStepRepository.getStepDtosByProcessId(processId);
        if (CommonCollectionUtil.isNotEmpty(stepDtos)) {
            List<JpmStepLangDto> stepLangDtos = jpmStepLangService.getStepLangDtosByProcessId(processId);
            if (CommonCollectionUtil.isNotEmpty(stepLangDtos)) {
                Map<Long, List<JpmStepLangDto>> stepLangDtosMap = stepLangDtos.stream()
                        .collect(Collectors.groupingBy(JpmStepLangDto::getStepId));
                stepDtos.forEach(stepDto -> stepDto.setStepLangs(stepLangDtosMap.get(stepDto.getStepId())));
            }

            List<JpmButtonForStepDto> buttonForStepDtos = jpmButtonForStepService.getButtonForStepDtosByProcessId(processId);
            if (CommonCollectionUtil.isNotEmpty(buttonForStepDtos)) {
                Map<Long, List<JpmButtonForStepDto>> buttonForStepDtosMap = buttonForStepDtos.stream()
                        .collect(Collectors.groupingBy(JpmButtonForStepDto::getStepId));
                stepDtos.forEach(stepDto -> stepDto.setButtonForStepDtos(buttonForStepDtosMap.get(stepDto.getStepId())));
            }
        }
        return stepDtos;
    }

    @Override
    public JpmStepDto getStepDtoByStepId(Long processStepId) {
        return jpmStepRepository.getStepDtoByStepId(processStepId);
    }

    @Override
    public Map<Long, Long> saveStepDtosByProcessId(List<JpmStepDto> stepDtos, Long processId) {
        Map<Long, Long> resMap = new HashMap<>();
        List<JpmStepDto> currentStepDtos = this.getStepDtosByProcessId(processId);
        Map<String, JpmStepDto> stepMap = CommonCollectionUtil.isEmpty(currentStepDtos) ? new HashMap<>()
                : currentStepDtos.stream().collect(Collectors.toMap(JpmStepDto::getStepCode, stepDto -> stepDto));

        for (JpmStepDto stepDto : stepDtos) {
            Long oldStepId = stepDto.getStepId();
            String stepCode = stepDto.getStepCode();
            JpmStepDto currentStepDto = stepMap.remove(stepCode);
            if (Objects.isNull(currentStepDto)) {
                stepDto.setStepId(null);
            } else {
                stepDto.setStepId(currentStepDto.getStepId());
            }
            stepDto.setProcessId(processId);
            this.saveJpmStepDto(stepDto);

            if (Objects.nonNull(oldStepId)) {
                resMap.put(oldStepId, stepDto.getStepId());
            }
        }
        // delete
        if (CommonMapUtil.isNotEmpty(stepMap)) {
            for (Map.Entry<String, JpmStepDto> entry : stepMap.entrySet()) {
                Long id = entry.getValue().getStepId();
                this.deleteById(id);
            }
        }

        return resMap;

    }

}