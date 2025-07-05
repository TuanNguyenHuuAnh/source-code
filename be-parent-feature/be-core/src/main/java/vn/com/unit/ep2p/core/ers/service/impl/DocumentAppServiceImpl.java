/*******************************************************************************
 * Class        ：DocumentAppServiceImpl
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.ers.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.enumdef.ReferenceType;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountOrgService;
import vn.com.unit.core.service.JcaOrganizationService;
import vn.com.unit.core.workflow.dto.DocumentAction;
import vn.com.unit.core.workflow.service.DocumentWorkflowService;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileVersionDto;
import vn.com.unit.ep2p.core.efo.service.EfoDocService;
import vn.com.unit.ep2p.core.efo.service.EfoFormService;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocMainFileVersionService;
import vn.com.unit.ep2p.core.exception.HandlerCastException;
import vn.com.unit.ep2p.core.res.dto.CalendarTimeOffRes;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
import vn.com.unit.ep2p.core.res.dto.RecruitmentCandidateResultActionRes;
import vn.com.unit.ep2p.core.res.dto.TaskSlaRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.DocumentAppService;
import vn.com.unit.ep2p.core.service.DocumentMainFileService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.AccountDetailDto;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaDateResultDto;
import vn.com.unit.sla.entity.SlaConfig;
import vn.com.unit.sla.service.SlaCalculateService;
import vn.com.unit.sla.service.SlaCalendarService;
import vn.com.unit.sla.service.SlaConfigService;
import vn.com.unit.sla.utils.SlaDateUtils;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.service.JpmProcessInstActService;
import vn.com.unit.workflow.constant.WorkflowConstant;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.workflow.dto.JpmHiTaskDto;
import vn.com.unit.workflow.dto.JpmHiTaskSlaDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.dto.JpmTaskDto;
import vn.com.unit.workflow.dto.JpmTaskSlaDto;
import vn.com.unit.workflow.entity.JpmHiTask;
import vn.com.unit.workflow.entity.JpmProcessDeploy;
import vn.com.unit.workflow.entity.JpmStepDeploy;
import vn.com.unit.workflow.enumdef.DocumentState;
import vn.com.unit.workflow.service.JpmButtonDeployService;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;
import vn.com.unit.workflow.service.JpmHiTaskService;
import vn.com.unit.workflow.service.JpmHiTaskSlaService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmStatusCommonService;
import vn.com.unit.workflow.service.JpmStatusDeployService;
import vn.com.unit.workflow.service.JpmStepDeployService;
import vn.com.unit.workflow.service.JpmTaskService;
import vn.com.unit.workflow.service.JpmTaskSlaService;

/**
 * DocumentAppServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@SuppressWarnings("rawtypes")
@Service("documentAppService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocumentAppServiceImpl<SAVE, ACTION> extends AbstractCommonService implements DocumentAppService {

    @Autowired
    protected HandlerCastException handlerCastException;

    @Autowired
    private EfoDocService efoDocService;

    @Autowired
    private DocumentWorkflowService documentWorkflowService;

    @Autowired
    private JpmButtonForStepDeployService jpmButtonForStepDeployService;

    @Autowired
    private MasterCommonService masterCommonService;

    @Autowired
    private JpmHiTaskService jpmHiTaskService;

    @Autowired
    private JpmHiTaskSlaService jpmHiTaskSlaService;

    @Autowired
    private JpmTaskSlaService jpmTaskSlaService;

    @Autowired
    private EfoOzDocMainFileVersionService efoOzDocMainFileVersionService;

    @Autowired
    private DocumentMainFileService documentMainFileService;

    @Autowired
    private EfoFormService efoFormService;

    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    @Autowired
    private JpmStepDeployService jpmStepDeployService;

    @Autowired
    private JpmProcessInstActService jpmProcessInstActService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JcaAccountOrgService jcaAccountOrgService;

    @Autowired
    private JcaOrganizationService jcaOrganizationService;

    @Autowired
    private SlaConfigService slaConfigService;

    @Autowired
    private SlaCalculateService slaCalculateService;

    @Autowired
    private JpmStatusCommonService jpmStatusCommonService;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private SlaCalendarService slaCalendarService;

    @Autowired
    private JpmTaskService jpmTaskService;

    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;

    @Autowired
    private JpmButtonDeployService jpmButtonDeployService;
    
    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;
    
    private static final String STATUS_COMMON_DRAFT = "000";
    private static final String STATUS_COMMON_FINISHED = "999";

    private static final Logger logger = LoggerFactory.getLogger(DocumentAppServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoDocDto save(EfoDocDto efoDocDto) throws Exception {
        try {
            efoDocDto.setSystemCode(AppCoreConstant.SYSTEM_CODE_PPL);
            efoDocDto.setAppCode(AppCoreConstant.APP_CODE_WEB);
            DocumentAction documentAction = new DocumentAction(efoDocService);

            /** BEGIN Save main file */
            EfoOzDocMainFileDto mainFileDto = documentMainFileService.buildEfoOzDocMainFileDto(efoDocDto);
            // DocumentActionFlag actionFlag = null;
            // if (null != mainFileDto.getId()) {
            // actionFlag = DocumentActionFlag.UPDATE_DATA;
            // }else {
            // actionFlag = DocumentActionFlag.CREATE_DATA;
            // }
            // EfoOzDocMainFileDto resDto =
            // documentMainFileService.saveOzDocMainFile(mainFileDto, actionFlag, true,
            // true);
            efoDocDto.setDocId(null == mainFileDto.getDocId() ? mainFileDto.getDocIdTmp() : mainFileDto.getDocId());
            efoDocDto.setFormId(efoDocDto.getFormId() != null ? efoDocDto.getFormId() : efoDocDto.getDocId());
            efoDocDto.setDocCode(mainFileDto.getDocCode());
            // efoDocDto.setMainFileId(resDto.getId());
            /** END */

            efoDocDto = documentWorkflowService.saveDocument(efoDocDto, documentAction);

            // Save JPM_PROCESS_INST_ACT
            int refType = 1;
            JpmProcessInstActDto instActDto = jpmProcessInstActService
                    .getJpmProcessInstActDtoByReference(efoDocDto.getDocId(), refType);
            if (instActDto == null) {
                JpmProcessInstActDto jpmProcessInstActDto = new JpmProcessInstActDto();
                jpmProcessInstActDto.setProcessDeployId(efoDocDto.getProcessDeployId());
                jpmProcessInstActDto.setBusinessId(efoDocDto.getBusinessId());
                jpmProcessInstActDto.setReferenceId(efoDocDto.getDocId());
                jpmProcessInstActDto.setReferenceType(refType);

                Long draftStatusId = jpmStatusCommonService.getStatusCommonIdConverter()
                        .get(DocumentState.DRAFT.toString());
                jpmProcessInstActDto.setCommonStatusId(draftStatusId);
                jpmProcessInstActDto.setCommonStatusCode(DocumentState.DRAFT.toString());

                jpmProcessInstActService.saveJpmProcessInstActDto(jpmProcessInstActDto);
            }

        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024402_APPAPI_DOCUMENT_ADD_ERROR);
        }
        return efoDocDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoDocDto action(EfoDocDto efoDocDto) throws Exception {

        efoDocDto.setSystemCode(AppCoreConstant.SYSTEM_CODE_PPL);
        efoDocDto.setAppCode(AppCoreConstant.APP_CODE_WEB);
        try {
            DocumentAction documentAction = new DocumentAction(efoDocService);

            int refType = 1;
            JpmProcessInstActDto instActDto = jpmProcessInstActService
                    .getJpmProcessInstActDtoByReference(efoDocDto.getDocId(), refType);

            String docState = (instActDto == null
                    || DocumentState.DRAFT.toString().equals(instActDto.getCommonStatusCode()))
                            ? DocumentState.DRAFT.toString()
                            : instActDto.getCommonStatusCode();
            efoDocDto.setCommonStatusCode(docState);

            ActionDto actionDto = new ActionDto();
            // Get actionDto
            actionDto = jpmButtonForStepDeployService.getActionDtoByCoreTaskIdAndButtonId(
                    efoDocDto.getProcessDeployId(), efoDocDto.getCoreTaskId(), efoDocDto.getButtonId(),
                    efoDocDto.getCommonStatusCode());
            // action document
            documentWorkflowService.actionDocument(efoDocDto, actionDto, documentAction);

            /** BEGIN Save main file */
            // up version main file
            if (actionDto.isSave()) {
                // EfoOzDocMainFileDto mainFileDto =
                // documentMainFileService.buildEfoOzDocMainFileDto(efoDocDto);
                // documentMainFileService.saveOzDocMainFile(mainFileDto,
                // DocumentActionFlag.UPDATE_DATA, true, true);
            }
            /** END */
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024403_APPAPI_DOCUMENT_ACTION_ERROR);
        }
        return efoDocDto;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentActionReq action(DocumentActionReq documentActionReq, Locale locale) throws Exception {
        // 1. get efo doc
        EfoDocDto efoDocDto = setValueActionDocument(documentActionReq);
        efoDocDto.setProcessStatusCodePrev(efoDocDto.getProcessStatusCode());

        if (efoDocDto.getId() == null) {
            efoDocDto = setValueSaveDocument(documentActionReq);
            efoDocDto.setCompanyId(UserProfileUtils.getCompanyId());
            efoDocDto = save(efoDocDto);
            documentActionReq.setDocId(efoDocDto.getDocId());
            DocumentAppRes doc = detail(efoDocDto.getDocId());
            if (doc != null && !doc.getJpmButtons().isEmpty()) {
                JpmButtonWrapper<JpmButtonForDocDto> lstButton = (JpmButtonWrapper<JpmButtonForDocDto>) doc
                        .getJpmButtons();
                if (lstButton != null && !lstButton.getData().isEmpty()) {
                    documentActionReq.setButtonId(lstButton.getData().get(0).getId());
                }
            }
        }

        // 2. set param for email template
        String jsonEmail = setInputJsonForEmail(documentActionReq);
        efoDocDto.setInputJsonEmail(jsonEmail);

        // 3. action document
        if (documentActionReq.isNextStep()) {
            action(efoDocDto);
        }

        // 4. action business
        DocumentActionReq dto = actionBusiness(documentActionReq, efoDocDto, locale);

        // 5. send mail process
        try {
            if (documentActionReq.isNextStep()) {
                AccountDetailDto accountDetail = new AccountDetailDto();
                accountDetail.setUsername(UserProfileUtils.getUserNameLogin());
                accountDetail.setEmail(UserProfileUtils.getUserPrincipal().getEmail());
                accountDetail.setId(UserProfileUtils.getAccountId());

                sendMailProcess(dto, null, null, dto.getStepNo(), accountDetail, null, locale);
            }
        } catch (Exception e) {
            logger.error("#####sendMail#####", e.getCause());
        }

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentActionReq actionBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto, Locale locale)
            throws Exception {
        Long documentId = efoDocDto.getDocId();
        if (null != documentId) {
            this.convertOzDocDtoByRestActionDto(documentActionReq, efoDocDto);
        } else {
            documentActionReq = objectMapper.convertValue(documentActionReq, DocumentActionReq.class);
            // this.setOrgAndPosMainByAcountId(efoDocDto);
        }

        // Lưu dữ liệu của bảng liên quan
        return documentActionReq;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setParamBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto) throws Exception {
        Long documentId = efoDocDto.getDocId();
        if (null != documentId) {
            this.convertOzDocDtoByRestActionDto(documentActionReq, efoDocDto);
        } else {
            documentActionReq = objectMapper.convertValue(documentActionReq, DocumentActionReq.class);
            // this.setOrgAndPosMainByAcountId(efoDocDto);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public DocumentAppRes detail(Long documentId) throws Exception {
        DocumentAppRes res = new DocumentAppRes();
        DocumentDataResultDto efoOzDocDto = null;
        Long accountId = UserProfileUtils.getAccountId();
        try {
            Map<String, List<Long>> mapResultAccountOrg = masterCommonService.getMapOrgAndPosByAccountId(accountId);
            List<Long> posIds = mapResultAccountOrg.get(AppCoreConstant.PARAMETTER_POS_IDS);
            List<Long> orgIds = mapResultAccountOrg.get(AppCoreConstant.PARAMETTER_ORG_IDS);
            List<String> refTypes = new ArrayList<>();
            refTypes.add(ReferenceType.REF_CC.toString());
            refTypes.add(ReferenceType.REF_TRANFER.toString());

            efoOzDocDto = efoDocService.getDetailDocumentByRole(posIds, orgIds, refTypes, documentId);
            if (null == efoOzDocDto) {
                logger.info(AppApiExceptionCodeConstant.E4024404_APPAPI_DOCUMENT_NOT_FOUND);
                throw new DetailException(AppApiExceptionCodeConstant.E4024404_APPAPI_DOCUMENT_NOT_FOUND);
            }
            res.setEfoDocDto(efoOzDocDto);
            // get process history
            List<JpmHiTaskDto> listProcessHistory = new ArrayList<>();
            listProcessHistory = this.getListProcessHistoryDocument(efoOzDocDto.getId());
            res.setListProcessHistory(listProcessHistory);

            // get document history
            List<EfoOzDocMainFileVersionDto> listOzDocVersion = new ArrayList<>();
            listOzDocVersion = efoOzDocMainFileVersionService
                    .getListEfoOzDocMainFileVersionDtoByOzDocId(efoOzDocDto.getId());
            res.setListOzDocVersion(listOzDocVersion);

            // get list button
            List<Long> ids = new ArrayList<>();
            ids.add(documentId);
            List<JpmButtonForDocDto> buttonForDocDtos = jpmButtonForStepDeployService
                    .getListButtonForStepDeployDtoByEfoOzDocIds(ids, accountId, UserProfileUtils.getLanguage());
            JpmButtonWrapper<JpmButtonForDocDto> jpmButtons = jpmButtonForStepDeployService
                    .getListButtonForDocDtoByDocId(buttonForDocDtos, documentId);

            String coreTaskId = CollectionUtils.isNotEmpty(jpmButtons.getData())
                    ? jpmButtons.getData().get(0).getCoreTaskId()
                    : null;
            efoOzDocDto.setCoreTaskId(coreTaskId);

            // load data from form id
            DocumentActionReq data = loadData(documentId);
            res.setDto(data);

            // handle button by business
            jpmButtons = handleButtonByBusiness(data, jpmButtons);
            res.setJpmButtons(jpmButtons);

        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024403_APPAPI_DOCUMENT_ACTION_ERROR);
        }
        return res;
    }

    @Override
    public List<JpmHiTaskDto> getListProcessHistoryDocument(Long documentId) {
        List<JpmHiTaskDto> jpmHiTaskDtoResultList = jpmHiTaskService.getListJpmHiTaskDtoByDocId(documentId);
        
        JpmStatusCommonDto statusCommon = jpmStatusCommonService.getStatusCommon(STATUS_COMMON_DRAFT,
                UserProfileUtils.getLanguage());
        
        JpmStatusDeployDto statusFisnied = jpmStatusDeployService.getStatusDeployByStatusCode(documentId,
                STATUS_COMMON_FINISHED, UserProfileUtils.getLanguage());
        
        if (CollectionUtils.isNotEmpty(jpmHiTaskDtoResultList)) {
            for (int i = 0; i < jpmHiTaskDtoResultList.size(); i++) {
                if (i == 0) {
                    if (statusCommon != null) {
                        jpmHiTaskDtoResultList.get(i).setStatusName(statusCommon.getStatusName());
                    }
                    if (jpmHiTaskDtoResultList.size() == i + 1 && ObjectUtils.isEmpty(statusFisnied)) {
                        jpmHiTaskDtoResultList.get(i).setNextStatus(jpmHiTaskDtoResultList.get(i + 1).getStatusName());
                    }
                } else if (i < jpmHiTaskDtoResultList.size() - 1 && ObjectUtils.isEmpty(statusFisnied)) {
                    if (StringUtils.isNotBlank(jpmHiTaskDtoResultList.get(i + 1).getStatusName())) {
                        jpmHiTaskDtoResultList.get(i).setNextStatus(jpmHiTaskDtoResultList.get(i + 1).getStatusName());
                    }
                }
            }
            if (StringUtils.isBlank(jpmHiTaskDtoResultList.get(jpmHiTaskDtoResultList.size() - 1).getNextStatus())) {
                if (statusFisnied != null) {
                    jpmHiTaskDtoResultList.get(jpmHiTaskDtoResultList.size() - 1)
                            .setNextStatus(statusFisnied.getStatusName());
                }
            }
        }
        
        jpmHiTaskDtoResultList.stream().map(result -> {

            String jsonData = result.getJsonData();

            if (null != jsonData) {
                JpmHiTaskDto jpmHiTaskDto;
                try {
                    jpmHiTaskDto = CommonJsonUtil.convertJSONToObject(result.getJsonData(), JpmHiTaskDto.class);
                    /** set value complete */
                    result.setCompletedAvatar(jpmHiTaskDto.getCompletedAvatar());
                    result.setCompletedAvatarRepoId(jpmHiTaskDto.getCompletedAvatarRepoId());
                    result.setCompletedCode(jpmHiTaskDto.getCompletedCode());
                    result.setCompletedEmail(jpmHiTaskDto.getCompletedEmail());
                    result.setCompletedFullName(jpmHiTaskDto.getCompletedFullName());
                    result.setCompletedId(jpmHiTaskDto.getCompletedId());
                    result.setCompletedName(jpmHiTaskDto.getCompletedName());
                    result.setCompletedOrgId(jpmHiTaskDto.getCompletedOrgId());
                    result.setCompletedOrgName(jpmHiTaskDto.getCompletedOrgName());
                    result.setCompletedPositionId(jpmHiTaskDto.getCompletedPositionId());
                    result.setCompletedPositionName(jpmHiTaskDto.getCompletedPositionName());

                    /** set value submitted */
                    result.setSubmittedAvatar(jpmHiTaskDto.getSubmittedAvatar());
                    result.setSubmittedAvatarRepoId(jpmHiTaskDto.getSubmittedAvatarRepoId());
                    result.setSubmittedCode(jpmHiTaskDto.getSubmittedCode());
                    result.setSubmittedEmail(jpmHiTaskDto.getSubmittedEmail());
                    result.setSubmittedFullName(jpmHiTaskDto.getSubmittedFullName());
                    result.setSubmittedId(jpmHiTaskDto.getSubmittedId());
                    result.setSubmittedName(jpmHiTaskDto.getSubmittedName());
                    result.setSubmittedOrgId(jpmHiTaskDto.getSubmittedOrgId());
                    result.setSubmittedOrgName(jpmHiTaskDto.getSubmittedOrgName());
                    result.setSubmittedPositionId(jpmHiTaskDto.getSubmittedPositionId());
                    result.setSubmittedPositionName(jpmHiTaskDto.getSubmittedPositionName());

                    /** set value owner */
                    result.setOwnerAvatar(jpmHiTaskDto.getOwnerAvatar());
                    result.setOwnerAvatarRepoId(jpmHiTaskDto.getOwnerAvatarRepoId());
                    result.setOwnerCode(jpmHiTaskDto.getOwnerCode());
                    result.setOwnerEmail(jpmHiTaskDto.getOwnerEmail());
                    result.setOwnerFullName(jpmHiTaskDto.getOwnerFullName());
                    result.setOwnerId(jpmHiTaskDto.getOwnerId());
                    result.setOwnerName(jpmHiTaskDto.getOwnerName());
                    result.setOwnerOrgId(jpmHiTaskDto.getOwnerOrgId());
                    result.setOwnerOrgName(jpmHiTaskDto.getOwnerOrgName());
                    result.setOwnerPositionId(jpmHiTaskDto.getOwnerPositionId());
                    result.setOwnerPositionName(jpmHiTaskDto.getOwnerPositionName());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return result;
        }).collect(Collectors.toList());
        
        return jpmHiTaskDtoResultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DocumentAppRes initDocument(Long formId) {
        DocumentAppRes documentAppRes = new DocumentAppRes();
        DocumentDataResultDto ozDocDto = new DocumentDataResultDto();
        documentAppRes.setEfoDocDto(ozDocDto);
        ozDocDto.setCommonStatusCode(DocumentState.DRAFT.toString());
        if (Objects.nonNull(formId)) {
            ozDocDto.setFormId(formId);
            EfoFormDto formDto = efoFormService.getEfoFormDtoById(formId);
            if (Objects.nonNull(formDto)) {
                Long businessId = formDto.getBusinessId();
                Long companyId = formDto.getCompanyId();
                ozDocDto.setBusinessId(businessId);
                ozDocDto.setCompanyId(companyId);
                ozDocDto.setOzFilePath(formDto.getOzFilePath());
                ozDocDto.setFormName(formDto.getFormName());
                ozDocDto.setMainFileNameView(formDto.getFormName());
                JpmProcessDeployDto processDeployDto = jpmProcessDeployService.getJpmProcessDeployLasted(companyId,
                        businessId);
                if (Objects.nonNull(processDeployDto)) {
                    Long processDeployId = processDeployDto.getProcessDeployId();
                    ozDocDto.setProcessDeployId(processDeployId);

                    JpmStepDeploy jpmStepDeployFirst = jpmStepDeployService
                            .getJpmStepDeployFirstByProcessDeployId(processDeployId);
                    String stepCode = jpmStepDeployFirst.getStepCode();
                    JpmButtonWrapper<JpmButtonForDocDto> jpmButtons = jpmButtonForStepDeployService
                            .getListButtonForDocDtoByProcessDeployIdAndStepCode(processDeployId, stepCode,
                                    UserProfileUtils.getLanguage());

                    // load data from form id
                    DocumentActionReq data = loadData(formId);
                    documentAppRes.setDto(data);

                    // handle button by business
                    jpmButtons = handleButtonByBusiness(data, jpmButtons);

                    documentAppRes.setJpmButtons(jpmButtons);
                }
            }
        }
        return documentAppRes;
    }

    @Override
    public EfoDocDto getEfoDocDtoById(Long documentId) {
        return efoDocService.getEfoDocDtoById(documentId);
    }

    @Override
    public EfoDocDto setValueSaveDocument(DocumentSaveReq documentSaveReq) throws DetailException {
        Long documentId = documentSaveReq.getDocId();
        EfoDocDto efoDocDto = null;
        if (null != documentId) {
            efoDocDto = this.getEfoDocDtoById(documentId);

            if (null == efoDocDto) {
                throw new DetailException(AppApiExceptionCodeConstant.E4024404_APPAPI_DOCUMENT_NOT_FOUND);
            }

            this.convertDocDtoByRestSaveDto(documentSaveReq, efoDocDto);
        } else {
            efoDocDto = objectMapper.convertValue(documentSaveReq, EfoDocDto.class);
            this.setOrgAndPosMainByAcountId(efoDocDto);
        }

        return efoDocDto;
    }

    @Override
    public EfoDocDto setValueActionDocument(DocumentActionReq documentActionReq) throws DetailException {
        Long documentId = documentActionReq.getDocId();
        JpmProcessDeploy processDeployDto = jpmProcessDeployService
                .getJpmProcessDeployByProcessCode(documentActionReq.getProcessDeployCode());
        if (processDeployDto != null) {
            documentActionReq.setBusinessId(processDeployDto.getBusinessId());
            documentActionReq.setCompanyId(processDeployDto.getCompanyId());
            documentActionReq.setProcessDeployId(processDeployDto.getId());
            documentActionReq.setDocSummary(processDeployDto.getDescription());
            documentActionReq.setDocTitle(processDeployDto.getDescription());
            documentActionReq.setPriority("1");
            documentActionReq.setProcessType(3);
        }

        EfoDocDto efoDocDto = null;
        if (null != documentId) {
            efoDocDto = this.getEfoDocDtoById(documentId);

            if (null == efoDocDto) {
                throw new DetailException(AppApiExceptionCodeConstant.E4024404_APPAPI_DOCUMENT_NOT_FOUND);
            }

            this.convertOzDocDtoByRestActionDto(documentActionReq, efoDocDto);
        } else {
            efoDocDto = objectMapper.convertValue(documentActionReq, EfoDocDto.class);
            efoDocDto.setId(null);
            this.setOrgAndPosMainByAcountId(efoDocDto);
        }

        return efoDocDto;
    }

    private void setOrgAndPosMainByAcountId(EfoDocDto efoDocDto) {
        Long accountId = UserProfileUtils.getAccountId();
        efoDocDto.setOwnerOrgId(efoDocDto.getOrgId());

        JcaAccountOrgDto jcaAccountOrgDto = jcaAccountOrgService.getMainJcaAccountOrgDtoByAccountId(accountId);
        if (jcaAccountOrgDto != null) {
            Long posIds = jcaAccountOrgDto.getOrgId();
            Long orgIds = jcaAccountOrgDto.getPositionId();

            efoDocDto.setOwnerPositionId(posIds);
            efoDocDto.setSubmittedOrgId(orgIds);
            efoDocDto.setSubmittedPositionId(posIds);
        }

        efoDocDto.setOwnerId(accountId);
        efoDocDto.setSummittedId(accountId);
        efoDocDto.setSubmittedDate(CommonDateUtil.getSystemDate());

    }

    private EfoDocDto convertDocDtoByRestSaveDto(DocumentSaveReq rq, EfoDocDto objectDto) {
        objectDto.setDocSummary(rq.getDocSummary());
        objectDto.setPriority(rq.getPriority());
        objectDto.setDocTitle(rq.getDocTitle());
        // set value ozdFile
        // objectDto.setMainFileId(rq.getMainFileId());
        // objectDto.setFileStream(rq.getFileStream());
        objectDto.setProcessType(rq.getProcessType());
        objectDto.setBusinessId(rq.getBusinessId());
        // objectDto.setFormId(rq.getFormId());

        if (null == objectDto.getProcessDeployId()) {
            objectDto.setProcessDeployId(rq.getProcessDeployId());
        }
        return objectDto;
    }

    private EfoDocDto convertOzDocDtoByRestActionDto(DocumentActionReq rq, EfoDocDto objectDto) {
        // set value change
        objectDto.setButtonId(rq.getButtonId());
        objectDto.setCoreTaskId(rq.getCoreTaskId());
        objectDto.setNote(rq.getNote());
        objectDto.setDocInputJson(rq.getDocInputJson());

        this.convertDocDtoByRestSaveDto(rq, objectDto);
        return objectDto;
    }

    @Override
    public List<TaskSlaRes> getListSlaDocument(Long documentId) {
        List<TaskSlaRes> slaForTaskResList = new ArrayList<>();
        // sla hi task
        List<JpmHiTaskSlaDto> jpmHiTaskDtoList = jpmHiTaskSlaService.getJpmHiTaskSlaDtoListByDocId(documentId);
        if (CollectionUtils.isNotEmpty(jpmHiTaskDtoList)) {
            for (JpmHiTaskSlaDto jpmHiTaskDto : jpmHiTaskDtoList) {
                slaForTaskResList.add(this.hiTaskToSlaTask(jpmHiTaskDto));
            }
        }
        // sla task
        List<JpmTaskSlaDto> jpmTaskDtoList = jpmTaskSlaService.getJpmTaskSlaDtoListByDocId(documentId);
        if (CollectionUtils.isNotEmpty(jpmTaskDtoList)) {
            for (JpmTaskSlaDto jpmTaskDto : jpmTaskDtoList) {
                slaForTaskResList.add(this.taskToSlaTask(jpmTaskDto));
            }
        }

        return slaForTaskResList;
    }

    private TaskSlaRes taskToSlaTask(JpmTaskSlaDto jpmTaskDto) {
        TaskSlaRes result = new TaskSlaRes();
        if (null != jpmTaskDto) {
            result = objectMapper.convertValue(jpmTaskDto, TaskSlaRes.class);
            Long ownerOrgId = jpmTaskDto.getOwnerOrgId();
            JcaOrganization jcaOrganization = jcaOrganizationService.getJcaOrganizationById(ownerOrgId);
            if (null != jcaOrganization) {
                result.setOwnerOrgName(jcaOrganization.getName());
            }
            // calculate actualElapseTime
            Long slaConfigId = jpmTaskDto.getSlaConfigId();
            if (null != slaConfigId) {
                SlaConfig slaConfig = slaConfigService.findOne(slaConfigId);
                if (null != slaConfig) {
                    Date sysDate = commonService.getSystemDate();
                    SlaDateResultDto slaDateResultDto = slaCalculateService.calcElapsedMinutes(
                            jpmTaskDto.getPlanStartDate(), sysDate, jpmTaskDto.getPlanTotalTime().intValue(),
                            slaConfig.getCalendarTypeId());
                    result.setActualElapseTime(slaDateResultDto.getActualElapseTime());
                    List<CalendarTimeOffRes> calendarTaskSlaResList = this.getCalendarTaskSlaResList(sysDate, 1,
                            slaConfig.getCalendarTypeId());
                    result.setCalendarTaskSlaResList(calendarTaskSlaResList);
                }
            }
        }
        return result;
    }

    private TaskSlaRes hiTaskToSlaTask(JpmHiTaskSlaDto jpmHiTaskDto) {
        TaskSlaRes result = new TaskSlaRes();
        if (null != jpmHiTaskDto) {
            result = objectMapper.convertValue(jpmHiTaskDto, TaskSlaRes.class);
            Long ownerOrgId = jpmHiTaskDto.getOwnerOrgId();
            JcaOrganization jcaOrganization = jcaOrganizationService.getJcaOrganizationById(ownerOrgId);
            if (null != jcaOrganization) {
                result.setOwnerOrgName(jcaOrganization.getName());
            }
        }
        return result;
    }

    private List<CalendarTimeOffRes> getCalendarTaskSlaResList(Date startDate, int numDays, Long calendarTypeId) {
        List<CalendarTimeOffRes> calendarTaskSlaResList = new ArrayList<>();
        startDate = SlaDateUtils.truncate(startDate, Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, numDays);
        Date endDate = calendar.getTime();
        try {
            List<SlaCalendarDto> slaCalendarDtoList = slaCalendarService
                    .getSlaCalendarDtoListByRangeAndCalendarTypeId(startDate, endDate, calendarTypeId);
            if (CommonCollectionUtil.isNotEmpty(slaCalendarDtoList)) {
//                Map<Date, Collection<SlaCalendarDto>> mapSlaCalendarDto = slaCalendarDtoList.stream().collect(Collectors.groupingBy(SlaCalendarDto::getCalendarDate, HashMap::new, Collectors.toCollection(ArrayList::new)));
//                for (Entry<Date, Collection<SlaCalendarDto>> entryMap : mapSlaCalendarDto.entrySet()) {
//                    CalendarTaskSlaRes calendarTaskSlaRes = new CalendarTaskSlaRes();
//                    calendarTaskSlaRes.setCalendarDate(entryMap.getKey());
//                    List<CalendarTimeOffRes> calendarTimeOffList = entryMap.getValue().stream().map(m -> new CalendarTimeOffRes(m.getStartCalendar(), m.getEndCalendar(), m.getStartTime(), m.getEndTime())).collect(Collectors.toList());
//                    calendarTaskSlaRes.setCalendarTimeOffList(calendarTimeOffList);
//                    calendarTaskSlaResList.add(calendarTaskSlaRes);
//                }
                calendarTaskSlaResList = slaCalendarDtoList.stream()
                        .map(m -> new CalendarTimeOffRes(m.getStartCalendar(), m.getEndCalendar(), m.getStartTime(),
                                m.getEndTime()))
                        .collect(Collectors.toList());
            }
        } catch (DetailException e) {
            e.printStackTrace();
        }
        return calendarTaskSlaResList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentSaveReq save(DocumentSaveReq documentSaveReq) throws Exception {
        // 1. Save document

        JpmProcessDeploy processDeployDto = jpmProcessDeployService
                .getJpmProcessDeployByProcessCode(documentSaveReq.getProcessDeployCode());
        documentSaveReq.setBusinessId(processDeployDto.getBusinessId());
        documentSaveReq.setCompanyId(processDeployDto.getCompanyId());
        documentSaveReq.setProcessDeployId(processDeployDto.getId());
        documentSaveReq.setDocSummary(processDeployDto.getDescription());
        documentSaveReq.setDocTitle(processDeployDto.getDescription());
        documentSaveReq.setPriority("1");
        documentSaveReq.setProcessType(3);
        EfoDocDto efoDocDto = setValueSaveDocument(documentSaveReq);
        save(efoDocDto);

        // 2. Save for business
        documentSaveReq.setDocId(efoDocDto.getDocId());
        documentSaveReq.setFormId(efoDocDto.getFormId() != null ? efoDocDto.getFormId() : efoDocDto.getDocId());
        documentSaveReq.setDocCode(efoDocDto.getDocCode());

        documentSaveReq = saveBusiness(documentSaveReq, efoDocDto);

        return documentSaveReq;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentSaveReq saveBusiness(DocumentSaveReq documentSaveReq, EfoDocDto efoDocDto) throws Exception {
        Long documentId = efoDocDto.getDocId();
        if (null != documentId) {
            this.convertDocDtoByRestSaveDto(documentSaveReq, efoDocDto);
        } else {
            documentSaveReq = objectMapper.convertValue(documentSaveReq, DocumentSaveReq.class);
        }

        // Lưu dữ liệu của bảng liên quan
        return documentSaveReq;
    }

    @Override
    public DocumentActionReq loadData(Long documentId) {
        DocumentActionReq resulst = new DocumentActionReq();

        EfoDocDto efoDocDto = getEfoDocDtoById(documentId);
        ;

        if (null != documentId) {
            this.convertOzDocDtoByRestActionDto(resulst, efoDocDto);
        } else {
            resulst = objectMapper.convertValue(resulst, DocumentActionReq.class);
        }

        return resulst;
    }

    @Override
    public JpmButtonWrapper handleButtonByBusiness(DocumentActionReq data, JpmButtonWrapper jpmButtons) {
        return jpmButtons;
    }

    @Override
    public String setInputJsonForEmail(DocumentActionReq documentActionReq) {
        String reulst = documentActionReq.getDocInputJson();
        return reulst;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoDocDto actionNextStep(Long docId, String stepCode, String buttonText) throws Exception {
        DocumentActionReq documentActionReq = efoDocService.getDetailDocumentCurrentStep(docId, stepCode, buttonText);
        // 1. get efo doc
        if (ObjectUtils.isNotEmpty(documentActionReq)) {

            EfoDocDto efoDocDto = setValueActionDocument(documentActionReq);
            // 2. set param for email template
//            String jsonEmail = setInputJsonForEmail(documentActionReq);
//            efoDocDto.setInputJsonEmail(jsonEmail);
            // 3. action document
            actionSlaBusiness(documentActionReq, efoDocDto);
            action(efoDocDto);
            return efoDocDto;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createHistTask(Long docId, String buttonText, String note) {
        JpmTaskDto taskDto = jpmTaskService.getCurrentTaskByDocId(docId);
        Long jpmTaskId = sqlManagerService.getNextValBySeqName(WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_TASK);
        JpmHiTask hiTask = jpmHiTaskService.getJpmHiTaskById(taskDto.getId());
        JpmButtonDeployDto btnDto = jpmButtonDeployService
                .getJpmButtonDeployDtoByButtonTextAndProcessDeployId(buttonText, taskDto.getProcessDeployId());

        // Get info UserProfile
        Long userId = UserProfileUtils.getAccountId();
//		String coreUserId = String.valueOf(userId);

        Date sysDate = commonService.getSystemDate();
        // jpmTaskService.createTask(jpmTask);
        hiTask.setCompletedDate(sysDate);
        hiTask.setCompletedId(userId);
        hiTask.setCompletedOrgId(taskDto.getCompletedOrgId());
        hiTask.setCompletedPositionId(taskDto.getCompletedPositionId());

        hiTask.setActionId(btnDto.getButtonDeployId());

        // TASK_STATUS
//        Date dueDate = hiTask.getPlanDueDate();
//        if (dueDate != null && endDate != null) {
//            hiTask.setActualElapseTime(elapseTime);
//            hiTask.setActualStartDate(startDate);
//            hiTask.setActualEndDate(endDate);
//            if (endDate.after(dueDate)) {
//                hiTask.setTaskStatus(WorkflowConstant.EXPIRED_SLA);
//            } else {
//                hiTask.setTaskStatus(WorkflowConstant.NONE_EXPIRED_FIELD);
//            }
//        }

        // NOTE
        hiTask.setNote(note);
        hiTask.setId(jpmTaskId);
        jpmTaskService.completeJpmTask(hiTask);
    }

    @Override
    public DocumentActionReq actionList(DocumentActionReq documentActionReq, Locale locale) throws Exception {
        // TODO Auto-generated method stub
        List<String> lstStatus = new ArrayList<>();
        List<String> lstErrorBusiness = new ArrayList<>();
        for (Long docId : documentActionReq.getLstDocId()) {
            JpmTaskDto task = jpmTaskService.getCurrentTaskByDocId(docId);
            /**
             * condition
             */
            JpmButtonDeployDto btnDeploy = jpmButtonDeployService.getJpmButtonDeployDtoByButtonTextAndProcessDeployId(
                    documentActionReq.getButtonText(), task.getProcessDeployId());

            List<String> lstButtonCode = new ArrayList<>();
            lstButtonCode.add("btnReturn");
            lstButtonCode.add("btnReject");
            lstButtonCode.add("btnPending");
            int processType = documentActionReq.getProcessType();
            documentActionReq.setDocId(docId);
            documentActionReq.setButtonId(btnDeploy.getButtonDeployId());
            DocumentActionReq dto = getInfomationByDocId(documentActionReq);
            documentActionReq = (RecruitmentCandidateResultActionRes) dto;
            if (StringUtils.isNotEmpty(documentActionReq.getErrorBusiness())
                    && !lstButtonCode.contains(btnDeploy.getButtonCode())
                    && task.getStepDeployCode().equalsIgnoreCase("waitingApprove")) {
                lstErrorBusiness.add(documentActionReq.getErrorBusiness());
                documentActionReq.setNextStep(false);
            } else {
                documentActionReq.setErrorBusiness(null);
            }
            documentActionReq.setApproveList(true);
            documentActionReq.setDocId(docId);
            documentActionReq.setProcessType(processType);
            documentActionReq.setCoreTaskId(task.getCoreTaskId());
            action(documentActionReq, locale);
            lstStatus.add(documentActionReq.getOrder());
        }
        String status = StringUtils.join(lstStatus, ",");
        documentActionReq.setOrder(status);
        documentActionReq.setLstErrorBusiness(lstErrorBusiness);
        return documentActionReq;
    }

    @Override
    public DocumentActionReq getInfomationByDocId(DocumentActionReq documentActionReq) throws Exception {

        // Lưu dữ liệu của bảng liên quan
        return documentActionReq;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DocumentAppRes detailHistory(Long documentId) throws Exception {
        DocumentAppRes res = new DocumentAppRes();
//		DocumentDataResultDto efoOzDocDto = null;
        try {
            // get process history
            List<JpmHiTaskDto> listProcessHistory = new ArrayList<>();
            listProcessHistory = this.getListProcessHistoryDocument(documentId);
            res.setListProcessHistory(listProcessHistory);

            // get document history
            List<EfoOzDocMainFileVersionDto> listOzDocVersion = new ArrayList<>();
            listOzDocVersion = efoOzDocMainFileVersionService.getListEfoOzDocMainFileVersionDtoByOzDocId(documentId);
            res.setListOzDocVersion(listOzDocVersion);

        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024403_APPAPI_DOCUMENT_ACTION_ERROR);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentActionReq saveDataBussiness(DocumentActionReq documentActionReq) throws Exception {
        // 1. get efo doc
        EfoDocDto efoDocDto = setValueActionDocument(documentActionReq);
        efoDocDto.setProcessStatusCodePrev(efoDocDto.getProcessStatusCode());
        // 4. action business
        DocumentActionReq dto = actionSaveBusiness(documentActionReq, efoDocDto);

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentActionReq actionSaveBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto)
            throws Exception {
        Long documentId = efoDocDto.getDocId();
        if (null != documentId) {
            this.convertOzDocDtoByRestActionDto(documentActionReq, efoDocDto);
        } else {
            documentActionReq = objectMapper.convertValue(documentActionReq, DocumentActionReq.class);
            // this.setOrgAndPosMainByAcountId(efoDocDto);
        }

        // Lưu dữ liệu của bảng liên quan
        return documentActionReq;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void actionSlaBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto) throws Exception {
        Long documentId = efoDocDto.getDocId();
        if (null != documentId) {
            this.convertOzDocDtoByRestActionDto(documentActionReq, efoDocDto);
        } else {
            documentActionReq = objectMapper.convertValue(documentActionReq, DocumentActionReq.class);
            // this.setOrgAndPosMainByAcountId(efoDocDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMailProcess(DocumentActionReq abstractProcessDto, Integer nextStepNo, String nextStatusCode,
            Integer curStepNo, AccountDetailDto accountAction, HttpServletRequest httpServletRequest, Locale locale)
            throws Exception {
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentActionReq actionSkipStep(DocumentActionReq documentActionReq, String stepCode, String buttonText, Locale locale ) throws Exception {
        // 1. get efo doc
        EfoDocDto efoDocDto = setValueActionDocument(documentActionReq);
        efoDocDto.setProcessStatusCodePrev(efoDocDto.getProcessStatusCode());

        if (efoDocDto.getId() == null) {
            efoDocDto = setValueSaveDocument(documentActionReq);
            efoDocDto.setCompanyId(UserProfileUtils.getCompanyId());
            efoDocDto = save(efoDocDto);
            documentActionReq.setDocId(efoDocDto.getDocId());
            DocumentAppRes doc = detail(efoDocDto.getDocId());
            if (doc != null && !doc.getJpmButtons().isEmpty()) {
            	JpmStepDeployDto jpmStepDeployDto = jpmStepDeployService.getJpmStepDeployDtoByProcessDeployIdAndStepCode(efoDocDto.getProcessDeployId(),
            			stepCode);
            	JpmButtonDeployDto btnDto = jpmButtonDeployService
        				.getJpmButtonDeployDtoByButtonTextAndProcessDeployId(buttonText, efoDocDto.getProcessDeployId());
                if (ObjectUtils.isNotEmpty(btnDto)) {
                    documentActionReq.setButtonId(btnDto.getButtonDeployId());
                    documentActionReq.setStepId(jpmStepDeployDto.getStepDeployId());
                }
            }
        }

        // 2. set param for email template
        String jsonEmail = setInputJsonForEmail(documentActionReq);
        efoDocDto.setInputJsonEmail(jsonEmail);

        // 3. action document
        if (documentActionReq.isNextStep()) {
            action(efoDocDto);
        }

        return documentActionReq;
    }
}