/*******************************************************************************
 * Class        JpmProcessServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhoaNA
 * Change log   2016/06/21 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.UserTask;
import org.activiti.bpmn.model.ValuedDataObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.dto.AppProcessDto;
import vn.com.unit.process.workflow.dto.AppProcessSearchDto;
import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.process.workflow.dto.AppStepDto;
import vn.com.unit.process.workflow.enumdef.JpmProcessSearchEnum;
import vn.com.unit.process.workflow.repository.AppProcessRepository;
import vn.com.unit.process.workflow.repository.AppStatusSystemDefaultRepository;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.process.workflow.service.AppProcessDmnService;
import vn.com.unit.process.workflow.service.AppProcessService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.activiti.utils.BpmnUtil;
import vn.com.unit.workflow.core.WorkflowRepositoryService;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.dto.JpmPermissionDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.JpmProcessSearchDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.entity.JpmProcess;
import vn.com.unit.workflow.entity.JpmProcessDeploy;
import vn.com.unit.workflow.entity.JpmStatusDefault;
import vn.com.unit.workflow.enumdef.StepKind;
import vn.com.unit.workflow.service.JpmAuthorityService;
import vn.com.unit.workflow.service.JpmBusinessService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmProcessService;
import vn.com.unit.workflow.service.JpmSlaInfoService;

/**
 * JpmProcessServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppProcessServiceImpl implements AppProcessService, AbstractCommonService {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private AppProcessRepository appProcessRepository;


    @Autowired
    private JCommonService commonService;

    @Autowired
    private AppBusinessService appBusinessService;


    @Autowired
    private CompanyService companyService;

    @Autowired
    private AppStatusSystemDefaultRepository appStatusSystemDefaultRepository;

    @Autowired
    private JpmProcessService jpmProcessService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    @Autowired
    private WorkflowRepositoryService workflowRepositoryService;

    @Autowired
    private JpmSlaInfoService jpmSlaInfoService;

    @Autowired
    private JpmAuthorityService jpmAuthorityService;
    
    @Autowired
    private JpmBusinessService jpmBusinessService;
    
    @Autowired
    private AppProcessDmnService appProcessDmnService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
 	private static final String UNKNOWN_STATUS_CODE = "unknown";
 	private static final String BEGIN_PARAM_SYMBOL = "p_";
 	
    @Override
    public PageWrapper<AppProcessDto> search(int page, int pageSize, AppProcessSearchDto searchDto) throws DetailException {
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<AppProcessDto> pageWrapper = new PageWrapper<>(page, sizeOfPage, listPageSize);
        if (null == searchDto) {
            searchDto = new AppProcessSearchDto();
        }

        //searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
        //searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        
        Pageable pageable = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JpmBusiness.class, JpmProcessService.TABLE_ALIAS_JPM_PROCESS);

        JpmProcessSearchDto reqSearch = this.buildJpmProcessSearchDto(searchDto);
        
        int count = jpmProcessService.countBySearchCondition(reqSearch);
        List<AppProcessDto> result = new ArrayList<>();
        if (count > 0) {
            List<JpmProcessDto> processDtos = jpmProcessService.getProcessDtosByCondition(reqSearch, pageable);
            result = processDtos.stream().map(this::convertJpmProcessDtoToAppProcessDto).collect(Collectors.toList());
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }
    
    private JpmProcessSearchDto buildJpmProcessSearchDto(AppProcessSearchDto searchDto) {
        JpmProcessSearchDto processSearchDto = new JpmProcessSearchDto();

        String keySearch = searchDto.getFieldSearch();
        Long companyId = Long.valueOf(0).equals(searchDto.getCompanyId()) ? null : searchDto.getCompanyId();
        Long businessId = null;

        processSearchDto.setCompanyId(companyId);
        processSearchDto.setBusinessId(businessId);
        processSearchDto.setKeySearch(keySearch);
        if (CommonCollectionUtil.isNotEmpty(searchDto.getFieldValues())) {
            for (String field : searchDto.getFieldValues()) {
                switch (JpmProcessSearchEnum.valueOf(field)) {
                case CODE:
                    processSearchDto.setProcessCode(CommonStringUtil.trimToNull(keySearch));
                    break;
                case NAME:
                    processSearchDto.setProcessName(CommonStringUtil.trimToNull(keySearch));
                    break;
                default:
                    processSearchDto.setProcessCode(CommonStringUtil.trimToNull(keySearch));
                    processSearchDto.setProcessName(CommonStringUtil.trimToNull(keySearch));
                    break;
                }
            }
        }else {
            processSearchDto.setProcessCode(CommonStringUtil.trimToNull(keySearch));
            processSearchDto.setProcessName(CommonStringUtil.trimToNull(keySearch));
        }
        return processSearchDto;
    }

    @Override
    public AppProcessDto getAppProcessDtoById(Long id) {
        JpmProcessDto processDto = jpmProcessService.getJpmProcessDtoByProcessId(id);
    	return this.convertJpmProcessDtoToAppProcessDto(processDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcess saveJpmProcessDtoWithAutoVersion(AppProcessDto objectDto, boolean isMajor) {
        JpmProcessDto processDto = this.convertAppProcessDtoToJpmProcessDto(objectDto);

        // upload file
        if (CommonStringUtil.isNotBlank(processDto.getFileBpmn())) {
            Long businessId = processDto.getBusinessId();
            AppBusinessDto businessDto = appBusinessService.getAppBusinessDtoById(businessId);
            String businessCode = businessDto.getCode();
            processDto.setBusinessCode(businessCode);

            this.uploadFile(processDto);
        }
        
//        String processType = objectDto.getProcessType();
    	JpmProcess jpmProcess = jpmProcessService.saveJpmProcessDto(processDto);
    	Long processId = processDto.getProcessId();
    	objectDto.setId(processId);

		return jpmProcess;
	}

    @Override
    public AppProcessDto getJpmProcessByCodeAndCompanyId(String code, Long companyId) {
        return appProcessRepository.findOneJpmProcessByCodeAndCompanyId(code, companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletedById(Long id) {
        return jpmProcessService.deleteById(id);
    }

	@Override
	public List<AppProcessDto> findJpmProcessDtoByBusinessId(Long businessId) {
		return appProcessRepository.findJpmProcessDtoByBusinessId(businessId);
	}
    
    
    
    /**
     * Create List<AppStepDto>, Set<AppStatusDto> by ProcessDefinition
     * Return Map<String,List<AppStepDto>> with key is statusCode. If statusCode is null or empty, it is set to "unknown" value.
     * And build defaul system step, status (reject, finish);
     * 
     * @param bpmnModel
     * 			type BpmnModel
     * @param jpmStatusDtoList
     * 			type Set<AppStatusDto>
     * @param jpmParamDtoList
     * 			type Set<String>
     * @param buildDefault
     * 			type boolean
     * @return Map<String,List<AppStepDto>>
     */
    private Map<String,List<AppStepDto>> buildJpmStepDtoListByProcessDefinitionId(BpmnModel bpmnModel
            , Set<AppStatusDto> jpmStatusDtoList
            , Set<String> jpmParamDtoList
            , boolean buildDefault) {
    	Map<String,List<AppStepDto>> jpmStepDtoMap = new HashMap<>();
    	
        Process process = bpmnModel.getMainProcess();
        
        if( process == null ) {
        	process = bpmnModel.getProcesses().get(0);
        }
        
        Collection<FlowElement> processFlowElements = process.getFlowElements();
        Collection<FlowElement> flowElements = this.getUserTaskList(processFlowElements);
        Collection<FlowElement> sequenceFlows = this.getSequenceFlowList(processFlowElements);
        Collection<ValuedDataObject> dataObjects = process.getDataObjects();
        
        int stepNo = 1;
        for (FlowElement sl : sequenceFlows) {
            if ( sl instanceof SequenceFlow ) {
                SequenceFlow sequenceFlow = (SequenceFlow) sl;
                String param = sequenceFlow.getConditionExpression();
                if(null != param){
                    int indexParam = param.indexOf(BEGIN_PARAM_SYMBOL);
                    if(indexParam != -1){
                        param = param.substring(indexParam);
                        param = param.substring(0, param.indexOf(ConstantCore.SPACE));
                        
                        jpmParamDtoList.add(param);
                    }
                }
            }
        }

        // param form dataobject
        for (ValuedDataObject dataObject : dataObjects) {
            String param = dataObject.getId();
            param = param.trim();
            jpmParamDtoList.add(param);
        }
        
        for (FlowElement el : flowElements) {
            if ( el instanceof UserTask ) {
                UserTask userTask = (UserTask) el;
                String taskIdStr = userTask.getId();
                String statusCode = getStatusCodeByTaskId(taskIdStr);
                String name = userTask.getName();
                
                // Create processStep
                AppStepDto appStepDto = new AppStepDto();
                appStepDto.setStepNo(Long.valueOf(stepNo));
                appStepDto.setCode(taskIdStr);
                appStepDto.setStepType(ConstantCore.STEP_TYPE_USER_TASK);
                appStepDto.setName(name);
                appStepDto.setStepKind(StepKind.NORMAL.getValue());
                
                List<AppStepDto> jpmStepDtoList = null;
                // Create processStatus
                if( !StringUtils.isEmpty(statusCode) ) {
                	AppStatusDto appStatusDto = new AppStatusDto();
                    appStatusDto.setStatusCode(statusCode);
                    appStatusDto.setStatusName(name);
                    jpmStatusDtoList.add(appStatusDto);
                    
                    jpmStepDtoList = jpmStepDtoMap.get(statusCode);
                } else {
                	statusCode = UNKNOWN_STATUS_CODE;
                	jpmStepDtoList = jpmStepDtoMap.get(statusCode);
                }
                
                if( jpmStepDtoList == null ) {
                	jpmStepDtoList = new ArrayList<>();
                }
                
                jpmStepDtoList.add(appStepDto);
                jpmStepDtoMap.put(statusCode, jpmStepDtoList);
                
                stepNo++;
            }
        }

        //step default system
        if(buildDefault){
            Iterable<JpmStatusDefault> listStatusDefault = appStatusSystemDefaultRepository.findAll();
            for(JpmStatusDefault status : listStatusDefault ){
                AppStatusDto statusDto = objectMapper.convertValue(status, AppStatusDto.class);
                statusDto.setId(null);
                statusDto.setIsSystem(true);
                jpmStatusDtoList.add(statusDto);
                
                AppStepDto appStepDto = new AppStepDto();
                stepNo = Integer.valueOf(status.getStatusCode());
                String stepName = status.getStatusName();
                String stepCode = stepName.concat(ConstantCore.UNDERSCORE).concat(String.valueOf(stepNo));
                
                appStepDto.setStepNo(Long.valueOf(stepNo));
                appStepDto.setCode(stepCode);
                appStepDto.setName(stepName);
                appStepDto.setStepType(ConstantCore.STEP_TYPE_END_EVENT);
                appStepDto.setStepKind(StepKind.NORMAL.getValue());
    
                List<AppStepDto> jpmStepDtoList = null;
                jpmStepDtoList = jpmStepDtoMap.get(String.valueOf(stepNo));
                
                if( jpmStepDtoList == null ) {
                    jpmStepDtoList = new ArrayList<>();
                }
                
                jpmStepDtoList.add(appStepDto);
                jpmStepDtoMap.put(String.valueOf(stepNo), jpmStepDtoList);
            }
        }
        
        return jpmStepDtoMap;
    }
    
    /**
     * Get UserTask list from Collection<FlowElement>
     * 
     * @param flowElements
     * 			type FlowElement
     * @return List<FlowElement
     * @author KhoaNA
     */
    private List<FlowElement> getUserTaskList(Collection<FlowElement> flowElements) {
    	List<FlowElement> result = new ArrayList<>();
    	
        for (FlowElement el : flowElements) {
            if ( el instanceof UserTask ) {
            	result.add(el);
            } else if( el instanceof SubProcess ) {
            	SubProcess sub = (SubProcess) el;
            	
            	Collection<FlowElement> subFlowElement = sub.getFlowElements();
            	
            	List<FlowElement> subUserTask = getUserTaskList(subFlowElement);
            	result.addAll(subUserTask);
            }
        }
    	
    	return result;
    }
    
    /**
     * Return str last index of taskIdStr that it is after the last underscore.
     * 
     * @param taskIdStr
     * 			type String
     * @return String
     * @author KhoaNA
     */
    public String getStatusCodeByTaskId(String taskIdStr) {
    	String result = ConstantCore.EMPTY;
    	
    	int underscoreLastIndex = taskIdStr.lastIndexOf(ConstantCore.UNDERSCORE);
    	// skip it
    	underscoreLastIndex = underscoreLastIndex + 1;
    	if( underscoreLastIndex < taskIdStr.length() ) {
    		result = taskIdStr.substring(underscoreLastIndex);
    	}
 
    	return result;
    }

    @Transactional(rollbackFor = Exception.class)
    private Long deploy(Long processId, Long oldProcessDeployId, boolean cloneSlaFlag, boolean cloneRoleFlag) throws Exception {
        Long processDeployId = null;

        // update version
        jpmProcessService.updateVersionForProcess(processId, true);

        // get process
        JpmProcessDto processDto = jpmProcessService.getJpmProcessDtoById(processId);

        if (Objects.isNull(processDto)) {
            return null;
        }

        // save entity process deploy for get id to next Step
        JpmProcessDeploy processDeploy = objectMapper.convertValue(processDto, JpmProcessDeploy.class);
        jpmProcessDeployService.saveJpmProcessDeploy(processDeploy);
        processDeployId = processDeploy.getId();

        JpmProcessDeployDto processDeployDto = objectMapper.convertValue(processDto, JpmProcessDeployDto.class);
        processDeployDto.setProcessDeployId(processDeployId);

        // gen function code
        List<JpmPermissionDeployDto> permissionDeployDtos = processDeployDto.getPermissions();
//        Map<Long, JpmPermissionDeployDto> permissionMap = new HashMap<>();
        if (CommonCollectionUtil.isNotEmpty(permissionDeployDtos)) {
//            List<JcaItemDto> items = this.buildItemDtosByPermissionDtos(processDeployDto);
//
//            permissionMap = permissionDeployDtos.stream()
//                    .collect(Collectors.toMap(JpmPermissionDeployDto::getPermissionId, permission -> permission));
//
            // save items
//            items.stream().filter(i -> !CommonConstant.EMPTY.equals(i.getFunctionCode())).forEach(item -> {
//                jcaItemService.saveJcaItemDto(item);
//
//                // create group by item for process engine
//                workflowIdentityService.createGroup(item.getFunctionCode(), item.getFunctionName());
//            });
        }

        // get code from permission set to each step of workflow
        List<JpmStepDeployDto> stepDeployDtos = processDeployDto.getSteps();
        if (CommonCollectionUtil.isNotEmpty(stepDeployDtos)) {
            for (JpmStepDeployDto stepDeployDto : stepDeployDtos) {
                Set<String> permissionCodes = new HashSet<>();
                List<JpmButtonForStepDeployDto> buttonForStepDeployDtos = stepDeployDto.getButtonForStepDtos();
                if (CommonCollectionUtil.isNotEmpty(buttonForStepDeployDtos)) {
//                    for (JpmButtonForStepDeployDto buttonForStepDeployDto : buttonForStepDeployDtos) {
////                        JpmPermissionDeployDto permissionDeployDto = permissionMap.getOrDefault(buttonForStepDeployDto.getPermissionId(),
////                                new JpmPermissionDeployDto());
////                        String permissionCode = permissionDeployDto.getDeployCode();
////                        String permissionType = permissionDeployDto.getPermissionType();
////                        if (PermissionType.GROUP.getValue().equalsIgnoreCase(permissionType)) {
////                            permissionCodes.add(permissionCode);
////                        }
//                    }
                }
                stepDeployDto.setPermissionCodes(permissionCodes.stream().collect(Collectors.toList()));
            }
        }

        // get file and deploy to process engine
        String filePathBpmn = processDeployDto.getBpmnFilePath();
        if (CommonStringUtil.isNotBlank(filePathBpmn)) {
            FileDownloadParam fileDownloadParam = new FileDownloadParam();
            fileDownloadParam.setFilePath(filePathBpmn);
            fileDownloadParam.setRepositoryId(processDeployDto.getBpmnRepoId());

            FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
            byte[] bpmnContent = fileDownloadResult.getFileByteArray();
//            bpmnContent = workflowDiagramService.updateCandidateForProcessInfo(bpmnContent, stepDeployDtos);
            Long companyId = processDeployDto.getCompanyId();
            String companyCode = companyService.getSystemCodeByCompanyId(companyId);
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
            
            appProcessDmnService.deployDmn(processId, processDeployId, deploymentId);
        }

        // save all data
        jpmProcessDeployService.saveJpmProcessDeployDto(processDeployDto);

        // clone SLA
        if (cloneSlaFlag && Objects.nonNull(oldProcessDeployId)) {
            jpmSlaInfoService.cloneJpmSla(oldProcessDeployId, processDeployId);
        }
        
        // clone role
        if (cloneRoleFlag && Objects.nonNull(oldProcessDeployId)) {
            jpmAuthorityService.cloneRoleForProcess(oldProcessDeployId, processDeployId);
        }
        return processDeployId;
    }
    
    
	@Override
	public AppProcessDto getByFormId(Long businessId,Long processId) {
		return appProcessRepository.getJpmProcessByBusinessId(businessId,processId);
	}

@Override
@Transactional(rollbackFor = Exception.class)
public Long saveAndDeployJpmProcessDtoWithAutoVersion(AppProcessDto objectDto, boolean isMajor) throws Exception {
    this.saveJpmProcessDtoWithAutoVersion(objectDto, isMajor);
    Long processId = objectDto.getId();
    boolean isCloneRole = objectDto.getIsCloneRole();
    boolean isCloneSLA = objectDto.getIsCloneSLA();
    Long processDeployOldId = objectDto.getProcessDeployOldId();

    Long processDeployId = this.deploy(processId, processDeployOldId, isCloneSLA, isCloneRole);

    return processDeployId;
}

	@Override
	public List<Select2Dto> getSelect2DtoListBusinessId(Long businessId) {
		List<AppProcessDto> list = appProcessRepository.findJpmProcessDtoByBusinessId(businessId);
		List<Select2Dto> resList = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(list)) {
			for (AppProcessDto jpmBusinessDto : list) {
				Long id = jpmBusinessDto.getId();
				String name = jpmBusinessDto.getName();
//				String code = jpmBusinessDto.getCode();
				
				Select2Dto item = new Select2Dto(String.valueOf(id), name, name);
				resList.add(item);
			}
		}
		return resList;
	}

	@Override
	public AppProcessDto getJpmProcessByNameAndCompanyId(String name, Long companyId) {
        return appProcessRepository.findOneJpmProcessByNameAndCompanyId(name, companyId);
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public Long cloneJpmProcess(AppProcessDto appProcessDto) throws Exception {
        Long oldProcessId = appProcessDto.getId();
        Long companyId = appProcessDto.getCompanyId();
        Long businessId = appProcessDto.getBusinessId();
        String processName = appProcessDto.getName();
        String processCode = appProcessDto.getCode();
        
        JpmBusinessDto businessDto = jpmBusinessService.getJpmBusinessDtoById(businessId);
        
        JpmProcessDto oldProcessDto = jpmProcessService.getJpmProcessDtoById(oldProcessId);
        JpmProcessImportExportDto processImportExportDto = objectMapper.convertValue(oldProcessDto, JpmProcessImportExportDto.class);
        processImportExportDto.setBusinessDto(businessDto);
        processImportExportDto.setProcessName(processName);
        processImportExportDto.setProcessCode(processCode);
        processImportExportDto.setCompanyId(companyId);
        
        // get file
        FileDownloadParam fileDownloadParam = new FileDownloadParam();
        fileDownloadParam.setFilePath(processImportExportDto.getBpmnFilePath());
        fileDownloadParam.setRepositoryId(processImportExportDto.getBpmnRepoId());

        FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
        String bpmnFile = new String(fileDownloadResult.getFileByteArray());
        processImportExportDto.setFileBpmn(CommonBase64Util.encode(bpmnFile));
	    return this.importJpmProcess(processImportExportDto);
	}		
	
	@Override
	public Map<String, AppStatusDto> getStatusListByBpmnFile(InputStream fileStream){
	    Map<String, AppStatusDto> resMap = new HashMap<>();
	    
	    Set<AppStatusDto> jpmStatusDtoSet = new HashSet<>();
	    Set<String> jpmParamDtoSet = new HashSet<>();
	    BpmnModel bpmnModel;
        try {
            bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(fileStream);
            this.buildJpmStepDtoListByProcessDefinitionId(bpmnModel, jpmStatusDtoSet, jpmParamDtoSet, false);
        } catch (UnsupportedEncodingException | XMLStreamException e) {
            e.printStackTrace();
        }
        
        for(AppStatusDto status : jpmStatusDtoSet){
            resMap.put(status.getStatusCode(), status);
        }
        
	    return resMap;
	}

    /**
     * @author KhuongTH
     * @throws Exception 
     */
    @Override
    public Long importJpmProcess(JpmProcessImportExportDto processImportDto) throws Exception {
        Long processId = null;
        // upload file
        JpmProcessDto processDto = new JpmProcessDto();
        processDto.setFileBpmn(processImportDto.getFileBpmn());
        processDto.setBpmnFileName(processImportDto.getBpmnFileName());
        processDto.setCompanyId(processImportDto.getCompanyId());
        processDto.setProcessCode(processImportDto.getProcessCode());
        processDto.setBusinessCode(processImportDto.getBusinessDto().getBusinessCode());

        this.uploadFile(processDto);
        processImportDto.setBpmnFilePath(processDto.getBpmnFilePath());
        processImportDto.setBpmnRepoId(processDto.getBpmnRepoId());

        processId = jpmProcessService.importProcess(processImportDto);
        return processId;
    }

    /**
     * getSequenceFlowList
     * @param flowElements
     * @return List<FlowElement>
     * @author KhuongTH
     */
    private List<FlowElement> getSequenceFlowList(Collection<FlowElement> flowElements) {
        List<FlowElement> result = new ArrayList<>();
        
        for (FlowElement el : flowElements) {
            if ( el instanceof SequenceFlow ) {
                result.add(el);
            } else if( el instanceof SubProcess ) {
                SubProcess sub = (SubProcess) el;
                
                Collection<FlowElement> subFlowElement = sub.getFlowElements();
                
                List<FlowElement> subSequenceFlow = getSequenceFlowList(subFlowElement);
                result.addAll(subSequenceFlow);
            }
        }
        
        return result;
    }

    private void uploadFile(JpmProcessDto processDto){
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
            String repoIdStr = systemConfig.getConfig(SystemConfig.REPO_UPLOADED_MAIN, processDto.getCompanyId());
            Long repoId = Long.parseLong(repoIdStr);
            // rename file append sysDate
            String rename = CommonFileUtil.getBaseName(fileNameBpmn).concat(CommonConstant.UNDERSCORE).concat(sysDateStr);
            param.setRename(rename);
            param.setRepositoryId(repoId);
            FileUploadResultDto uploadResultDto;
            try {
                uploadResultDto = fileStorageService.upload(param);

                String filePath = uploadResultDto.getFilePath();

                processDto.setBpmnRepoId(repoId);
                processDto.setBpmnFilePath(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JCommonService getCommonService() {
        return commonService;
    }
    
    private AppProcessDto convertJpmProcessDtoToAppProcessDto(JpmProcessDto processDto) {
        if (processDto == null)
            return null;
        AppProcessDto appProcessDto = objectMapper.convertValue(processDto, AppProcessDto.class);
        appProcessDto.setId(processDto.getProcessId());
        appProcessDto.setCode(processDto.getProcessCode());
        appProcessDto.setName(processDto.getProcessName());
        appProcessDto.setIsShowWorkflow(processDto.isShowWorkflow());
        appProcessDto.setIsDeployed(processDto.isDeployed());
        appProcessDto.setjRepositoryId(processDto.getBpmnRepoId());
        appProcessDto.setFileNameBpmn(processDto.getBpmnFileName());
        appProcessDto.setFilePathBpmn(processDto.getBpmnFilePath());

        return appProcessDto;
    }
    
    private JpmProcessDto convertAppProcessDtoToJpmProcessDto(AppProcessDto appProcessDto) {
        if (appProcessDto == null)
            return null;
        JpmProcessDto processDto = new JpmProcessDto();
        processDto.setProcessId(appProcessDto.getId());
        processDto.setProcessCode(appProcessDto.getCode());
        processDto.setProcessName(appProcessDto.getName());
        processDto.setShowWorkflow(appProcessDto.getIsShowWorkflow());
        processDto.setDeployed(appProcessDto.getIsDeployed());
        processDto.setProcessLangs(appProcessDto.getProcessLangs());
        processDto.setProcessType(appProcessDto.getProcessType());
        processDto.setDescription(appProcessDto.getDescription());
        processDto.setCompanyId(appProcessDto.getCompanyId());
        processDto.setEffectiveDate(appProcessDto.getEffectiveDate());
        processDto.setBusinessId(appProcessDto.getBusinessId());
        processDto.setBpmnFileName(appProcessDto.getFileNameBpmn());
        processDto.setActived(true);

        if (Objects.nonNull(appProcessDto.getFileProcess())) {
            try {
                String fileBpmn = CommonBase64Util.encode(appProcessDto.getFileProcess().getBytes());
                processDto.setFileBpmn(fileBpmn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return processDto;
    }
}
