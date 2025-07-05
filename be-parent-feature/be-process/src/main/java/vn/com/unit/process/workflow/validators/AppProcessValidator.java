/*******************************************************************************
 * Class        JpmProcessValidator
 * Created date 2019/06/23
 * Lasted date  2019/06/23
 * Author       KhoaNA
 * Change log   2019/06/23 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.validators;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.validation.ValidationError;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.core.workflow.enumdef.ProcessType;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppButtonDto;
import vn.com.unit.process.workflow.dto.AppParamDto;
import vn.com.unit.process.workflow.dto.AppProcessDto;
import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.process.workflow.dto.AppStepDto;
import vn.com.unit.process.workflow.service.AppButtonService;
import vn.com.unit.process.workflow.service.AppParamService;
import vn.com.unit.process.workflow.service.AppProcessService;
import vn.com.unit.process.workflow.service.AppStatusDefaultService;
import vn.com.unit.process.workflow.service.AppStatusService;
import vn.com.unit.process.workflow.service.AppStepService;
import vn.com.unit.workflow.activiti.utils.BpmnUtil;
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.entity.JpmStatusDefault;
import vn.com.unit.workflow.enumdef.ButtonType;
import vn.com.unit.workflow.enumdef.StepKind;
import vn.com.unit.workflow.enumdef.StepType;

/**
 * JpmProcessValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class AppProcessValidator implements Validator {

    /** SerialInfoService */
    @Autowired
    private AppProcessService appProcessService;

    @Autowired
    private AppStepService appStepService;

    @Autowired
    private AppParamService appParamService;

    @Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private AppStatusDefaultService appStatusDefaultService;

    @Autowired
    private AppStatusService appStatusService;

    @Autowired
    private AppButtonService appButtonService;

    private static final String EXTENSION_BPMN20_XML = ".bpmn20.xml";
    private static final String EXTENSION_BPMN_XML = ".bpmn";

    private static final Logger logger = LoggerFactory.getLogger(AppProcessValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return AppProcessDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppProcessDto objectDto = (AppProcessDto) target;

        Long id = objectDto.getId();
        String code = objectDto.getCode();
        Long companyId = objectDto.getCompanyId();
        String name = objectDto.getName();
        boolean isClone = objectDto.getIsClone();
        String processType = objectDto.getProcessType();

        // validate code when create new data
        if (id == null) {
            this.validateCode(code, companyId, errors);
            this.validateName(name, companyId, errors);
        }

        if (!isClone && ProcessType.BPMN.toString().equals(processType)) {
            MultipartFile fileBpmn = objectDto.getFileProcess();

            if (fileBpmn != null) {
                String fileName = fileBpmn.getOriginalFilename();
                if (fileName.endsWith(EXTENSION_BPMN20_XML) || fileName.endsWith(EXTENSION_BPMN_XML)) {
                    try {
                        BpmnModel bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(fileBpmn.getInputStream());
                        this.validateBpmnFile(bpmnModel, errors);
                    } catch (IOException | XMLStreamException ex) {
                        logger.error("Error:Exception: ", ex);
                        errors.rejectValue("fileNameBpmn", "message.error.jpm.process.fileNameBpmn.invalid.format", null,
                                ConstantCore.EMPTY);
                    }
                } else {
                    errors.rejectValue("fileNameBpmn", "message.error.jpm.process.fileNameBpmn.invalid.format", null, ConstantCore.EMPTY);
                }
                try {
                    Map<String, AppStatusDto> statusMap = appProcessService.getStatusListByBpmnFile(fileBpmn.getInputStream());
                    List<JpmStatusDefault> statusDefalutList = appStatusDefaultService.getListDefault();
                    String codeFail = new String("");
                    for (JpmStatusDefault statusDefalut : statusDefalutList) {
                        String statusCode = statusDefalut.getStatusCode();
                        if (null != statusMap.remove(statusCode)) {
                            codeFail = codeFail.concat(statusCode).concat(ConstantCore.SEMI_COLON).concat(ConstantCore.SPACE);
                        }
                    }
                    if (StringUtils.isNotBlank(codeFail)) {
                        String[] mes = { codeFail };
                        errors.rejectValue("fileNameBpmn", "message.error.jpm.process.fileNameBpmn.status.has.default", mes,
                                ConstantCore.EMPTY);
                    }
                } catch (Exception e) {
                    errors.rejectValue("fileNameBpmn", "message.error.jpm.process.fileNameBpmn.invalid.format", null, ConstantCore.EMPTY);
                }

            } else if (id == null && fileBpmn == null) {
                errors.rejectValue("fileNameBpmn", "NotNull", null, ConstantCore.EMPTY);
            }
        }

        boolean isDeployed = objectDto.getIsDeployed();
        if (isDeployed) {
            Long processId = objectDto.getId();
            boolean hasStepSurvey = false;
            List<AppStepDto> listJpmStepDto = appStepService.getJpmStepDtoDetailByProcessId(processId,
                    LocaleContextHolder.getLocale().getLanguage());
            for (AppStepDto item : listJpmStepDto) {
                if (StepType.NORMAL_STEP.getValue().equalsIgnoreCase(item.getStepType()) && StringUtils.isEmpty(item.getButtonName())) {
                    errors.rejectValue("isActive", "message.error.jpm.process.step.no.config", null, ConstantCore.EMPTY);
                    break;
                }

                if (StepKind.PARALLEL_SURVEY.getValue().equalsIgnoreCase(item.getStepKind())) {
                    hasStepSurvey = true;
                }
            }

            AppParamDto[] listJpmParamDto = appParamService.getListJpmParamByProcessId(processId);
            for (AppParamDto item : listJpmParamDto) {
                if (StringUtils.isEmpty(item.getFormFieldName())) {
                    errors.rejectValue("isActive", "message.error.jpm.process.param.no.config", null, ConstantCore.EMPTY);
                    break;
                }
            }

            AppStatusDto[] listJpmStatusDto = appStatusService.getListJpmStatusByProcessId(processId);
            for (AppStatusDto item : listJpmStatusDto) {
                List<JpmStatusLangDto> listLang = item.getListJpmStatusLang();
                if (CollectionUtils.isEmpty(listLang)) {
                    errors.rejectValue("isActive", "message.error.jpm.process.status.no.config", new String[] { item.getStatusName() },
                            ConstantCore.EMPTY);
                    break;
                }
            }

            if (hasStepSurvey) {
                AppButtonDto[] listButton = appButtonService.getListJpmButtonByProcessId(processId);

                boolean hasAutoApprove = false;
                boolean hasAgree = false;
                boolean hasDeny = false;
                boolean hasNoIdeas = false;
                for (AppButtonDto item : listButton) {
                    if (ButtonType.APPROVE_AUTO.toString().equalsIgnoreCase(item.getButtonType())) {
                        hasAutoApprove = true;
                        continue;
                    }
                    if (ButtonType.SURVEY_AGREE.toString().equalsIgnoreCase(item.getButtonType())) {
                        hasAgree = true;
                        continue;
                    }
                    if (ButtonType.SURVEY_DENIED.toString().equalsIgnoreCase(item.getButtonType())) {
                        hasDeny = true;
                        continue;
                    }
                    if (ButtonType.SURVEY_NO_IDEAS.toString().equalsIgnoreCase(item.getButtonType())) {
                        hasNoIdeas = true;
                        continue;
                    }
                }
                if (!hasAutoApprove || !hasAgree || !hasDeny || !hasNoIdeas) {
                    errors.rejectValue("isActive", "message.error.jpm.process.button.do.not.have.auto.complete", null, ConstantCore.EMPTY);
                }
            }
        }
    }

    /**
     * Validate duplicate code by companyId
     * 
     * @param processCode
     *            type String
     * @param companyId
     *            type Long
     * @param errors
     *            type Errors
     * @author KhoaNA
     */
    private void validateCode(String processCode, Long companyId, Errors errors) {
        AppProcessDto appProcessDto = appProcessService.getJpmProcessByCodeAndCompanyId(processCode, companyId);
        if (appProcessDto != null) {
            String[] errorArgs = new String[1];
            errorArgs[0] = "code";
            errors.rejectValue("code", "message.error.jpm.process.code.existed", errorArgs, ConstantCore.EMPTY);
        }
    }

    /**
     * Validate duplicate name by companyId
     * 
     * @param processName
     *            type String
     * @param companyId
     *            type Long
     * @param errors
     *            type Errors
     * @author KhoaNA
     */
    private void validateName(String processName, Long companyId, Errors errors) {
        AppProcessDto appProcessDto = appProcessService.getJpmProcessByNameAndCompanyId(processName, companyId);
        if (appProcessDto != null) {
            String[] errorArgs = new String[1];
            errorArgs[0] = "name";
            errors.rejectValue("name", "message.error.jpm.process.name.existed", errorArgs, ConstantCore.EMPTY);
        }
    }

    /**
     * Validate bpmn file
     * 
     * @param bpmnModel
     *            type BpmnModel
     * @param errors
     *            type Errors
     * @author KhoaNA
     */
    private void validateBpmnFile(BpmnModel bpmnModel, Errors errors) {
        List<ValidationError> validationErrorList = processEngineConfiguration.getProcessValidator().validate(bpmnModel);
        if (validationErrorList != null && !validationErrorList.isEmpty()) {
            errors.rejectValue("fileNameBpmn", "message.error.jpm.process.fileNameBpmn.validation", null, ConstantCore.EMPTY);

            for (ValidationError validationError : validationErrorList) {
                String description = ConstantCore.OPENING_BRACKET.concat(validationError.getActivityId())
                        .concat(ConstantCore.CLOSING_BRACKET).concat(ConstantCore.HYPHEN).concat(validationError.getDefaultDescription());
                errors.rejectValue("filePathBpmn", null, description);
            }
        }

        // validate step endEvent
        Process mainProcess = bpmnModel.getMainProcess();

        /*
         * if( mainProcess == null ) { mainProcess = bpmnModel.getProcesses().get(0); }
         */

        Collection<FlowElement> processFlowElements = mainProcess.getFlowElements();
        FlowElement finishedElement = processFlowElements.stream()
                .filter(x -> x instanceof EndEvent && "Finished_999".equalsIgnoreCase(x.getId())).findAny().orElse(null);

        if (null == finishedElement) {
            errors.rejectValue("fileNameBpmn", "message.error.jpm.process.fileNameBpmn.validation", null, ConstantCore.EMPTY);
        }
    }
}
