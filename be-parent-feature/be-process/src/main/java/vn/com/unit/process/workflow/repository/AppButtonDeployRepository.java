package vn.com.unit.process.workflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.process.workflow.dto.AppButtonDeployDto;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.repository.JpmButtonDeployRepository;

public interface AppButtonDeployRepository extends JpmButtonDeployRepository {
	/**
     * findListButtonDeployDtoByProcessId
     * @Button Long processId
     * @return array <AppButtonDeployDto>
     * @author KhuongTH
     */
	public AppButtonDeployDto[] findListButtonDeployDtoByProcessId(@Param("processId") Long processId);
	/**
     * getById
     * @Button Long id
     * @return AppButtonDeployDto
     * @author KhuongTH
     */
	public AppButtonDeployDto getById(@Param("id") Long id);
	
	public List<AppButtonDeployDto> findListButtonDeployDtoByStepId(@Param("stepId")Long stepId);
	
	/**
	 * findListButtonForDocDtoByProcessDeployIdAndStepCode
	 * 
	 * @param processDeployId
	 * 			type Long
	 * @param stepCode
	 * 			type String
	 * @param lang
	 * 			type String
	 * @return List<AppButtonDeployDto>
	 * @author KhoaNA
	 */
	public List<JpmButtonForDocDto> findListButtonForDocDtoByProcessDeployIdAndStepCode(
			@Param("processId") Long processDeployId, @Param("stepCode") String stepCode, @Param("lang") String lang);
	
	
	public List<JpmButtonForDocDto> findListButtonForDocDtoByProcessIdAndButtonType(
			@Param("processId") Long processDeployId, @Param("buttonType")String buttonType, @Param("lang") String lang);
	
	/**
	 * findOneButtonByProcessIdAndButtonType for find button auto-approve;
	 *
	 * @param processId
	 * @param buttonType
	 * @return AppButtonDeployDto
	 * @author KhuongTH
	 */
	AppButtonDeployDto findOneButtonByProcessIdAndButtonType(@Param("processId")Long processId, @Param("buttonType")String buttonType);
	
	/**
	 * 
	 * findOneButtonByProcessIdAndButtonType
	 * @param processId
	 * @param buttonId
	 * @return
	 * @author taitt
	 */
	ActionDto findActionDtoByProcessIdAndButtonId(@Param("processId")Long processId, @Param("buttonId")Long buttonId);
	
	List<AppButtonDeployDto> findButtonsByStepId(@Param("stepId")Long stepId, @Param("lang")String lang);
	
	/**
	 * 
	 * findListButtonForDocDtoByBusinessKeysAndActTaskIds
	 * @param businessKeys
	 * @param actTaskIds
	 * @param userId
	 * @param lang
	 * @return
	 * @author taitt
	 */
	public List<JpmButtonForDocDto> findListButtonForDocDtoByBusinessKeysAndActTaskIds(
            @Param("businessKeys") List<String> businessKeys, @Param("actTaskIds")  List<String> actTaskIds
            , @Param("userId") Long userId, @Param("lang")String lang, @Param("sysDate")Date sysDate);
    
}
