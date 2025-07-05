package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.process.workflow.dto.AppButtonForStepDeployDto;
import vn.com.unit.workflow.entity.JpmButtonForStepDeploy;
import vn.com.unit.workflow.repository.JpmButtonForStepDeployRepository;

public interface AppButtonForStepDeployRepository extends JpmButtonForStepDeployRepository {
	List<AppButtonForStepDeployDto> getButtonForStepByStepId(@Param("stepId") Long stepId);

    List<JpmButtonForStepDeploy> getButtonForStepByProcessId(@Param("processId") Long processId);
    
    List<String> getListFunctionCodeByStepIdAndPrefixButtonType(@Param("stepId")Long stepId, @Param("prefix")String prefix);
    
    boolean getDisplayHistoryApproveByStepIdAndButtonId(@Param("stepId")Long stepId,@Param("buttonId")Long buttonId);
    
    ActionDto getActionDtoByStepAndButtonId(@Param("processDeployId")Long processDeployId, @Param("stepCode")String stepCode, 
            @Param("buttonId")Long buttonId);

    ActionDto getActionDtoByActTaskIdAndButtonId(@Param("processDeployId") Long processDeployId, @Param("actTaskId") String actTaskId,
            @Param("buttonId") Long buttonId, @Param("docState") String docState);
    
    List<String> getListFunctionCodeByProcessIdAndStepCode(@Param("processId") Long processId
            , @Param("stepCode") String stepCode);
}
