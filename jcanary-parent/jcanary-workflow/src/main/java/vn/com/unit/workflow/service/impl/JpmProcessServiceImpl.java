/*******************************************************************************
* Class        JpmProcessServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.workflow.core.WorkflowDiagramService;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.JpmProcessLangDto;
import vn.com.unit.workflow.dto.JpmProcessSearchDto;
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.dto.JpmStepLangDto;
import vn.com.unit.workflow.entity.JpmProcess;
import vn.com.unit.workflow.enumdef.DocumentState;
import vn.com.unit.workflow.enumdef.PermissionType;
import vn.com.unit.workflow.enumdef.StepKind;
import vn.com.unit.workflow.enumdef.StepType;
import vn.com.unit.workflow.repository.JpmProcessRepository;
import vn.com.unit.workflow.service.JpmBusinessService;
import vn.com.unit.workflow.service.JpmButtonDefaultService;
import vn.com.unit.workflow.service.JpmButtonService;
import vn.com.unit.workflow.service.JpmHiProcessService;
import vn.com.unit.workflow.service.JpmLanguageService;
import vn.com.unit.workflow.service.JpmParamService;
import vn.com.unit.workflow.service.JpmPermissionService;
import vn.com.unit.workflow.service.JpmProcessLangService;
import vn.com.unit.workflow.service.JpmProcessService;
import vn.com.unit.workflow.service.JpmStatusCommonService;
import vn.com.unit.workflow.service.JpmStatusDefaultService;
import vn.com.unit.workflow.service.JpmStatusService;
import vn.com.unit.workflow.service.JpmStepService;

/**
 * JpmProcessServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmProcessServiceImpl implements JpmProcessService {

    @Autowired
    private JpmProcessRepository jpmProcessRepository;

    @Autowired
    private JpmProcessLangService jpmProcessLangService;

    @Autowired
    private JpmStepService jpmStepService;

    @Autowired
    private JpmParamService jpmParamService;

    @Autowired
    private JpmStatusService jpmStatusService;

    @Autowired
    private JpmButtonService jpmButtonService;

    @Autowired
    private JpmPermissionService jpmPermissionService;

    @Autowired
    private JpmHiProcessService jpmHiProcessService;

    @Autowired
    private JpmBusinessService jpmBusinessService;

    @Autowired
    private JpmButtonDefaultService jpmButtonDefaultService;

    @Autowired
    private JpmStatusDefaultService jpmStatusDefaultService;

    @Autowired
    private WorkflowDiagramService workflowDiagramService;

    @Autowired
    private JpmLanguageService jpmLanguageService;
    
    @Autowired
    private JpmStatusCommonService jpmStatusCommonService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmProcessDto getJpmProcessDtoById(Long id) {
        JpmProcessDto jpmProcessDto = null;
        if (Objects.nonNull(id)) {
            JpmProcess jpmProcess = jpmProcessRepository.findOne(id);
            if (Objects.nonNull(jpmProcess) && 0L == jpmProcess.getDeletedId()) {
                jpmProcessDto = objectMapper.convertValue(jpmProcess, JpmProcessDto.class);
                jpmProcessDto.setProcessId(id);

                List<JpmProcessLangDto> processLangs = jpmProcessLangService.getJpmProcessLangDtosByProcessId(id);
                jpmProcessDto.setProcessLangs(processLangs);

                // get detail
                this.getElementForProcess(jpmProcessDto);
            }
        }
        return jpmProcessDto;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (Objects.nonNull(id)) {
            JpmProcess jpmProcess = jpmProcessRepository.findOne(id);
            if (Objects.nonNull(jpmProcess) && 0L == jpmProcess.getDeletedId()) {
                jpmProcess.setDeletedId(userId);
                jpmProcess.setDeletedDate(sysDate);
                jpmProcessRepository.update(jpmProcess);
                res = true;
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcess saveJpmProcess(JpmProcess jpmProcess) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmProcess.getId();
        if (null != id) {
            JpmProcess oldJpmProcess = jpmProcessRepository.findOne(id);
            if (null != oldJpmProcess) {
                jpmProcess.setCreatedId(oldJpmProcess.getCreatedId());
                jpmProcess.setCreatedDate(oldJpmProcess.getCreatedDate());
                jpmProcess.setMajorVersion(oldJpmProcess.getMajorVersion());
                jpmProcess.setMinorVersion(oldJpmProcess.getMinorVersion());
                if (Objects.isNull(jpmProcess.getBpmnFilePath())) {
                    jpmProcess.setBpmnFilePath(oldJpmProcess.getBpmnFilePath());
                    jpmProcess.setBpmnRepoId(oldJpmProcess.getBpmnRepoId());
                }
                jpmProcess.setUpdatedId(userId);
                jpmProcess.setUpdatedDate(sysDate);
                jpmProcessRepository.update(jpmProcess);
            }
        } else {
            jpmProcess.setCreatedId(userId);
            jpmProcess.setCreatedDate(sysDate);
            jpmProcess.setUpdatedId(userId);
            jpmProcess.setUpdatedDate(sysDate);
            jpmProcessRepository.create(jpmProcess);
        }

        // save history
        jpmHiProcessService.saveJpmHiProcess(jpmProcess);

        return jpmProcess;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcess saveJpmProcessDto(JpmProcessDto jpmProcessDto) {
        Long processId = jpmProcessDto.getProcessId();
        boolean isInitDefault = false;
        // initial version
        if (null == processId) {
            jpmProcessDto.setMajorVersion(CommonConstant.NUMBER_ZERO_L);
            jpmProcessDto.setMinorVersion(CommonConstant.NUMBER_ZERO_L);
            isInitDefault = true;
        }

        JpmProcess jpmProcess = objectMapper.convertValue(jpmProcessDto, JpmProcess.class);
        jpmProcess.setId(processId);
        this.saveJpmProcess(jpmProcess);

        processId = jpmProcess.getId();
        jpmProcessDto.setProcessId(processId);

        // save language
        List<JpmProcessLangDto> processLangs = jpmProcessDto.getProcessLangs();
        if (CommonCollectionUtil.isNotEmpty(processLangs)) {
            jpmProcessLangService.saveProcessLangDtosByProcessId(processLangs, processId);
        }

        String bpmnFile = jpmProcessDto.getFileBpmn();
        if (Objects.nonNull(bpmnFile)) {
            byte[] fileContentBytes = CommonBase64Util.decodeToByte(bpmnFile);
            InputStream fileBpmnInputStream = new ByteArrayInputStream(fileContentBytes);

            List<JpmStepDto> stepDtos = this.buildStepDtosByProcessDiagram(fileBpmnInputStream);
            fileBpmnInputStream = new ByteArrayInputStream(fileContentBytes);
            List<JpmParamDto> paramDtos = workflowDiagramService.buildParamDtosByProcessDiagram(fileBpmnInputStream);
            List<JpmStatusDto> statusDtos = this.buildStatusDtoByStepDto(stepDtos);

            if (isInitDefault) {
                List<JpmPermissionDto> permissionDtos = this.buildPermissionDtoByStepDto(stepDtos);
                jpmPermissionService.savePermissionDtosByProcessId(permissionDtos, processId);

                List<JpmButtonDto> buttonDtos = jpmButtonDefaultService.getListButtonDefault();
                jpmButtonService.saveButtonDtosByProcessId(buttonDtos, processId);
            }

            List<JpmStatusDto> statusDefaultDtos = jpmStatusDefaultService.getListStatusDefault();
            // use stepNo ~ statusCode when build default to remap
            statusDefaultDtos.stream().forEach(status -> status.setStatusId(Long.valueOf(status.getStatusCode())));
            statusDtos.addAll(statusDefaultDtos);
            List<JpmStepDto> stepDefaultDtos = this.buildStepDtoByStatusDto(statusDefaultDtos);
            stepDtos.addAll(stepDefaultDtos);

            Map<Long, Long> stepNoToStatusIdConverter = jpmStatusService.saveStatusDtosByProcessId(statusDtos, processId);
            // remap status with step
            stepDtos.stream().forEach(stepDto -> stepDto.setStatusId(stepNoToStatusIdConverter.getOrDefault(stepDto.getStepNo(), null)));
            jpmStepService.saveStepDtosByProcessId(stepDtos, processId);
            jpmParamService.saveParamDtosByProcessId(paramDtos, processId);
        }

        // update version
        this.updateVersionForProcess(processId, false);

        return jpmProcess;
    }

    @Override
    public int countBySearchCondition(JpmProcessSearchDto searchDto) {
        return jpmProcessRepository.countBySearchCondition(searchDto);
    }

    @Override
    public List<JpmProcessDto> getProcessDtosByCondition(JpmProcessSearchDto searchDto, Pageable pageable) {
        return jpmProcessRepository.getProcessDtosByCondition(searchDto, pageable).getContent();
    }

    /**
     * <p>
     * Gets the element for process.
     * </p>
     *
     * @param processDto
     *            type {@link JpmProcessDto}
     * @return the element for process
     * @author KhuongTH
     */
    private void getElementForProcess(JpmProcessDto processDto) {
        Long processId = processDto.getProcessId();
        if (null == processId) {
            return;
        }
        List<JpmStepDto> steps = jpmStepService.getStepDtosByProcessId(processId);
        List<JpmParamDto> params = jpmParamService.getParamDtosByProcessId(processId);
        List<JpmStatusDto> statuses = jpmStatusService.getStatusDtosByProcessId(processId);
        List<JpmButtonDto> buttons = jpmButtonService.getButtonDtosByProcessId(processId);
        List<JpmPermissionDto> permissions = jpmPermissionService.getPermissionDtosByProcessId(processId);

        processDto.setSteps(steps);
        processDto.setParams(params);
        processDto.setStatuses(statuses);
        processDto.setButtons(buttons);
        processDto.setPermissions(permissions);
    }

    /**
     * <p>
     * Builds the status dto by step dto. use for init status
     * </p>
     *
     * @param stepDtos
     *            type {@link List<JpmStepDto>}
     * @return {@link List<JpmStatusDto>}
     * @author KhuongTH
     */
    private List<JpmStatusDto> buildStatusDtoByStepDto(List<JpmStepDto> stepDtos) {
        List<JpmStatusDto> statusDtos = new ArrayList<>();

        if (CommonCollectionUtil.isNotEmpty(stepDtos)) {
            for (JpmStepDto stepDto : stepDtos) {
                JpmStatusDto statusDto = new JpmStatusDto();
                statusDto.setStatusCode(workflowDiagramService.getStatusCodeByStepCode(stepDto.getStepCode()));
                statusDto.setStatusName(stepDto.getStepName());
                statusDto.setStatusId(stepDto.getStepNo());

                List<JpmStatusLangDto> statusLangDtos = new ArrayList<>();
                List<JpmStepLangDto> stepLangDtos = stepDto.getStepLangs();
                if (CommonCollectionUtil.isNotEmpty(stepLangDtos)) {
                    for (JpmStepLangDto stepLangDto : stepLangDtos) {
                        JpmStatusLangDto statusLangDto = new JpmStatusLangDto();
                        statusLangDto.setLangCode(stepLangDto.getLangCode());
                        statusLangDto.setLangId(stepLangDto.getLangId());
                        statusLangDto.setStatusName(stepLangDto.getStepName());

                        statusLangDtos.add(statusLangDto);
                    }
                }
                statusDto.setStatusLangs(statusLangDtos);
                statusDtos.add(statusDto);
            }
        }
        return statusDtos;
    }

    /**
     * <p>
     * Builds the step dto by status dto. use for build step default by status default
     * </p>
     *
     * @param statusDtos
     *            type {@link List<JpmStatusDto>}
     * @return {@link List<JpmStepDto>}
     * @author KhuongTH
     */
    private List<JpmStepDto> buildStepDtoByStatusDto(List<JpmStatusDto> statusDtos) {
        List<JpmStepDto> stepDtos = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(statusDtos)) {
            Map<String, Long> statusCommonConverter = jpmStatusCommonService.getStatusCommonIdConverter();
            stepDtos = statusDtos.stream().map(statusDto -> {
                JpmStepDto stepDto = new JpmStepDto();
                stepDto.setStatusCode(statusDto.getStatusCode());
                stepDto.setStepNo(Long.valueOf(statusDto.getStatusCode()));
                stepDto.setStepCode(CommonStringUtil.lowerCase(statusDto.getStatusName()).concat(CommonConstant.UNDERSCORE)
                        .concat(statusDto.getStatusCode()));
                stepDto.setStepName(statusDto.getStatusName());
                stepDto.setStepType(StepType.SYSTEM_STEP.getValue());
                stepDto.setStepKind(StepKind.NORMAL.getValue());
                stepDto.setCommonStatusCode(statusDto.getStatusCode());
                stepDto.setCommonStatusId(statusCommonConverter.getOrDefault(statusDto.getStatusCode(), 0L));

                List<JpmStatusLangDto> statusLangDtos = statusDto.getStatusLangs();
                if (CommonCollectionUtil.isNotEmpty(statusLangDtos)) {
                    List<JpmStepLangDto> stepLangDtos = statusLangDtos.stream().map(statusLangDto -> {
                        JpmStepLangDto stepLangDto = new JpmStepLangDto();
                        stepLangDto.setLangCode(statusLangDto.getLangCode());
                        stepLangDto.setLangId(statusLangDto.getLangId());
                        stepLangDto.setStepName(statusLangDto.getStatusName());
                        return stepLangDto;
                    }).collect(Collectors.toList());
                    stepDto.setStepLangs(stepLangDtos);
                }
                return stepDto;
            }).collect(Collectors.toList());
        }
        return stepDtos;
    }

    private List<JpmPermissionDto> buildPermissionDtoByStepDto(List<JpmStepDto> stepDtos) {
        List<JpmPermissionDto> permissionDtos = new ArrayList<>();

        int count = 1;
        if (CommonCollectionUtil.isNotEmpty(stepDtos)) {
            for (JpmStepDto stepDto : stepDtos) {
                JpmPermissionDto permissionDto = new JpmPermissionDto();
                permissionDto.setPermissionType(PermissionType.USER.getValue());
                permissionDto.setPermissionCode(CommonStringUtil.leftPad(String.valueOf(count), 5, "0"));
                permissionDto.setPermissionName(stepDto.getStepName());
                permissionDtos.add(permissionDto);
                count++;
            }
        }

        return permissionDtos;
    }

    @Override
    public JpmProcessDto getJpmProcessDtoByProcessId(Long processId) {
        JpmProcessDto processDto = jpmProcessRepository.getJpmProcessDtoByProcessId(processId);
        List<JpmProcessLangDto> processLangs = jpmProcessLangService.getJpmProcessLangDtosByProcessId(processId);
        processDto.setProcessLangs(processLangs);
        return processDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importProcess(JpmProcessImportExportDto processImportDto) throws DetailException {
        Long companyId = processImportDto.getCompanyId();
        String processCode = processImportDto.getProcessCode();

        JpmProcessDto currentProcessDto = this.getProcessDtoByCodeAndCompanyId(processCode, companyId);
        Long processId = null;
        Long majorVersion = null;
        Long minorVersion = null;
        if (Objects.nonNull(currentProcessDto)) {
            processId = currentProcessDto.getProcessId();
            majorVersion = currentProcessDto.getMajorVersion();
            minorVersion = currentProcessDto.getMinorVersion() + CommonConstant.NUMBER_ONE_L;
        } else {
            majorVersion = CommonConstant.NUMBER_ZERO_L;
            minorVersion = CommonConstant.NUMBER_ZERO_L;
        }

        JpmBusinessDto businessDto = processImportDto.getBusinessDto();
        JpmBusinessDto currentBusinessDto = jpmBusinessService.getBusinessDtoByCodeAndCompanyId(businessDto.getBusinessCode(), companyId);
        Long businessId;
        if (Objects.isNull(currentBusinessDto)) {
            businessDto.setCompanyId(companyId);
            businessDto.setBusinessId(null);
            jpmBusinessService.saveJpmBusinessDto(businessDto);
            businessId = businessDto.getBusinessId();
        } else {
            businessId = currentBusinessDto.getBusinessId();
        }
        JpmProcessDto processDto = objectMapper.convertValue(processImportDto, JpmProcessDto.class);
        processDto.setBusinessId(businessId);
        processDto.setProcessId(processId);
        processDto.setActived(true);
        processDto.setMajorVersion(majorVersion);
        processDto.setMinorVersion(minorVersion);

        // save process
        JpmProcess jpmProcess = objectMapper.convertValue(processDto, JpmProcess.class);
        jpmProcess.setId(processId);
        this.saveJpmProcess(jpmProcess);
        processId = jpmProcess.getId();

        // update version
        this.updateVersionForProcess(processId, false);
        
        // save process language
        List<JpmProcessLangDto> processLangs = processDto.getProcessLangs();
        jpmProcessLangService.saveProcessLangDtosByProcessId(processLangs, processId);

        processId = jpmProcess.getId();

        List<JpmPermissionDto> permissionDto = processDto.getPermissions();
        Map<Long, Long> conveterPermission = jpmPermissionService.savePermissionDtosByProcessId(permissionDto, processId);

        List<JpmButtonDto> buttonDtos = processDto.getButtons();
        Map<Long, Long> conveterButton = jpmButtonService.saveButtonDtosByProcessId(buttonDtos, processId);

        List<JpmStatusDto> statusDtos = processDto.getStatuses();

        Map<Long, Long> conveterStatus = jpmStatusService.saveStatusDtosByProcessId(statusDtos, processId);

        List<JpmStepDto> stepDtos = processDto.getSteps();

        stepDtos.stream().forEach(stepDto -> {
            stepDto.setStatusId(conveterStatus.get(stepDto.getStatusId()));
            List<JpmButtonForStepDto> buttonForStepDtos = stepDto.getButtonForStepDtos();
            if (CommonCollectionUtil.isNotEmpty(buttonForStepDtos)) {
                buttonForStepDtos.stream().forEach(buttonStepDto -> {
                    buttonStepDto.setButtonId(conveterButton.get(buttonStepDto.getButtonId()));
                    buttonStepDto.setPermissionId(conveterPermission.get(buttonStepDto.getPermissionId()));
                });
            }
        });
        Map<Long, Long> conveterStep = jpmStepService.saveStepDtosByProcessId(stepDtos, processId);

        List<JpmParamDto> paramDtos = processDto.getParams();
        paramDtos.stream().forEach(parramDto -> {
            List<JpmParamConfigDto> paramConfigDtos = parramDto.getParamConfigDtos();
            if (CommonCollectionUtil.isNotEmpty(paramConfigDtos)) {
                paramConfigDtos.stream().forEach(paramConfigDto -> paramConfigDto.setStepId(conveterStep.get(paramConfigDto.getStepId())));
            }
        });
        jpmParamService.saveParamDtosByProcessId(paramDtos, processId);

        return jpmProcess.getId();
    }

    @Override
    public JpmProcessDto getProcessDtoByCodeAndCompanyId(String processCode, Long companyId) {
        return jpmProcessRepository.getProcessDtoByCodeAndCompanyId(processCode, companyId);
    }

    @Override
    public byte[] updateProcessInfo(InputStream fileStream, JpmProcessDto processDto) {

        // update info
        Long companyId = processDto.getCompanyId();
        String processCode = processDto.getProcessCode();

        // Long businessId = processDto.getBusinessId();
        // JpmBusinessDto jpmBusinessDto = jpmBusinessService.getJpmBusinessDtoById(businessId);
        String businessCode = processDto.getBusinessCode();// jpmBusinessDto.getBusinessCode();
        String processKey = "C".concat(String.valueOf(companyId)).concat(CommonConstant.UNDERSCORE).concat(processCode);
        String processCategory = businessCode;

        return workflowDiagramService.updateProcessInfo(fileStream, processKey, processCategory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateVersionForProcess(Long processId, boolean isMajor) {
        boolean res = false;

        if (Objects.nonNull(processId)) {
            JpmProcess process = jpmProcessRepository.findOne(processId);
            if (Objects.nonNull(process)) {
                if (isMajor) {
                    process.setMajorVersion(process.getMajorVersion() + CommonConstant.NUMBER_ONE_L);
                    process.setMinorVersion(CommonConstant.NUMBER_ZERO_L);
                } else {
                    process.setMinorVersion(process.getMinorVersion() + CommonConstant.NUMBER_ONE_L);
                }
                jpmProcessRepository.update(process);
            }
        }

        return res;
    }

    private List<JpmStepDto> buildStepDtosByProcessDiagram(InputStream fileBpmnInputStream) {
        List<JpmStepDto> stepDtos = workflowDiagramService.buildStepDtosByProcessDiagram(fileBpmnInputStream);

        if (CommonCollectionUtil.isNotEmpty(stepDtos)) {
            Map<String, Long> statusCommonConverter = jpmStatusCommonService.getStatusCommonIdConverter(); 
            for (JpmStepDto stepDto : stepDtos) {
                Map<String, Long> langCodes = jpmLanguageService.getLanguageIdConverter();
                String stepName = stepDto.getStepName();
                List<JpmStepLangDto> stepLangs = new ArrayList<>();
                if (CommonMapUtil.isNotEmpty(langCodes)) {
                    for (Entry<String, Long> entry : langCodes.entrySet()) {
                        JpmStepLangDto stepLangDto = new JpmStepLangDto();
                        stepLangDto.setLangCode(entry.getKey());
                        stepLangDto.setLangId(entry.getValue());
                        stepLangDto.setStepName(stepName);
                        stepLangs.add(stepLangDto);
                    }
                }
                stepDto.setStepLangs(stepLangs);
                stepDto.setCommonStatusCode(DocumentState.IN_PROGRESS.toString());
                stepDto.setCommonStatusId(statusCommonConverter.getOrDefault(stepDto.getCommonStatusCode(), 0L));
            }
        }

        return stepDtos;
    }

    @Override
    public JpmProcessDto getJpmProcessDtoByBusinessId(Long businessId, Long companyId) {
        return jpmProcessRepository.getProcessDtoByBusinessIdAndCompanyId(businessId, companyId);
    }
}