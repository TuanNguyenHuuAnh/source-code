/*******************************************************************************
 * Class        ：ActivitiDiagramServiceImpl
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：KhuongTH
 * Change log   ：2021/01/13：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.core.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.UserTask;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.workflow.activiti.utils.BpmnUtil;
import vn.com.unit.workflow.core.WorkflowDiagramService;
import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.enumdef.DocumentState;
import vn.com.unit.workflow.enumdef.StepKind;
import vn.com.unit.workflow.enumdef.StepType;

/**
 * <p>
 * ActivitiDiagramServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActivitiDiagramServiceImpl implements WorkflowDiagramService {

    @Override
    public List<JpmStepDto> buildStepDtosByProcessDiagram(InputStream fileStream) {
        List<JpmStepDto> stepDtos = new ArrayList<>();
        try {
            BpmnModel bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(fileStream);
            Process process = bpmnModel.getMainProcess();

            Collection<FlowElement> processFlowElements = process.getFlowElements();
            List<UserTask> userTasks = this.getUserTasks(processFlowElements);

            for (UserTask userTask : userTasks) {
                String stepCode = userTask.getId();
                String stepName = userTask.getName();

                JpmStepDto stepDto = new JpmStepDto();
                stepDto.setStepCode(stepCode);
                stepDto.setStepName(stepName);
                stepDto.setStepType(StepType.NORMAL_STEP.getValue());
                stepDto.setCommonStatusCode(DocumentState.IN_PROGRESS.toString());
               
                // set to sort
                stepDto.setStepKind(this.getStatusCodeByStepCode(stepCode));
                stepDtos.add(stepDto);
            }

            if (CommonCollectionUtil.isNotEmpty(stepDtos)) {
                Long stepNo = 1L;
                stepDtos = stepDtos.stream().sorted(Comparator.comparing(JpmStepDto::getStepKind)).collect(Collectors.toList());
                for (JpmStepDto stepDto : stepDtos) {
                    stepDto.setStepNo(stepNo++);
                    stepDto.setStepKind(StepKind.NORMAL.getValue());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stepDtos;
    }

    @Override
    public List<JpmParamDto> buildParamDtosByProcessDiagram(InputStream fileStream) {
        List<JpmParamDto> paramDtos = new ArrayList<>();
        try {
            BpmnModel bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(fileStream);
            Process process = bpmnModel.getMainProcess();

            List<ValuedDataObject> dataObjects = process.getDataObjects();

            // param form dataobject
            for (ValuedDataObject dataObject : dataObjects) {
                String param = dataObject.getId();
                String type = dataObject.getType();
                param = param.trim();
                JpmParamDto paramDto = new JpmParamDto();
                paramDto.setFieldName(param);
                paramDto.setDataType(type);
                paramDtos.add(paramDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramDtos;
    }

    private List<UserTask> getUserTasks(Collection<FlowElement> flowElements) {
        List<UserTask> result = new ArrayList<>();

        for (FlowElement el : flowElements) {
            if (el instanceof UserTask) {
                result.add((UserTask) el);
            } else if (el instanceof SubProcess) {
                SubProcess sub = (SubProcess) el;

                Collection<FlowElement> subFlowElement = sub.getFlowElements();
                List<UserTask> subUserTask = getUserTasks(subFlowElement);
                result.addAll(subUserTask);
            }
        }

        return result;
    }

    @Override
    public String getStatusCodeByStepCode(String stepCode) {
        int underscoreLastIndex = CommonStringUtil.lastIndexOf(stepCode, CommonConstant.UNDERSCORE);
        return CommonStringUtil.substring(stepCode, underscoreLastIndex + 1);
    }

    @Override
    public byte[] updateProcessInfo(InputStream fileStream, String processKey, String processCategory) {
        byte[] resBytes = null;
        try {
            BpmnModel bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(fileStream);
            bpmnModel.setTargetNamespace(processCategory);

            List<Pool> pools = bpmnModel.getPools();
            if (CommonCollectionUtil.isNotEmpty(pools)) {
                int index = 1;
                for (Pool pool : pools) {
                    String processRef = pool.getProcessRef();

                    String processIdNew = "P".concat(processKey).concat(String.valueOf(index));
                    pool.setProcessRef(processIdNew);

                    Process process = bpmnModel.getProcessById(processRef);
                    process.setId(processIdNew);
                }
            } else {
                Process process = bpmnModel.getMainProcess();
                process.setId("P".concat(processKey));
            }

            Process mainProcess = bpmnModel.getMainProcess();

            Collection<FlowElement> processFlowElements = mainProcess.getFlowElements();
            List<UserTask> userTasks = this.getUserTasks(processFlowElements);

            for (UserTask userTask : userTasks) {
                List<ActivitiListener> activitiListeners = userTask.getTaskListeners();
                if (CommonCollectionUtil.isEmpty(activitiListeners)) {

                    // init Listener create
                    ActivitiListener createEvent = new ActivitiListener();
                    createEvent.setEvent("create");
                    createEvent.setImplementation("${docWorkflowListener.onCreateTask(execution, task)}");
                    createEvent.setImplementationType("expression");
                    activitiListeners.add(createEvent);

                    // init Listener complete
                    ActivitiListener completeEvent = new ActivitiListener();
                    completeEvent.setEvent("complete");
                    completeEvent.setImplementation("${docWorkflowListener.onCompleteTask(execution, task)}");
                    completeEvent.setImplementationType("expression");
                    activitiListeners.add(completeEvent);

                    userTask.setTaskListeners(activitiListeners);
                }
            }

            for (FlowElement el : processFlowElements) {
                if (el instanceof EndEvent && CommonStringUtil.endsWith(el.getId(), "999")) {
                    List<ActivitiListener> executionListeners = el.getExecutionListeners();
                    if (CommonCollectionUtil.isEmpty(executionListeners)) {
                        // init Listener start
                        ActivitiListener startEvent = new ActivitiListener();
                        startEvent.setEvent("start");
                        startEvent.setImplementation("${docWorkflowListener.onComplete(execution)}");
                        startEvent.setImplementationType("expression");

                        executionListeners.add(startEvent);
                        el.setExecutionListeners(executionListeners);
                    }
                }
            }

            BpmnXMLConverter converter = new BpmnXMLConverter();
            String xml = new String(converter.convertToXML(bpmnModel), CommonConstant.UTF_8);
            resBytes = xml.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resBytes;
    }

    @Override
    public byte[] updateCandidateForProcessInfo(byte[] contentFile, List<JpmStepDeployDto> stepDeployDtos) {
        byte[] resBytes = null;
        try (InputStream fileStream = new ByteArrayInputStream(contentFile)) {

            BpmnModel bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(fileStream);

            Map<String, List<String>> permissionCodesMap = stepDeployDtos.stream()
                    .collect(Collectors.toMap(JpmStepDeployDto::getStepCode, JpmStepDeployDto::getPermissionCodes));

            Process process = bpmnModel.getMainProcess();
            if (process == null) {
                process = bpmnModel.getProcesses().get(0);
            }
            Collection<FlowElement> processFlowElements = process.getFlowElements();
            List<UserTask> userTasks = this.getUserTasks(processFlowElements);
            for (UserTask userTask : userTasks) {
                String taskIdStr = userTask.getId();

                List<String> candidateGroups = permissionCodesMap.get(taskIdStr);
                if (CommonCollectionUtil.isNotEmpty(candidateGroups)) {
                    userTask.setCandidateGroups(candidateGroups);
                }
            }

            BpmnXMLConverter converter = new BpmnXMLConverter();
            String xml = new String(converter.convertToXML(bpmnModel), CommonConstant.UTF_8);
            resBytes = xml.getBytes(CommonConstant.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resBytes;
    }

    @Override
    public String getDiagramFromProcessFile(byte[] contentFile) {
        String res = null;
        try (InputStream fileStream = new ByteArrayInputStream(contentFile)) {

            BpmnModel bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(fileStream);
            try (InputStream in = new DefaultProcessDiagramGenerator().generatePngDiagram(bpmnModel)) {
                res = CommonBase64Util.encode(IOUtils.toByteArray(in));
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return res;
    }

}
