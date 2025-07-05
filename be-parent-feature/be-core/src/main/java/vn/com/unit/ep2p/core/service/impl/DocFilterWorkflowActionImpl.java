/*******************************************************************************
 * Class        ：DocFilterWorkflowActionImpl
 * Created date ：2021/01/14
 * Lasted date  ：2021/01/14
 * Author       ：taitt
 * Change log   ：2021/01/14：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.AbstractDocumentFilterDto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.enumdef.ReferenceType;
import vn.com.unit.core.service.DocumentFilterActionService;
import vn.com.unit.core.service.JcaAccountOrgService;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterIn;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterOut;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterRef;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocFilterInService;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocFilterOutService;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocFilterRefService;
import vn.com.unit.workflow.enumdef.StepCodeCommon;
import vn.com.unit.workflow.service.JpmTaskAssigneeService;

/**
 * DocFilterWorkflowActionImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocFilterWorkflowActionImpl implements DocumentFilterActionService{

    @Autowired
    private EfoOzDocFilterRefService efoOzDocFilterRefService;
    
    @Autowired
    private EfoOzDocFilterInService efoOzDocFilterInService;
    
    @Autowired
    private EfoOzDocFilterOutService efoOzDocFilterOutService;
    
    @Autowired
    private JpmTaskAssigneeService jpmTaskAssigneeService;
    
    @Autowired
    private JcaAccountOrgService jcaAccountOrgService;
    
    
    @Autowired
    private ObjectMapper objectMapper;



	@Override
	public <T extends AbstractDocumentFilterDto> void submitDocFilter(T docFilterActionDto) {

		// Step 1: create document filter "IN" and "OUT"
		EfoOzDocFilterIn efoOzDocFilterIn = objectMapper.convertValue(docFilterActionDto, EfoOzDocFilterIn.class);
		efoOzDocFilterInService.saveDocFilterIn(efoOzDocFilterIn);
		
		EfoOzDocFilterOut efoOzDocFilterOut = objectMapper.convertValue(docFilterActionDto, EfoOzDocFilterOut.class);
		efoOzDocFilterOutService.saveDocFilterOut(efoOzDocFilterOut);
		
		// Step2: complete document filter with this.completeDocFilter
	}



	@Override
	public <T extends AbstractDocumentFilterDto> void completeDocFilter(T docFilterActionDto,Long taskNewId) {
		Date sysDate = CommonDateUtil.getSystemDateTime();
		String processStatusCode = docFilterActionDto.getProcessStatusCode();
		
		Long taskId = docFilterActionDto.getTaskId();
		Long docId = docFilterActionDto.getDocId();
		
		// Step 1: remove document filter "IN"
		Long completeId = docFilterActionDto.getAssigneeId();
		efoOzDocFilterInService.deleteRefInByJpmTaskId(taskId,docId);

		// Step 2: move accIds without process to "REF"
		List<Long> acctRefTask = jpmTaskAssigneeService.getAccIdsByTaskId(taskId);
		List<Long> acctRefTaskWithoutAct =  acctRefTask.stream().filter(item -> !completeId.toString().equals(item.toString())).collect(Collectors.toList());
		EfoOzDocFilterRef efoOzDocFilterRef = new EfoOzDocFilterRef(); 
		
		efoOzDocFilterRef.setTaskId(taskId);
		efoOzDocFilterRef.setDocId(docId);
		efoOzDocFilterRef.setRelatedType(ReferenceType.REF_JOIN.toString());
		
		acctRefTaskWithoutAct.stream().map(item -> {
			
			JcaAccountOrgDto jcaAccountOrg = jcaAccountOrgService.getMainJcaAccountOrgDtoByAccountId(item);
			Long orgId = jcaAccountOrg.getOrgId();
			Long posId = jcaAccountOrg.getPositionId();
			
			efoOzDocFilterRef.setRelatedOrgId(orgId);
			efoOzDocFilterRef.setRelatedId(item);
			efoOzDocFilterRef.setRelatedPositionId(posId);
			
			efoOzDocFilterRefService.saveDocFilterRef(efoOzDocFilterRef);
			
			return efoOzDocFilterRef;
		}).collect(Collectors.toList());
		
		// Step 3: update "OUT"
		EfoOzDocFilterOut efoOzDocFilterOut = efoOzDocFilterOutService.getEfoOzDocFilterOutByTaskId(taskId);
		JcaAccountOrgDto jcaAccountOrg = jcaAccountOrgService.getMainJcaAccountOrgDtoByAccountId(completeId);

        if (jcaAccountOrg != null) {
            efoOzDocFilterOut.setCompletedOrgId(jcaAccountOrg.getOrgId());
            efoOzDocFilterOut.setCompletedPositionId(jcaAccountOrg.getPositionId());
        }
		
		efoOzDocFilterOut.setCompletedDate(sysDate);
		efoOzDocFilterOut.setCompletedId(completeId);
		
		efoOzDocFilterOutService.saveDocFilterOut(efoOzDocFilterOut);
		
		if (null != taskNewId && (!StepCodeCommon.FINISHED.toString().equals(processStatusCode) || !StepCodeCommon.REJECT.toString().equals(processStatusCode))) {
			// Step 4: IF not complete workflow then create document filter "IN"  and "OUT"
			EfoOzDocFilterIn efoOzDocFilterInTaskNew = new EfoOzDocFilterIn();
			efoOzDocFilterInTaskNew.setTaskId(taskNewId);
			efoOzDocFilterInTaskNew.setDocId(docId);
			
			
			efoOzDocFilterInTaskNew.setSubmittedDate(sysDate);
			efoOzDocFilterInTaskNew.setSubmittedOrgId(docFilterActionDto.getAssigneeOrgId());
			efoOzDocFilterInTaskNew.setSubmittedPositionId(docFilterActionDto.getAssigneePositionId());
			efoOzDocFilterInTaskNew.setSubmittedId(docFilterActionDto.getAssigneeId());
				
			efoOzDocFilterInTaskNew.setOwnerOrgId(docFilterActionDto.getAssigneeOrgId());
			efoOzDocFilterInTaskNew.setOwnerPositionId(docFilterActionDto.getAssigneePositionId());
			efoOzDocFilterInTaskNew.setOwnerId(docFilterActionDto.getAssigneeId());
			
			efoOzDocFilterInService.saveDocFilterIn(efoOzDocFilterInTaskNew);
			
			EfoOzDocFilterOut efoOzDocFilterOutTaskNew = objectMapper.convertValue(efoOzDocFilterInTaskNew, EfoOzDocFilterOut.class);
			efoOzDocFilterOutTaskNew.setId(null);
			efoOzDocFilterOutService.saveDocFilterOut(efoOzDocFilterOutTaskNew);
		}
		
		

	}
    
    
//    @Override
//    public void actionCompleteFlowDataBeforeCreateTask(Long taskId,Long accActionId) {
//        // Step 1: remove task IN
//        List<EfoOzDocFilterIn> efoOzDocFilterInList = efoOzDocFilterInService.getListEfoOzDocFilterInByJpmTaskId(jpmTaskId);
//        for (EfoOzDocFilterIn efoOzDocFilterIn : efoOzDocFilterInList) {
//            efoOzDocFilterInService.deleteRefInByJpmTaskId(jpmTaskId);
//            // Step 2: Create task OUT
//            EfoOzDocFilterOut efoOzDocFilterOut = objectMapper.convertValue(efoOzDocFilterIn, EfoOzDocFilterOut.class);
//            efoOzDocFilterOut.setId(null);
//            efoOzDocFilterOutService.createFilterOut(efoOzDocFilterOut, accActionId);  
//        }        
//    }
//
//
//    @Override
//    public <T extends AbstractDocumentFilterDto> void actionSubmitFlowData(T abstractdocFilterActionDto) {
//      DocFilterActionDto docFilterActionDto = (DocFilterActionDto) abstractdocFilterActionDto;
//      // Step 1: Create task IN
//      EfoOzDocFilterIn efoOzDocFilterIn = objectMapper.convertValue(docFilterActionDto, EfoOzDocFilterIn.class);
//      efoOzDocFilterIn.setType(TaskType.TASK_TYPE_ASSIGNEE.toString());
//      efoOzDocFilterIn.setOwnerId(docFilterActionDto.getAccActionId());
//      efoOzDocFilterInService.createAccRefWithRefType(efoOzDocFilterIn);
////      this.actionCompleteFlowDataBeforeCreateTask(docFilterActionDto.getJpmTaskId(), docFilterActionDto.getAccActionId());
//    }
//
//
//    @Override
//    public <T extends AbstractDocumentFilterDto> void actionCompleteFlowDataAfterCreateTask(T abstractdocFilterActionDto) {
//        DocFilterActionDto docFilterActionDto = (DocFilterActionDto) abstractdocFilterActionDto;
//        
//        // Step 1: Create task IN
//        Long ownerId = docFilterActionDto.getOwnerId();
//        docFilterActionDto.setType(TaskType.TASK_TYPE_CANDIDATE.toString());
//        docFilterActionDto.setOwnerId(docFilterActionDto.getFunctionId());
//        if (null != ownerId) {
//            docFilterActionDto.setType(TaskType.TASK_TYPE_ASSIGNEE.toString());
//            docFilterActionDto.setOwnerId(ownerId);
//        }
//        
//        EfoOzDocFilterIn efoOzDocFilterIn = objectMapper.convertValue(docFilterActionDto, EfoOzDocFilterIn.class);
//        if (null != docFilterActionDto.getJpmTaskId()) {
//            efoOzDocFilterInService.createAccRefWithRefType(efoOzDocFilterIn);
//        }
//        // Step 3: Create Refer
//        List<Long> idsCc = docFilterActionDto.getAccCcIds();
//        if (DtsCollectionUtil.isNotEmpty(idsCc)) {
//            for (Long idCc : idsCc) {
//                EfoOzDocFilterRef efoOzDocFilterRef = objectMapper.convertValue(docFilterActionDto, EfoOzDocFilterRef.class);
//                efoOzDocFilterRef.setOwnerId(idCc);
//                efoOzDocFilterRef.setRefType(ReferenceType.REF_CC.toString());
//                efoOzDocFilterRef.setUnRead(false);
//                efoOzDocFilterRefService.createAccRefWithRefType(efoOzDocFilterRef);
//            }
//        }
//    }
}
