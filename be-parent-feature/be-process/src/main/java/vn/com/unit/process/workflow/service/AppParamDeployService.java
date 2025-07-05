package vn.com.unit.process.workflow.service;

import vn.com.unit.process.workflow.dto.AppParamDeployDto;

public interface AppParamDeployService {
	public AppParamDeployDto[] getListParamDeployDtoByProcessId(Long processId);
	
	public AppParamDeployDto getById(Long Id);
}
