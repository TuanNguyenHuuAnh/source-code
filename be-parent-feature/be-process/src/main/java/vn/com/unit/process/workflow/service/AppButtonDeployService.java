package vn.com.unit.process.workflow.service;

import java.util.List;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppButtonDeployDto;

public interface AppButtonDeployService {
	/**
	 * AppButtonService
	 * 
	 * @version 01-00
	 * @since 01-00
	 * @author KhuongTH
	 */
	public AppButtonDeployDto[] getListButtonDeployDtoByProcessId(Long processId);
	
	public AppButtonDeployDto getById(Long id);

	public List<AppButtonDeployDto> getListButtonDeployDtoByStepId(Long stepId);
	/**
	 * @param processDeployId
	 * @param stepCode
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	List<Select2Dto> getButtonActions(Long processDeployId, String stepCode, String lang) throws Exception;
	
	/**
	 * findOneButtonByProcessIdAndButtonType for get button auto-approve;
	 *
	 * @param processId
	 * @param buttonType
	 * @return AppButtonDeployDto
	 * @author KhuongTH
	 */
	AppButtonDeployDto findOneButtonByProcessIdAndButtonType(Long processId, String buttonType);

    /**
     * getSurveyButtons
     *
     * @param stepId
     * @param lang
     * @return List<Select2Dto>
     * @throws Exception 
     * @author KhuongTH
     */
    List<Select2Dto> getSurveyButtons(Long stepId, String lang) throws Exception;

    /**
     * findActionDtoByProcessIdAndButtonId
     * @param processId
     * @param buttonId
     * @return
     * @author taitt
     */
    ActionDto findActionDtoByProcessIdAndButtonId(Long processId, Long buttonId);


}
