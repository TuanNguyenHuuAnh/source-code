/*******************************************************************************
 * Class        ：WorkflowRepositoryServiceImpl
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：tantm
 * Change log   ：2020/11/30：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.core.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.dmn.api.DmnDeployment;
import org.activiti.dmn.api.DmnRepositoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.workflow.core.WorkflowRepositoryService;

/**
 * WorkflowRepositoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActivitiRepositoryServiceImpl implements WorkflowRepositoryService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

//    @Autowired
//    private DmnRepositoryService dmnRepositoryService;

    @Override
    public String getProcessDefinitionKeyById(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
                .singleResult();
        if (processDefinition == null) {
            return null;
        }

        return processDefinition.getKey();
    }

    @Override
    public String deployBpmn(byte[] contentFile, String deploymentName, String categoryName, String tenantId, String filePathBpmn) {
        Deployment deployment = repositoryService.createDeployment()
                .name(deploymentName)
                .addInputStream(filePathBpmn, new ByteArrayInputStream(contentFile))
                .tenantId(tenantId)
                .category(categoryName)
                .deploy();

        return deployment.getId();
    }

    @Override
    public String getWorkflowDiagramByProcessDefinitionId(String processDefinitionId) throws IOException {
        InputStream in = repositoryService.getProcessDiagram(processDefinitionId);
        String res = CommonConstant.EMPTY;
        try {
            res = CommonBase64Util.encode(IOUtils.toByteArray(in));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            in.close();
        }
        return res;
    }

    @Override
    public String getWorkflowDiagramByBusinessKey(String businessKey) throws IOException {
        String res = null;
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        if (Objects.nonNull(pi)) {
            String processInstanceId = pi.getProcessInstanceId();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
            InputStream in = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, "png",
                    runtimeService.getActiveActivityIds(processInstanceId));
            try {
                res = CommonBase64Util.encode(IOUtils.toByteArray(in));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                in.close();
            }
        }
        return res;
    }

    @Override
    public String deployDmn(byte[] contentFile, String categoryName, String tenantId, String filePathBpmn,
            String parentDeploymentId) {
//        DmnDeployment deployment = dmnRepositoryService.createDeployment()
//                .addInputStream(filePathBpmn, new ByteArrayInputStream(contentFile))
//                .tenantId(tenantId)
//                .category(categoryName)
//                .deploy();

        return null;
    }

    @Override
    public String getProcessDefinitionIdByDeploymentId(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        return null != processDefinition ? processDefinition.getId() : null;
    }

}
