package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppStepDeployDto;
import vn.com.unit.workflow.entity.JpmStepDeploy;
import vn.com.unit.workflow.repository.JpmStepDeployRepository;

public interface AppStepDeployRepository extends JpmStepDeployRepository {
	JpmStepDeploy getById(@Param("id") Long id);
    
    /**
     * find List<JpmStepDeploy> by processId and deleteBy is null
     * 
     * @param processId
     * 			type Long
     * @return List<JpmStepDeploy>
     * @author KhuongTH
     */
    List<JpmStepDeploy> findJpmStepByProcessId(@Param("processId") Long processId);
    
    /**
     * find List<AppStepDeployDto> by processId and deleteBy is null
     * get data :StepDeploy, status, button, function
     * 
     * @param processId
     * 			type Long
     * @return List<AppStepDeployDto>
     * @author KhuongTH
     */
    List<AppStepDeployDto> findJpmStepDtoDetailByProcessId(@Param("processId") Long processId, @Param("lang") String lang, @Param("constantStatusType") String constantStatusType);
    
    /**
     * findMinJpmStepDeployByProcessId
     * 
     * @param processDeployId
     * 			type Long
     * @return JpmStepDeploy
     * @author KhoaNA
     */
    JpmStepDeploy findMinJpmStepDeployByProcessId(@Param("processDeployId") Long processDeployId);
    
    /**
     * @param processDeployId
     * @param stepcode
     * @return
     */
    JpmStepDeploy findJpmStepDeployByProcessIdAndStepCode (@Param("processDeployId") Long processDeployId, @Param("stepcode") String stepcode);
    
    /**
     * @param processId
     * @return
     */
    List<AppStepDeployDto> findJpmStepDtoByProcessId(@Param("processId") Long processId);
    
    /**
     * @param processDeployId
     * @param statusCode
     * @return
     */
    JpmStepDeploy findJpmStepDeployByProcessIdAndStatusCode(@Param("processDeployId") Long processDeployId, @Param("statusCode") String statusCode);
    
    /**
     * @param processDeployId
     * @param stepcode
     * @return
     */
    String findStepKindByIdAndCode(@Param("processDeployId") Long processDeployId, @Param("stepcode") String stepcode);
    
    /**
     * 
     * findStatusByProcessAndStatusCommon
     * @param processDeployId
     * @param statusCommon
     * @return
     * @author taitt
     */
    List<String> findStatusByProcessAndStatusCommon(@Param("processDeployId") Long processDeployId, @Param("statusCommon") String statusCommon);
    
    /**
     * findStepsByProcessId
     * @param newProcessDeployId
     * @return
     */
    List<Select2Dto> findStepsByProcessId(@Param("newProcessDeployId") Long newProcessDeployId);
    
    /**
     * findCodeById
     * @param Id
     * @return
     */
    String findCodeById(@Param("id") Long Id);
    
    /**
     * 
     * findStepCodeByProcessAndStatusCommon
     * @param processDeployId
     * @param statusCommon
     * @return
     * @author taitt
     */
    List<String> findStepCodeByProcessAndStatusCommon(@Param("processDeployId") Long processDeployId, @Param("statusCommon") String statusCommon);
    
}
