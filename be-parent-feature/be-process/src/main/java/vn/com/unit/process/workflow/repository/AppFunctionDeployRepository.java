package vn.com.unit.process.workflow.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.process.workflow.dto.AppFunctionDeployDto;
import vn.com.unit.workflow.repository.JpmPermissionDeployRepository;

public interface AppFunctionDeployRepository extends JpmPermissionDeployRepository {
	/**
     * findListFunctionDeployDtoByProcessId
     * @Function Long processId
     * @return array <AppFunctionDeployDto>
     * @author KhuongTH
     */
	public AppFunctionDeployDto[] findListFunctionDeployDtoByProcessId(@Param("processId") Long processId);
	/**
     * getById
     * @Function Long id
     * @return array <AppFunctionDeployDto>
     * @author KhuongTH
     */
	public AppFunctionDeployDto getById(@Param("id") Long id);
}
