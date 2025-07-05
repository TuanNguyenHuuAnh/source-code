/*******************************************************************************
* Class        JpmProcessDeployServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.dto.JpmParamConfigDeployDto;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.dto.JpmParamDeployDto;
import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.dto.JpmPermissionDeployDto;
import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeployLangDto;
import vn.com.unit.workflow.dto.JpmProcessDeploySearchDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.entity.JpmProcessDeploy;
import vn.com.unit.workflow.repository.JpmProcessDeployRepository;
import vn.com.unit.workflow.service.JpmBusinessService;
import vn.com.unit.workflow.service.JpmButtonDeployService;
import vn.com.unit.workflow.service.JpmParamDeployService;
import vn.com.unit.workflow.service.JpmPermissionDeployService;
import vn.com.unit.workflow.service.JpmProcessDeployLangService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmStatusDeployService;
import vn.com.unit.workflow.service.JpmStepDeployService;

/**
 * JpmProcessDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmProcessDeployServiceImpl implements JpmProcessDeployService {

    @Autowired
    private JpmProcessDeployRepository jpmProcessDeployRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private JpmProcessDeployLangService jpmProcessDeployLangService;

    @Autowired
    private JpmParamDeployService jpmParamDeployService;

    @Autowired
    private JpmStepDeployService jpmStepDeployService;

    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;

    @Autowired
    private JpmPermissionDeployService jpmPermissionDeployService;

    @Autowired
    private JpmButtonDeployService jpmButtonDeployService;

    @Autowired
    private JpmBusinessService jpmBusinessService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmProcessDeployDto getJpmProcessDeployDtoById(Long id) {
        JpmProcessDeployDto jpmProcessDeployDto = null;
        if (Objects.nonNull(id)) {
            jpmProcessDeployDto = this.getJpmProcessDeployDtoByProcessDeployId(id);
            if (Objects.nonNull(jpmProcessDeployDto)) {
                List<JpmProcessDeployLangDto> processLangs = jpmProcessDeployLangService.getJpmProcessDeployLangDtosByProcessDeployId(id);
                jpmProcessDeployDto.setProcessLangs(processLangs);

                // get detail
                this.getElementForProcess(jpmProcessDeployDto);
            }
        }
        return jpmProcessDeployDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (Objects.nonNull(id)) {
            JpmProcessDeploy jpmProcessDeploy = jpmProcessDeployRepository.findOne(id);
            if (Objects.nonNull(jpmProcessDeploy) && Long.valueOf(0L).equals(jpmProcessDeploy.getDeletedId())) {
                jpmProcessDeploy.setDeletedId(userId);
                jpmProcessDeploy.setDeletedDate(sysDate);
                jpmProcessDeployRepository.update(jpmProcessDeploy);
                res = true;
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcessDeploy saveJpmProcessDeploy(JpmProcessDeploy jpmProcessDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmProcessDeploy.getId();
        if (Objects.nonNull(id)) {
            JpmProcessDeploy oldJpmProcessDeploy = jpmProcessDeployRepository.findOne(id);
            if (Objects.nonNull(oldJpmProcessDeploy) && 0L == oldJpmProcessDeploy.getDeletedId()) {
                jpmProcessDeploy.setCreatedId(oldJpmProcessDeploy.getCreatedId());
                jpmProcessDeploy.setCreatedDate(oldJpmProcessDeploy.getCreatedDate());
                jpmProcessDeploy.setUpdatedId(userId);
                jpmProcessDeploy.setUpdatedDate(sysDate);
                jpmProcessDeployRepository.update(jpmProcessDeploy);
            }
        } else {
            jpmProcessDeploy.setCreatedId(userId);
            jpmProcessDeploy.setCreatedDate(sysDate);
            jpmProcessDeploy.setUpdatedId(userId);
            jpmProcessDeploy.setUpdatedDate(sysDate);
            jpmProcessDeployRepository.create(jpmProcessDeploy);
        }
        return jpmProcessDeploy;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcessDeploy saveJpmProcessDeployDto(JpmProcessDeployDto jpmProcessDeployDto) {
        JpmProcessDeploy jpmProcessDeploy = objectMapper.convertValue(jpmProcessDeployDto, JpmProcessDeploy.class);
        jpmProcessDeploy.setId(jpmProcessDeployDto.getProcessDeployId());
        jpmProcessDeploy = this.saveJpmProcessDeploy(jpmProcessDeploy);

        Long processDeployId = jpmProcessDeploy.getId();
        List<JpmProcessDeployLangDto> processLangs = jpmProcessDeployDto.getProcessLangs();
        jpmProcessDeployLangService.saveJpmProcessDeployLangDtos(processLangs, processDeployId);

        List<JpmStatusDeployDto> statuses = jpmProcessDeployDto.getStatuses();
        Map<Long, Long> statusIdConverter = jpmStatusDeployService.saveJpmStatusDeployDtos(statuses, processDeployId);

        List<JpmPermissionDeployDto> permissions = jpmProcessDeployDto.getPermissions();
        Map<Long, Long> permissionIdConverter = jpmPermissionDeployService.saveJpmPermissionDeployDtos(permissions, processDeployId);

        List<JpmButtonDeployDto> buttons = jpmProcessDeployDto.getButtons();
        Map<Long, Long> buttonIdConverter = jpmButtonDeployService.saveJpmButtonDeployDtos(buttons, processDeployId);

        List<JpmStepDeployDto> steps = jpmProcessDeployDto.getSteps();
        if (CommonCollectionUtil.isNotEmpty(steps)) {
            steps.forEach(step -> {
                step.setStatusDeployId(statusIdConverter.get(step.getStatusId()));

                if (CommonCollectionUtil.isNotEmpty(step.getButtonForStepDtos())) {
                    step.getButtonForStepDtos().forEach(buttonForStep -> {
                        buttonForStep.setButtonDeployId(buttonIdConverter.get(buttonForStep.getButtonId()));
                        buttonForStep.setPermissionDeployId(permissionIdConverter.get(buttonForStep.getPermissionId()));
                    });
                }
            });
        }
        Map<Long, Long> stepIdConverter = jpmStepDeployService.saveJpmStepDeployDtos(steps, processDeployId);

        List<JpmParamDeployDto> params = jpmProcessDeployDto.getParams();
        if (CommonCollectionUtil.isNotEmpty(params)) {
            params.forEach(param -> {
                if (CommonCollectionUtil.isNotEmpty(param.getParamConfigDtos())) {
                    param.getParamConfigDtos()
                            .forEach(paramConfig -> paramConfig.setStepDeployId(stepIdConverter.get(paramConfig.getStepId())));
                }
            });
        }
        jpmParamDeployService.saveJpmParamDeployDtos(params, processDeployId);

        return jpmProcessDeploy;
    }

    @Override
    public JpmProcessDeployDto getJpmProcessDeployLasted(Long companyId, Long businessId) {
        Date sysDate = commonService.getSystemDate();
        return jpmProcessDeployRepository.getJpmProcessDeployLasted(companyId, businessId, sysDate);
    }

    @Override
    public int countBySearchCondition(JpmProcessDeploySearchDto processDeploySearchDto) {
        return jpmProcessDeployRepository.countBySearchCondition(processDeploySearchDto);
    }

    @Override
    public List<JpmProcessDeployDto> getProcessDeployDtosByCondition(JpmProcessDeploySearchDto searchDto, Pageable pageable) {
        return jpmProcessDeployRepository.getProcessDeployDtosByCondition(searchDto, pageable).getContent();
    }

    @Override
    public JpmProcessImportExportDto exportProcess(Long processDeployId) {
        JpmProcessImportExportDto resObj = null;
        JpmProcessDeployDto processDeployDto = this.getJpmProcessDeployDtoById(processDeployId);
        if (Objects.nonNull(processDeployDto)) {
            resObj = objectMapper.convertValue(processDeployDto, JpmProcessImportExportDto.class);
            Long businessId = processDeployDto.getBusinessId();
            if (Objects.nonNull(businessId)) {
                JpmBusinessDto businessDto = jpmBusinessService.getJpmBusinessDtoById(businessId);
                resObj.setBusinessDto(businessDto);
            }

            // remap deployId to Id
            List<JpmPermissionDeployDto> permissionDeployDtos = processDeployDto.getPermissions();
            if (CommonCollectionUtil.isNotEmpty(permissionDeployDtos)) {
                List<JpmPermissionDto> permissionDtos = permissionDeployDtos.stream().map(permissionDeployDto -> {
                    JpmPermissionDto permissionDto = objectMapper.convertValue(permissionDeployDto, JpmPermissionDto.class);
                    permissionDto.setPermissionId(permissionDeployDto.getPermissionDeployId());
                    return permissionDto;
                }).collect(Collectors.toList());
                resObj.setPermissions(permissionDtos);
            }

            List<JpmButtonDeployDto> buttonDeployDtos = processDeployDto.getButtons();
            if (CommonCollectionUtil.isNotEmpty(buttonDeployDtos)) {
                List<JpmButtonDto> buttonDtos = buttonDeployDtos.stream().map(buttonDeployDto -> {
                    JpmButtonDto buttonDto = objectMapper.convertValue(buttonDeployDto, JpmButtonDto.class);
                    buttonDto.setButtonId(buttonDeployDto.getButtonDeployId());
                    return buttonDto;
                }).collect(Collectors.toList());
                resObj.setButtons(buttonDtos);
            }

            List<JpmStepDeployDto> stepDeployDtos = processDeployDto.getSteps();
            if (CommonCollectionUtil.isNotEmpty(stepDeployDtos)) {
                List<JpmStepDto> stepDtos = stepDeployDtos.stream().map(stepDeployDto -> {
                    JpmStepDto stepDto = objectMapper.convertValue(stepDeployDto, JpmStepDto.class);
                    stepDto.setStepId(stepDeployDto.getStepDeployId());
                    stepDto.setStatusId(stepDeployDto.getStatusDeployId());

                    List<JpmButtonForStepDeployDto> buttonForStepDeployDtos = stepDeployDto.getButtonForStepDtos();
                    if (CommonCollectionUtil.isNotEmpty(buttonForStepDeployDtos)) {
                        List<JpmButtonForStepDto> buttonForStepDtos = buttonForStepDeployDtos.stream().map(buttonForStepDeployDto -> {
                            JpmButtonForStepDto buttonForStepDto = objectMapper.convertValue(buttonForStepDeployDto,
                                    JpmButtonForStepDto.class);
                            buttonForStepDto.setButtonId(buttonForStepDeployDto.getButtonDeployId());
                            buttonForStepDto.setPermissionId(buttonForStepDeployDto.getPermissionDeployId());
                            return buttonForStepDto;
                        }).collect(Collectors.toList());
                        stepDto.setButtonForStepDtos(buttonForStepDtos);
                    }

                    return stepDto;
                }).collect(Collectors.toList());
                resObj.setSteps(stepDtos);
            }

            List<JpmParamDeployDto> paramDeployDtos = processDeployDto.getParams();
            if (CommonCollectionUtil.isNotEmpty(paramDeployDtos)) {
                List<JpmParamDto> paramDtos = paramDeployDtos.stream().map(paramDeployDto -> {
                    JpmParamDto paramDto = objectMapper.convertValue(paramDeployDto, JpmParamDto.class);
                    paramDto.setParamId(paramDeployDto.getParamDeployId());

                    List<JpmParamConfigDeployDto> paramConfigDeployDtos = paramDeployDto.getParamConfigDtos();
                    if (CommonCollectionUtil.isNotEmpty(paramConfigDeployDtos)) {
                        List<JpmParamConfigDto> paramConfigDtos = paramConfigDeployDtos.stream().map(paramConfigDeployDto -> {
                            JpmParamConfigDto paramConfigDto = objectMapper.convertValue(paramConfigDeployDto, JpmParamConfigDto.class);
                            paramConfigDto.setStepId(paramConfigDeployDto.getStepDeployId());
                            return paramConfigDto;
                        }).collect(Collectors.toList());
                        paramDto.setParamConfigDtos(paramConfigDtos);
                    }
                    return paramDto;
                }).collect(Collectors.toList());
                resObj.setParams(paramDtos);
            }

            List<JpmStatusDeployDto> statusDeployDtos = processDeployDto.getStatuses();
            if (CommonCollectionUtil.isNotEmpty(statusDeployDtos)) {
                List<JpmStatusDto> statusDtos = statusDeployDtos.stream().map(statusDeployDto -> {
                    JpmStatusDto statusDto = objectMapper.convertValue(statusDeployDto, JpmStatusDto.class);
                    statusDto.setStatusId(statusDeployDto.getStatusDeployId());
                    return statusDto;
                }).collect(Collectors.toList());
                resObj.setStatuses(statusDtos);
            }
        }
        return resObj;
    }

    @Override
    public JpmProcessDeployDto getJpmProcessDeployLastedByBusinessCode(Long companyId, String businessCode) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        return jpmProcessDeployRepository.getJpmProcessDeployLastedByBusinessCode(companyId, businessCode, sysDate);
    }

    @Override
    public JpmProcessDeployDto getJpmProcessDeployDtoByProcessDeployId(Long processDeployId) {
        JpmProcessDeployDto jpmProcessDeployDto = null;
        if (Objects.nonNull(processDeployId)) {
            JpmProcessDeploy jpmProcessDeploy = jpmProcessDeployRepository.findOne(processDeployId);
            if (Objects.nonNull(jpmProcessDeploy) && 0L == jpmProcessDeploy.getDeletedId()) {
                jpmProcessDeployDto = objectMapper.convertValue(jpmProcessDeploy, JpmProcessDeployDto.class);
                jpmProcessDeployDto.setProcessDeployId(processDeployId);
                
                JpmBusinessDto businessDto = jpmBusinessService.getJpmBusinessDtoById(jpmProcessDeploy.getBusinessId());
                Integer processType = businessDto.getProcessType();
                jpmProcessDeployDto.setProcessType(processType);
            }
        }
        return jpmProcessDeployDto;
    }

    /** ===PRIVATE=== */
    private void getElementForProcess(JpmProcessDeployDto processDeployDto) {
        Long processDeployId = processDeployDto.getProcessDeployId();
        if (null == processDeployId) {
            return;
        }
        List<JpmStepDeployDto> steps = jpmStepDeployService.getStepDeployDtosByProcessDeployId(processDeployId);
        List<JpmParamDeployDto> params = jpmParamDeployService.getParamDeployDtosByProcessDeployId(processDeployId);
        List<JpmStatusDeployDto> statuses = jpmStatusDeployService.getStatusDeployDtosByProcessDeployId(processDeployId);
        List<JpmButtonDeployDto> buttons = jpmButtonDeployService.getButtonDeployDtosByProcessDeployId(processDeployId);
        List<JpmPermissionDeployDto> permissions = jpmPermissionDeployService.getPermissionDeployDtosByProcessDeployId(processDeployId);

        processDeployDto.setSteps(steps);
        processDeployDto.setParams(params);
        processDeployDto.setStatuses(statuses);
        processDeployDto.setButtons(buttons);
        processDeployDto.setPermissions(permissions);
    }
    
    @Override
    public List<JpmProcessDeployDto> getJpmProcessDeployByFormId(Long formId) {
        return jpmProcessDeployRepository.getJpmProcessDeployDtoByFormId(formId);
    }
	@Override
    public JpmProcessDeploy getJpmProcessDeployByProcessCode(String processCode) {
        return jpmProcessDeployRepository.getJpmProcessDeployByProcessCode(processCode);
    }
}