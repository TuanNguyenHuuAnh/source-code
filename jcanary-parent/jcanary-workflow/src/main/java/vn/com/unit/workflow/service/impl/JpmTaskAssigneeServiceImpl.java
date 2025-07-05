/*******************************************************************************
 * Class        ：JpmTaskAssigneeServiceImpl
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：Tan Tai
 * Change log   ：2021/03/04：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.workflow.dto.JpmTaskAssigneeDto;
import vn.com.unit.workflow.entity.JpmHiTaskAssignee;
import vn.com.unit.workflow.entity.JpmTaskAssignee;
import vn.com.unit.workflow.repository.JpmTaskAssigneeRepository;
import vn.com.unit.workflow.service.JpmHiTaskAssigneeService;
import vn.com.unit.workflow.service.JpmTaskAssigneeService;

/**
 * JpmTaskAssigneeServiceImpl.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmTaskAssigneeServiceImpl implements JpmTaskAssigneeService{

	
	/** The jpm task assignee repository. */
	@Autowired
	private JpmTaskAssigneeRepository jpmTaskAssigneeRepository;
	
	/** The jpm hi task assignee service. */
	@Autowired
	private JpmHiTaskAssigneeService jpmHiTaskAssigneeService;
	
    /** The object mapper. */
    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;
	
	/* (non-Javadoc)
	 * @see vn.com.unit.workflow.service.JpmTaskAssigneeService#createTaskAssignee(vn.com.unit.workflow.dto.JpmTaskAssigneeDto, java.util.List)
	 */
	@Transactional
	@Override
	public void createTaskAssignee(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds) {
		this.saveJpmtaskWithFlag(jpmTaskAssigneeDto, accountIds, JpmTaskAssigneeService.MATRIX_FLAG.ASSIGNEE_FLAG.getValue());
	}
	
	/* (non-Javadoc)
	 * @see vn.com.unit.workflow.service.JpmTaskAssigneeService#createTaskOwner(vn.com.unit.workflow.dto.JpmTaskAssigneeDto, java.util.List)
	 */
	@Transactional
	@Override
	public void createTaskOwner(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds) {
		this.saveJpmtaskWithFlag(jpmTaskAssigneeDto, accountIds, JpmTaskAssigneeService.MATRIX_FLAG.OWNER_FLAG.getValue());
	}


	/* (non-Javadoc)
	 * @see vn.com.unit.workflow.service.JpmTaskAssigneeService#createTaskSubmitted(vn.com.unit.workflow.dto.JpmTaskAssigneeDto, java.util.List)
	 */
	@Transactional
	@Override
	public void createTaskSubmitted(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds) {
		this.saveJpmtaskWithFlag(jpmTaskAssigneeDto, accountIds, JpmTaskAssigneeService.MATRIX_FLAG.SUBMITTED_FLAG.getValue());
	}

	/* (non-Javadoc)
	 * @see vn.com.unit.workflow.service.JpmTaskAssigneeService#createTaskDelegate(vn.com.unit.workflow.dto.JpmTaskAssigneeDto, java.util.List)
	 */
	@Transactional
	@Override
	public void createTaskDelegate(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds) {
		this.saveJpmtaskWithFlag(jpmTaskAssigneeDto, accountIds, JpmTaskAssigneeService.MATRIX_FLAG.DELEGATE_FLAG.getValue());
	}

	/**
	 * <p>Save jpmtask with flag.</p>
	 *
	 * @author Tan Tai
	 * @param jpmTaskAssigneeDto type {@link JpmTaskAssigneeDto}
	 * @param accountIds type {@link List<Long>}
	 * @param matrixFlag type {@link String}
	 * @return {@link List<JpmTaskAssignee>}
	 */
	@Override
	public List<JpmTaskAssignee> saveJpmtaskWithFlag(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds,String matrixFlag) {
		List<JpmTaskAssignee> jpmTaskAssigneeLst = new ArrayList<>();
		
		Long processDeployId = jpmTaskAssigneeDto.getProcessDeployId();
		Long permissionDeployId = jpmTaskAssigneeDto.getPermissionDeployId();
		Long stepDeployId = jpmTaskAssigneeDto.getStepDeployId();
		Long taskId = jpmTaskAssigneeDto.getTaskId();
		Integer permissionType = jpmTaskAssigneeDto.getPermissionType();
		
				
		if(DtsCollectionUtil.isNotEmpty(accountIds)) {
			jpmTaskAssigneeLst = accountIds.stream().map(accountId -> {
				JpmTaskAssignee jpmTaskAssignee = new JpmTaskAssignee();

				JpmTaskAssignee jpmTaskAssigneeOld = jpmTaskAssigneeRepository.getListJpmTaskAssigneeByTaskIdAndAccountId(taskId, stepDeployId, processDeployId,accountId);
				
				if (null != jpmTaskAssigneeOld) {
					jpmTaskAssignee = jpmTaskAssigneeOld;
					
					
					this.setMatrixFlag(matrixFlag, jpmTaskAssigneeOld);
					jpmTaskAssigneeRepository.update(jpmTaskAssigneeOld);
			        // update task history
			        JpmHiTaskAssignee jpmHiTaskAssigneeold = objectMapper.convertValue(jpmTaskAssigneeOld, JpmHiTaskAssignee.class);
			        jpmHiTaskAssigneeService.updateJpmHiTaskAssignee(jpmHiTaskAssigneeold);
				}else {					
					jpmTaskAssignee.setProcessDeployId(processDeployId);
					jpmTaskAssignee.setPermissionDeployId(permissionDeployId);
					jpmTaskAssignee.setPermissionType(permissionType);
					jpmTaskAssignee.setStepDeployId(stepDeployId);
					jpmTaskAssignee.setTaskId(taskId);
					jpmTaskAssignee.setAccountId(accountId);

					this.setMatrixFlag(matrixFlag, jpmTaskAssignee);
					jpmTaskAssigneeRepository.create(jpmTaskAssignee);

			        // save task history
			        JpmHiTaskAssignee jpmHiTaskAssignee = objectMapper.convertValue(jpmTaskAssignee, JpmHiTaskAssignee.class);
			        jpmHiTaskAssigneeService.saveJpmHiTaskAssignee(jpmHiTaskAssignee);
				}

				
				return jpmTaskAssignee;
			}).collect(Collectors.toList());
		}
		
		return jpmTaskAssigneeLst;
	}
	
	
	/**
	 * <p>Sets the matrix flag.</p>
	 *
	 * @author Tan Tai
	 * @param matrixFlag type {@link String}
	 * @param jpmTaskAssignee type {@link JpmTaskAssignee}
	 */
	private void setMatrixFlag(String matrixFlag, JpmTaskAssignee jpmTaskAssignee) {
		JpmTaskAssigneeService.MATRIX_FLAG enumsFlag = JpmTaskAssigneeService.MATRIX_FLAG.resolveByValue(matrixFlag);
		switch (enumsFlag) {
		case ASSIGNEE_FLAG:
			jpmTaskAssignee.setAssigneeFlag(Boolean.TRUE);
			break;
		case DELEGATE_FLAG:
			jpmTaskAssignee.setDelegateFlag(Boolean.TRUE);
			break;
		case OWNER_FLAG:
			jpmTaskAssignee.setOwnerFlag(Boolean.TRUE);
			break;
		case SUBMITTED_FLAG:
			jpmTaskAssignee.setSubmittedFlag(Boolean.TRUE);
			break;

		default:
			break;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see vn.com.unit.workflow.service.JpmTaskAssigneeService#getAccIdsByTaskId(java.lang.Long)
	 */
	@Override
	public List<Long> getAccIdsByTaskId(Long taskId) {
		return jpmTaskAssigneeRepository.getAccIdsByTaskId(taskId);
	}
	
	@Transactional
	@Override
	public void completeTaskAssignee(Long taskId) {
		jpmTaskAssigneeRepository.deleteByTaskId(taskId);
	}

    @Override
    public List<Long> getListAccountByTaskIdAndType(Long taskId, Long type) {
        return jpmTaskAssigneeRepository.getListAccountByTaskIdAndType(taskId, type);
    }
}
