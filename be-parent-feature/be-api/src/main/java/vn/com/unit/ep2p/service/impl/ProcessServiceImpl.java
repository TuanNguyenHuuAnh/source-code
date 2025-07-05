/*******************************************************************************
 * Class        ：ProcessServiceImpl
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.ProcessInfoReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.ProcessAddReq;
import vn.com.unit.ep2p.dto.req.ProcessImportReq;
import vn.com.unit.ep2p.dto.req.ProcessUpdateReq;
import vn.com.unit.ep2p.service.ProcessService;
import vn.com.unit.ep2p.service.RoleForProcessService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.core.WorkflowRepositoryService;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.JpmProcessSearchDto;
import vn.com.unit.workflow.entity.JpmProcess;
import vn.com.unit.workflow.entity.JpmProcessDeploy;
import vn.com.unit.workflow.service.JpmBusinessService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmProcessService;
import vn.com.unit.workflow.service.JpmSlaInfoService;

/**
 * <p>
 * ProcessServiceImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessServiceImpl extends AbstractCommonService implements ProcessService {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JpmProcessService jpmProcessService;

    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    @Autowired
    private JpmBusinessService jpmBusinessService;

    @Autowired
    private FileStorageService fileStorageService;

//    @Autowired
//    private JcaItemService jcaItemService;

//    @Autowired
//    private WorkflowDiagramService workflowDiagramService;

    @Autowired
    private WorkflowRepositoryService workflowRepositoryService;

    @Autowired
    private JpmSlaInfoService jpmSlaInfoService;

//    @Autowired
//    private WorkflowIdentityService workflowIdentityService;

    @Autowired
    private RoleForProcessService roleForProcessService;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.ProcessService#create(vn.com.unit.mbal.api.req.dto.ProcessAddReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProcessAddReq reqProcessAddDto) throws DetailException {
        Long processId = null;
        try {
            JpmProcessDto processDto = convertReqObjToJpmObj(reqProcessAddDto);

            // validate
            String errorCode = this.validate(processDto);
            if (CommonStringUtil.isNotBlank(errorCode)) {
                throw new DetailException(errorCode, true);
            }

            this.save(processDto);
            processId = processDto.getProcessId();
        } catch (DetailException de) {
            throw de;
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021401_APPAPI_PROCESS_ADD_ERROR, true);
        }
        return processId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.ProcessService#update(vn.com.unit.mbal.api.req.dto.ProcessUpdateReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcessUpdateReq reqProcessUpdateDto) throws DetailException {
        try {
            Long processId = reqProcessUpdateDto.getProcessId();
            if (null == processId) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
            }

            JpmProcessDto processDto = convertReqObjToJpmObj(reqProcessUpdateDto);
            processDto.setProcessId(processId);
            this.save(processDto);
        } catch (DetailException de) {
            throw de;
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021402_APPAPI_PROCESS_UPDATE_INFO_ERROR, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long processId) throws DetailException {
        try {
            if (!jpmProcessService.deleteById(processId)) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
            }
        } catch (DetailException e) {
            throw e;
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021403_APPAPI_PROCESS_DELETE_ERROR, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
     */
    @Override
    public JpmProcessDto detail(Long processId) throws DetailException {
        JpmProcessDto processDto = jpmProcessService.getJpmProcessDtoById(processId);
        if (Objects.isNull(processDto)) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
        }
        return processDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#save(java.lang.Object)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcessDto save(JpmProcessDto objectDto) throws DetailException {
        try {
            String processFileString = objectDto.getFileBpmn();
            if (CommonStringUtil.isNotBlank(processFileString)) {
                Long businessId = objectDto.getBusinessId();
                JpmBusinessDto businessDto = jpmBusinessService.getJpmBusinessDtoById(businessId);
                String businessCode = businessDto.getBusinessCode();
                objectDto.setBusinessCode(businessCode);

                this.uploadFile(objectDto);
            }

            jpmProcessService.saveJpmProcessDto(objectDto);
        } catch (DetailException de) {
            throw de;
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021402_APPAPI_PROCESS_UPDATE_INFO_ERROR, true);
        }
        return objectDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.ProcessService#deploy(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long deploy(Long processId, Long oldProcessDeployId, boolean cloneSlaFlag, boolean cloneRoleFlag) throws DetailException {
        Long processDeployId = null;
        try {

            // update version
            jpmProcessService.updateVersionForProcess(processId, true);

            // get process
            JpmProcessDto processDto = jpmProcessService.getJpmProcessDtoById(processId);

            if (Objects.isNull(processDto)) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
            }

            // save entity process deploy for get id to next Step
            JpmProcessDeploy processDeploy = objectMapper.convertValue(processDto, JpmProcessDeploy.class);
            jpmProcessDeployService.saveJpmProcessDeploy(processDeploy);
            processDeployId = processDeploy.getId();

            JpmProcessDeployDto processDeployDto = objectMapper.convertValue(processDto, JpmProcessDeployDto.class);
            processDeployDto.setProcessDeployId(processDeployId);

            // gen function code
//            List<JpmPermissionDeployDto> permissionDeployDtos = processDeployDto.getPermissions();
//            Map<Long, JpmPermissionDeployDto> permissionMap = new HashMap<>();
//            if (CommonCollectionUtil.isNotEmpty(permissionDeployDtos)) {
//                List<JcaItemDto> items = this.buildItemDtosByPermissionDtos(processDeployDto);
//
//                permissionMap = permissionDeployDtos.stream()
//                        .collect(Collectors.toMap(JpmPermissionDeployDto::getPermissionId, permission -> permission));
//
//                // save items
//                items.stream().filter(i -> !CommonConstant.EMPTY.equals(i.getFunctionCode())).forEach(item -> {
////                    jcaItemService.saveJcaItemDto(item);
//
//                    // create group by item for process engine
//                    workflowIdentityService.createGroup(item.getFunctionCode(), item.getFunctionName());
//                });
//            }

            // get code from permission set to each step of workflow
//            List<JpmStepDeployDto> stepDeployDtos = processDeployDto.getSteps();
//            if (CommonCollectionUtil.isNotEmpty(stepDeployDtos)) {
//                for (JpmStepDeployDto stepDeployDto : stepDeployDtos) {
//                    Set<String> permissionCodes = new HashSet<>();
//                    List<JpmButtonForStepDeployDto> buttonForStepDeployDtos = stepDeployDto.getButtonForStepDtos();
//                    if (CommonCollectionUtil.isNotEmpty(buttonForStepDeployDtos)) {
//                        for (JpmButtonForStepDeployDto buttonForStepDeployDto : buttonForStepDeployDtos) {
//                            JpmPermissionDeployDto permissionDeployDto = permissionMap
//                                    .getOrDefault(buttonForStepDeployDto.getPermissionId(), new JpmPermissionDeployDto());
//                            String permissionCode = permissionDeployDto.getDeployCode();
//                            String permissionType = permissionDeployDto.getPermissionType();
//                            if (PermissionType.GROUP.getValue().equalsIgnoreCase(permissionType)) {
//                                permissionCodes.add(permissionCode);
//                            }
//                        }
//                    }
//                    stepDeployDto.setPermissionCodes(permissionCodes.stream().collect(Collectors.toList()));
//                }
//            }

            // get file and deploy to process engine
            String filePathBpmn = processDeployDto.getBpmnFilePath();
            if (CommonStringUtil.isNotBlank(filePathBpmn)) {
                FileDownloadParam fileDownloadParam = new FileDownloadParam();
                fileDownloadParam.setFilePath(filePathBpmn);
                fileDownloadParam.setRepositoryId(processDeployDto.getBpmnRepoId());

                FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
                byte[] bpmnContent = fileDownloadResult.getFileByteArray();
//                bpmnContent = workflowDiagramService.updateCandidateForProcessInfo(bpmnContent, stepDeployDtos);
                Long companyId = processDeployDto.getCompanyId();
                String companyCode = CommonStringUtil.leftPad(String.valueOf(companyId), 5, "0");// TODO KhuongTH hard
                String processCategory = processDeployDto.getBusinessCode();
                String processCode = processDeployDto.getProcessCode();
                String processKey = companyCode.concat(CommonConstant.UNDERSCORE).concat(processCode);
                Date sysDate = CommonDateUtil.getSystemDateTime();
                String sysDateStr = CommonDateUtil.formatDateToString(sysDate, CommonDateUtil.YYYYMMDDHHMMSS);
                // Define deploymentName = tenantId_processCode_YYYYMMDDHHMMSS
                String deploymentName = processKey.concat(CommonConstant.UNDERSCORE).concat(sysDateStr);
                String deploymentId = workflowRepositoryService.deployBpmn(bpmnContent, deploymentName, processCategory, companyCode,
                        filePathBpmn);
                String processDefinitionId = workflowRepositoryService.getProcessDefinitionIdByDeploymentId(deploymentId);
                processDeployDto.setProcessDefinitionId(processDefinitionId);
            }

            // save all data
            jpmProcessDeployService.saveJpmProcessDeployDto(processDeployDto);

            if (cloneSlaFlag && Objects.nonNull(oldProcessDeployId)) {
                jpmSlaInfoService.cloneJpmSla(oldProcessDeployId, processDeployId);
            }

            if (cloneRoleFlag && Objects.nonNull(oldProcessDeployId)) {
                // clone role
                roleForProcessService.cloneRoleForProcess(oldProcessDeployId, processDeployId);
            }

        } catch (DetailException de) {
            throw de;
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021407_APPAPI_PROCESS_DEPLOY_ERROR, true);
        }
        return processDeployId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.ProcessService#importProcess(vn.com.unit.mbal.api.req.dto.ProcessImportReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importProcess(ProcessImportReq reqProcessImportDto) throws DetailException {
        Long companyId = reqProcessImportDto.getCompanyId();
        String importFile = reqProcessImportDto.getImportFile();

        JpmProcessImportExportDto processImportDto;
        Long processId = null;
        try {
            // byte[] contentBytes = importFile.getBytes();
            String contentFile = CommonBase64Util.decode(importFile);
            processImportDto = CommonJsonUtil.convertJSONToObject(contentFile, JpmProcessImportExportDto.class);

            // reset companyId
            processImportDto.setCompanyId(companyId);

            // upload file
            JpmProcessDto processDto = new JpmProcessDto();
            processDto.setFileBpmn(processImportDto.getFileBpmn());
            processDto.setBpmnFileName(processImportDto.getBpmnFileName());
            processDto.setCompanyId(companyId);
            processDto.setProcessCode(processImportDto.getProcessCode());
            processDto.setBusinessCode(processImportDto.getBusinessDto().getBusinessCode());

            // validate
            boolean override = reqProcessImportDto.isOverride();
            if (!override) {
                String errorCode = this.validate(processDto);
                if (CommonStringUtil.isNotBlank(errorCode)) {
                    throw new DetailException(errorCode, true);
                }
            }

            this.uploadFile(processDto);
            processImportDto.setBpmnFilePath(processDto.getBpmnFilePath());
            processImportDto.setBpmnRepoId(processDto.getBpmnRepoId());

            processId = jpmProcessService.importProcess(processImportDto);
            if (Objects.isNull(processId)) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021401_APPAPI_PROCESS_ADD_ERROR, true);
            }
        } catch (DetailException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Exception ", e);
            throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
        }

        return processId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<JpmProcessDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<JpmProcessDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JpmProcess.class, JpmProcessService.TABLE_ALIAS_JPM_PROCESS);

            /** init param search repository */
            JpmProcessSearchDto searchDto = this.buildJpmProcessSearchDto(commonSearch);

            int totalData = jpmProcessService.countBySearchCondition(searchDto);
            List<JpmProcessDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jpmProcessService.getProcessDtosByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021400_APPAPI_PROCESS_LIST_ERROR);
        }
        return resObj;
    }

    /** === PRIVATE === */

    /**
     * <p>
     * Convert req obj to jpm obj.
     * </p>
     *
     * @param reqProcessDto
     *            type {@link ProcessInfoReq}
     * @return {@link JpmProcessDto}
     * @author KhuongTH
     */
    private JpmProcessDto convertReqObjToJpmObj(ProcessInfoReq reqProcessDto) {
        JpmProcessDto processDto = objectMapper.convertValue(reqProcessDto, JpmProcessDto.class);
        processDto.setFileBpmn(reqProcessDto.getBpmnContent());

        String effectiveDateStr = reqProcessDto.getEffectiveDate();
        if (CommonStringUtil.isNotBlank(effectiveDateStr)) {
            processDto.setEffectiveDate(CommonDateUtil.parseDate(effectiveDateStr, CommonDateUtil.YYYYMMDDHHMMSS));
        }

        return processDto;
    }

    /**
     * <p>
     * Builds the jca account search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaAccountSearchDto}
     * @author taitt
     */
    private JpmProcessSearchDto buildJpmProcessSearchDto(MultiValueMap<String, String> commonSearch) {
        JpmProcessSearchDto processSearchDto = new JpmProcessSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Long businessId = null != commonSearch.getFirst("businessId") ? Long.valueOf(commonSearch.getFirst("businessId")) : null;

        processSearchDto.setCompanyId(companyId);
        processSearchDto.setBusinessId(businessId);
        processSearchDto.setKeySearch(keySearch);
        processSearchDto.setProcessCode(CommonStringUtil.isNotBlank(keySearch) ? keySearch.trim() : null);
        processSearchDto.setProcessName(CommonStringUtil.isNotBlank(keySearch) ? keySearch.trim() : null);
        return processSearchDto;
    }

    /**
     * <p>
     * Builds the item dtos by permission dtos. and set deploy code
     * </p>
     *
     * @param processDeployDto
     *            type {@link JpmProcessDeployDto}
     * @return {@link Map<Long,JcaItemDto>}
     * @author KhuongTH
     */
//    private List<JcaItemDto> buildItemDtosByPermissionDtos(JpmProcessDeployDto processDeployDto) {
//        List<JcaItemDto> result = new ArrayList<>();
//
//        Long businessId = processDeployDto.getBusinessId();
//        Long processDeployId = processDeployDto.getProcessDeployId();
//        Long companyId = processDeployDto.getCompanyId();
//
//        // is authority freeform
//        boolean isAuthority = true;
//        JpmBusinessDto business = jpmBusinessService.getJpmBusinessDtoById(businessId);
//        if (Objects.nonNull(business)) {
//            Integer processType = business.getProcessType();
////            if (ProcessType.FREE.getValue().equalsIgnoreCase(processType)) {
////                isAuthority = business.isAuthority();
////            }
//            String businessCode = business.getBusinessCode();
//            processDeployDto.setBusinessCode(businessCode);
//            processDeployDto.setProcessType(processType);
//        }
//
//        List<JpmPermissionDeployDto> permissionDeployDtos = processDeployDto.getPermissions();
//        if (CommonCollectionUtil.isNotEmpty(permissionDeployDtos)) {
//            int displayOrder = 1;
//            for (JpmPermissionDeployDto permissionDeployDto : permissionDeployDtos) {
//                String permissionCode = permissionDeployDto.getPermissionCode();
//                String permissionName = permissionDeployDto.getPermissionName();
//                JcaItemDto itemDto = new JcaItemDto();
////                permissionDeployDto.setDeployCode(deployCode);
//
//                itemDto.setCompanyId(companyId);
////                itemDto.setFunctionCode(deployCode);
//                itemDto.setFunctionType(FunctionType.PROCESS.getValue());
//                itemDto.setFunctionName(permissionName);
//                itemDto.setDisplayOrder(displayOrder);
//                itemDto.setCompanyId(companyId);
//
//                result.add(itemDto);
//                displayOrder++;
//            }
//        }
//
//        return result;
//    }

    /**
     * Upload file. update process info to bpmn file and upfile to repository
     *
     * @param processDto
     *            the process dto
     * @throws Exception
     *             the exception
     */
    private void uploadFile(JpmProcessDto processDto) throws Exception {
        if (CommonStringUtil.isNotBlank(processDto.getFileBpmn())) {
            byte[] contentFile = CommonBase64Util.decodeToByte(processDto.getFileBpmn());
            contentFile = jpmProcessService.updateProcessInfo(new ByteArrayInputStream(contentFile), processDto);
            String fileNameBpmn = processDto.getBpmnFileName();

            // upload file
            FileUploadParamDto param = new FileUploadParamDto();
            param.setFileByteArray(contentFile);
            param.setFileName(fileNameBpmn);
            param.setTypeRule(2);
            param.setSubFilePath("/bpmn");// TODO hardcode
            param.setCompanyId(processDto.getCompanyId());

            Date sysDate = CommonDateUtil.getSystemDateTime();
            String sysDateStr = CommonDateUtil.formatDateToString(sysDate, CommonDateUtil.YYYYMMDDHHMMSS);
            // rename file append sysDate
            String rename = CommonFileUtil.getBaseName(fileNameBpmn).concat(CommonConstant.UNDERSCORE).concat(sysDateStr);
            param.setRename(rename);
            param.setRepositoryId(5L);// TODO
            FileUploadResultDto uploadResultDto = fileStorageService.upload(param);

            String filePath = uploadResultDto.getFilePath();
            Long repoId = uploadResultDto.getRepositoryId();

            processDto.setBpmnRepoId(repoId);
            processDto.setBpmnFilePath(filePath);
        }
    }

    private String validate(JpmProcessDto jpmProcessDto) {
        String res = null;

        Long companyId = jpmProcessDto.getCompanyId();
        String processCode = jpmProcessDto.getProcessCode();
        JpmProcessDto validateObj = jpmProcessService.getProcessDtoByCodeAndCompanyId(processCode, companyId);
        if (Objects.nonNull(validateObj)) {
            res = AppApiExceptionCodeConstant.E4021406_APPAPI_PROCESS_ALREADY_EXISTS;
        }

        return res;
    }
}
