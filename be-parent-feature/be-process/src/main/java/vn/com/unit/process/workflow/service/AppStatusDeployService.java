package vn.com.unit.process.workflow.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppStatusDeployDto;
import vn.com.unit.workflow.entity.JpmStatusDeploy;

public interface AppStatusDeployService {
	/**
	 * AppStatusService
	 * 
	 * @version 01-00
	 * @since 01-00
	 * @author KhuongTH
	 */
	public AppStatusDeployDto[] getListStatusDeployDtoByProcessId(Long processId);
	
	public AppStatusDeployDto getById(Long id);
	
	public JpmStatusDeploy getByBusinessCodeAndStatusCode(Long processId);
	
	/**
	 * getStatusListCommon
	 * @return List<Select2Dto>
	 * @author KhoaNA
	 */
	List<Select2Dto> getStatusListCommon(Locale locale) throws Exception;
	
	List<Select2Dto> getStatusListCommon(Locale locale, String constantDisplayType);
	
	/**
	 * getStatusDeployListByProcessDeployId
	 * 
	 * @param processDeployId
	 * @param lang
	 * @return List<Select2Dto>
	 * @author KhoaNA
	 */
	List<Select2Dto> getStatusDeployListByProcessDeployId(Long processDeployId, String lang);
}
