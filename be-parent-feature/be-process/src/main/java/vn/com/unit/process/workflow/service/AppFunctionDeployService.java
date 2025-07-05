package vn.com.unit.process.workflow.service;

import vn.com.unit.process.workflow.dto.AppFunctionDeployDto;

public interface AppFunctionDeployService {

    public AppFunctionDeployDto[] getListFunctionDeployDtoByProcessId(Long processId);

    public AppFunctionDeployDto getById(Long id);
}
