/*******************************************************************************
 * Class        ：ProcessRest
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：KhuongTH
 * Change log   ：2020/11/11：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.web;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.entity.JpmButtonDeploy;
import vn.com.unit.workflow.entity.JpmProcessDeploy;
import vn.com.unit.workflow.entity.JpmStatusDeploy;
import vn.com.unit.workflow.entity.JpmStepDeploy;
import vn.com.unit.workflow.enumdef.StepKind;
import vn.com.unit.workflow.enumdef.StepType;
import vn.com.unit.workflow.service.JpmBusinessService;
import vn.com.unit.workflow.service.JpmButtonDeployService;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmStatusDeployService;
import vn.com.unit.workflow.service.JpmStepDeployService;

/**
 * ProcessRest
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@RestController
@RequestMapping("/process")
public class ProcessRest1 extends AbstractRest{

    @Autowired
    private JpmBusinessService businessService;

    @Autowired
    private JpmProcessDeployService processDeployService;
    
    @Autowired
    private JpmButtonDeployService buttonDeployService;
    
    @Autowired
    private JpmStepDeployService stepDeployService;
    
    @Autowired
    private JpmButtonForStepDeployService buttonForStepDeployService;
    
    @Autowired
    private JpmStatusDeployService statusDeployService;

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/init")
    public DtsApiResponse index() throws JsonProcessingException {
        Long companyId = 1L;
        Long createdId = 0L;

        JpmBusinessDto businessDto = new JpmBusinessDto();
        businessDto.setCompanyId(companyId);
        businessDto.setBusinessCode("SIMPLE_BUSINESS");
        businessDto.setBusinessName("Simple Business");
        businessDto.setActived(true);
        businessDto.setAuthority(true);
        JpmBusiness business = businessService.saveJpmBusinessDto(businessDto);
        
        String fileName = "bpmn/ProcessSimpleTest.bpmn20.xml";
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }

        File file;
        String processDefinitionId = null;
        try {
            file = new File(resource.toURI());
            Deployment deployment = repositoryService.createDeployment().name("ProcessSimpleTest").tenantId("UNIT")
                    .addInputStream(file.getPath(), new FileInputStream(file))
                    .category("SIMPLE").deploy();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId()).singleResult();
            processDefinitionId = processDefinition.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JpmProcessDeployDto processDeployDto = new JpmProcessDeployDto();
        processDeployDto.setProcessCode("SIMPLE_PROCESS");
        processDeployDto.setProcessName("Simple Process");
        processDeployDto.setActived(true);
        processDeployDto.setCompanyId(companyId);
        processDeployDto.setProcessId(createdId);
        processDeployDto.setBusinessId(business.getId());
        processDeployDto.setProcessDefinitionId(processDefinitionId);
        processDeployDto.setEffectiveDate(CommonDateUtil.getSystemDateTime());
        JpmProcessDeploy processDeploy = processDeployService.saveJpmProcessDeployDto(processDeployDto);
        Long processDeployId = processDeploy.getId();
        
        JpmButtonDeployDto approveButton = new JpmButtonDeployDto();
        approveButton.setButtonId(createdId);
        approveButton.setButtonCode("approve");
        approveButton.setButtonValue("1");
        approveButton.setButtonType("APPROVE");
        approveButton.setButtonText("APPROVE");
        approveButton.setProcessDeployId(processDeployId); 
        JpmButtonDeploy approveButtonDeploy = buttonDeployService.saveJpmButtonDeployDto(approveButton);
        Long buttonId = approveButtonDeploy.getId();
        
        // status 1
        JpmStatusDeployDto status1Dto = new JpmStatusDeployDto();
        status1Dto.setProcessDeployId(processDeployId);
        status1Dto.setStatusCode("101");
        status1Dto.setStatusName("Submit Document");
        status1Dto.setStatusId(1L);
        JpmStatusDeploy status1 = statusDeployService.saveJpmStatusDeployDto(status1Dto);
        Long status1Id = status1.getId();
        
        // status 2
        JpmStatusDeployDto status2Dto = new JpmStatusDeployDto();
        status2Dto.setProcessDeployId(processDeployId);
        status2Dto.setStatusCode("102");
        status2Dto.setStatusName("Approval Management");
        status2Dto.setStatusId(1L);
        JpmStatusDeploy status2 = statusDeployService.saveJpmStatusDeployDto(status2Dto);
        Long status2Id = status2.getId();
        
        // status 1
        JpmStatusDeployDto status3Dto = new JpmStatusDeployDto();
        status3Dto.setProcessDeployId(processDeployId);
        status3Dto.setStatusCode("103");
        status3Dto.setStatusName("Approval BOD");
        status3Dto.setStatusId(1L);
        JpmStatusDeploy status3 = statusDeployService.saveJpmStatusDeployDto(status3Dto);
        Long status3Id = status3.getId();
        
        // step 1
        JpmStepDeployDto step1Dto = new JpmStepDeployDto();
        step1Dto.setCommonStatusCode("997");
        step1Dto.setStepNo(1L);
        step1Dto.setProcessDeployId(processDeployId);
        step1Dto.setStepCode("submitDocument_101");
        step1Dto.setStepName("Submit Document");
        step1Dto.setStatusDeployId(status1Id);
        step1Dto.setUseClaimButton(false);
        step1Dto.setStepKind(StepKind.NORMAL.getValue());
        step1Dto.setStepType(StepType.NORMAL_STEP.getValue());
        step1Dto.setStepId(1L);
        JpmStepDeploy step1 = stepDeployService.saveJpmStepDeployDto(step1Dto);
        Long step1Id = step1.getId();
        
        // step 2
        JpmStepDeployDto step2Dto = new JpmStepDeployDto();
        step2Dto.setCommonStatusCode("997");
        step2Dto.setStepNo(2L);
        step2Dto.setProcessDeployId(processDeployId);
        step2Dto.setStepCode("approvalManagement_102");
        step2Dto.setStepName("Approval Management");
        step2Dto.setStatusDeployId(status2Id);
        step2Dto.setUseClaimButton(false);
        step2Dto.setStepKind(StepKind.NORMAL.getValue());
        step2Dto.setStepType(StepType.NORMAL_STEP.toString());
        step2Dto.setStepId(1L);
        JpmStepDeploy step2 = stepDeployService.saveJpmStepDeployDto(step2Dto);
        Long step2Id = step2.getId();
        
        // step 3
        JpmStepDeployDto step3Dto = new JpmStepDeployDto();
        step3Dto.setCommonStatusCode("997");
        step3Dto.setStepNo(3L);
        step3Dto.setProcessDeployId(processDeployId);
        step3Dto.setStepCode("approvalBOD_103");
        step3Dto.setStepName("Approval BOD");
        step3Dto.setStatusDeployId(status3Id);
        step3Dto.setUseClaimButton(false);
        step3Dto.setStepKind(StepKind.NORMAL.getValue());
        step3Dto.setStepType(StepType.NORMAL_STEP.toString());
        step3Dto.setStepId(1L);
        JpmStepDeploy step3 = stepDeployService.saveJpmStepDeployDto(step3Dto);
        Long step3Id = step3.getId();
        
        JpmButtonForStepDeployDto buttonForStep1 = new JpmButtonForStepDeployDto();
        buttonForStep1.setButtonDeployId(buttonId);
        buttonForStep1.setPermissionDeployId(createdId);
        buttonForStep1.setOptionShowHistory(true);
        buttonForStep1.setStepDeployId(step1Id);
        buttonForStepDeployService.saveJpmButtonForStepDeployDto(buttonForStep1);
        
        JpmButtonForStepDeployDto buttonForStep2 = new JpmButtonForStepDeployDto();
        buttonForStep2.setButtonDeployId(buttonId);
        buttonForStep2.setPermissionDeployId(createdId);
        buttonForStep2.setOptionShowHistory(true);
        buttonForStep2.setStepDeployId(step2Id);
        buttonForStepDeployService.saveJpmButtonForStepDeployDto(buttonForStep2);
        
        JpmButtonForStepDeployDto buttonForStep3 = new JpmButtonForStepDeployDto();
        buttonForStep3.setButtonDeployId(buttonId);
        buttonForStep3.setPermissionDeployId(createdId);
        buttonForStep3.setOptionShowHistory(true);
        buttonForStep3.setStepDeployId(step3Id);
        buttonForStepDeployService.saveJpmButtonForStepDeployDto(buttonForStep3);

        long start = System.currentTimeMillis();
        return this.successHandler.handlerSuccess("Welcome to mbal EnterPrise Api ", start);
    }

}
