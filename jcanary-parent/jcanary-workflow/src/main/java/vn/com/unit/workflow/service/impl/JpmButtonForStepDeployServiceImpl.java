/*******************************************************************************
* Class        JpmButtonForStepDeployServiceImpl
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

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.workflow.dto.JpmPermissionDeployDto;
import vn.com.unit.workflow.entity.JpmButtonForStepDeploy;
import vn.com.unit.workflow.repository.JpmButtonForStepDeployRepository;
import vn.com.unit.workflow.service.JpmButtonDeployService;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;
import vn.com.unit.workflow.service.JpmPermissionDeployService;

/**
 * JpmButtonForStepDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonForStepDeployServiceImpl implements JpmButtonForStepDeployService {

    @Autowired
    private JpmButtonForStepDeployRepository jpmButtonForStepDeployRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpmButtonDeployService jpmButtonDeployService;

    @Autowired
    private JpmPermissionDeployService jpmPermissionDeployService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButtonForStepDeploy saveJpmButtonForStepDeploy(JpmButtonForStepDeploy jpmButtonForStepDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmButtonForStepDeploy.setCreatedId(userId);
        jpmButtonForStepDeploy.setCreatedDate(sysDate);
        jpmButtonForStepDeploy.setUpdatedId(userId);
        jpmButtonForStepDeploy.setUpdatedDate(sysDate);
        jpmButtonForStepDeployRepository.create(jpmButtonForStepDeploy);
        return jpmButtonForStepDeploy;
    }

    @Override
    public JpmButtonForStepDeploy saveJpmButtonForStepDeployDto(JpmButtonForStepDeployDto jpmButtonForStepDeployDto) {
        JpmButtonForStepDeploy jpmButtonForStepDeploy = objectMapper.convertValue(jpmButtonForStepDeployDto, JpmButtonForStepDeploy.class);
        return this.saveJpmButtonForStepDeploy(jpmButtonForStepDeploy);
    }

    @Override
    public JpmButtonWrapper<JpmButtonForDocDto> getListButtonForDocDtoByProcessDeployIdAndStepCode(Long processDeployId, String stepCode, String lang) {
        JpmButtonWrapper<JpmButtonForDocDto> buttonWrapper = new JpmButtonWrapper<>();
        Long userId = UserProfileUtils.getAccountId();

        List<JpmButtonForDocDto> buttonForDocDtos = jpmButtonForStepDeployRepository
                .getListButtonForDocDtoByProcessDeployIdAndStepCode(processDeployId, userId, stepCode, lang);

        if (CommonCollectionUtil.isNotEmpty(buttonForDocDtos)) {
            boolean isSaveForm = buttonForDocDtos.stream().anyMatch(JpmButtonForDocDto::isSaveForm);
            boolean isSaveEform = buttonForDocDtos.stream().anyMatch(JpmButtonForDocDto::isSaveEform);
            boolean useClaimButton = buttonForDocDtos.stream().anyMatch(JpmButtonForDocDto::isUseClaimButton);
            buttonWrapper.setSaveForm(isSaveForm);
            buttonWrapper.setSaveEform(isSaveEform);
            buttonWrapper.setUseClaimButton(useClaimButton);
        }

        buttonWrapper.setData(buttonForDocDtos);
        return buttonWrapper;
    }

    @Override
    public void saveJpmButtonForStepDeployDtos(List<JpmButtonForStepDeployDto> buttonForStepDeployDtos, Long stepDeployId) {
        if (CommonCollectionUtil.isNotEmpty(buttonForStepDeployDtos) && Objects.nonNull(stepDeployId)) {
            Long processDeployId = buttonForStepDeployDtos.get(0).getProcessDeployId();
            List<JpmPermissionDeployDto> permissionDtos = jpmPermissionDeployService
                    .getPermissionDeployDtosByProcessDeployId(processDeployId);
            Map<Long, String> permissionIdConverter = new HashMap<>();
            if (CommonCollectionUtil.isNotEmpty(permissionDtos)) {
                permissionIdConverter = permissionDtos.stream()
                        .collect(Collectors.toMap(JpmPermissionDeployDto::getPermissionDeployId, JpmPermissionDeployDto::getPermissionCode));
            }
            for (JpmButtonForStepDeployDto buttonForStepDeployDto : buttonForStepDeployDtos) {
                buttonForStepDeployDto.setStepDeployId(stepDeployId);
                buttonForStepDeployDto
                        .setPermissionCode(permissionIdConverter.getOrDefault(buttonForStepDeployDto.getPermissionDeployId(), "NULL"));
                this.saveJpmButtonForStepDeployDto(buttonForStepDeployDto);
            }
        }
    }

    @Override
    public List<JpmButtonForStepDeployDto> getButtonForStepDeployDtosByProcessDeployId(Long processDeployId) {
        return jpmButtonForStepDeployRepository.getButtonForStepDeployDtosByProcessDeployId(processDeployId);
    }

    @Override
    public List<JpmButtonForStepDeployDto> getButtonForStepDeployDtosByStepDeployId(Long stepDeployId) {
        return jpmButtonForStepDeployRepository.getButtonForStepDeployDtosByStepDeployId(stepDeployId);
    }

    @Override
    public Long getPermissionDeployIdByStepDeployIdAndButtonDeployId(Long stepId, Long actionId) {
        return jpmButtonForStepDeployRepository.getPermissionDeployIdByStepDeployIdAndButtonDeployId(stepId, actionId);
    }

    @Override
    public ActionDto getActionDtoByCoreTaskIdAndButtonId(Long processDeployId, String coreTaskId, Long buttonId, String commonStatusCode) {
        ActionDto actionDto = jpmButtonForStepDeployRepository.getActionDtoByCoreTaskIdAndButtonId(processDeployId, coreTaskId, buttonId,
                commonStatusCode);

        if (actionDto == null) {
            // button no config
            actionDto = jpmButtonDeployService.getActionDtoByProcessDeployIdAndButtonDeployId(processDeployId, buttonId);
        }

        return actionDto;
    }

    @Override
    public List<JpmButtonForDocDto> getListButtonForStepDeployDtoByEfoOzDocIds(List<Long> efoOzDocIds, Long accountId, String lang) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        return jpmButtonForStepDeployRepository.getListButtonForStepDeployDtoByEfoOzDocIds(efoOzDocIds, accountId, lang, sysDate);
    }

    @Override
    public JpmButtonWrapper<JpmButtonForDocDto> getListButtonForDocDtoByDocId(List<JpmButtonForDocDto> buttonForDocDtos, Long docId) {
        /** button List */
        JpmButtonWrapper<JpmButtonForDocDto> jpmBtnWrapper = new JpmButtonWrapper<>();
        List<JpmButtonForDocDto> jpmButtonList = buttonForDocDtos.stream().filter(b -> docId.toString().equals(b.getDocId().toString()))
                .collect(Collectors.toList());
        if (CommonCollectionUtil.isNotEmpty(jpmButtonList)) {
            boolean isSaveForm = buttonForDocDtos.stream().anyMatch(JpmButtonForDocDto::isSaveForm);
            boolean isSaveEform = buttonForDocDtos.stream().anyMatch(JpmButtonForDocDto::isSaveEform);
            boolean useClaimButton = buttonForDocDtos.stream().anyMatch(JpmButtonForDocDto::isUseClaimButton);
            jpmBtnWrapper.setSaveForm(isSaveForm);
            jpmBtnWrapper.setSaveEform(isSaveEform);
            jpmBtnWrapper.setUseClaimButton(useClaimButton);
        }
        jpmBtnWrapper.setData(jpmButtonList);

        return jpmBtnWrapper;
    }

    @Override
    public Long getPermissionDeployIdByProcessDeployIdAndStepDeployId(Long processDeployId, Long stepDeployId) {
        return jpmButtonForStepDeployRepository.getPermissionDeployIdByProcessDeployIdAndStepDeployId(processDeployId, stepDeployId);
    }

}