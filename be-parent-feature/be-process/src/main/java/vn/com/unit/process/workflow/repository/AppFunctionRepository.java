package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.process.workflow.dto.AppFunctionDto;
import vn.com.unit.workflow.entity.JpmPermission;
import vn.com.unit.workflow.repository.JpmPermissionRepository;

public interface AppFunctionRepository extends JpmPermissionRepository {
    List<AppFunctionDto> getListJpmFunctionByProcessId(@Param("jpmProcessId") Long jpmProcessId);
    JpmPermission getById(@Param("id") Long id);
    boolean validateFunctionWithNameAndProcessId(@Param("functionId") Long functionId, @Param("functionName") String functionName, @Param("processId") Long processId);
    int countJpmFunctionByProcessId(@Param("processId") Long processId);
    boolean validateJpmFunctionUsing(@Param("functionId") Long functionId);
    
    List<AppFunctionDto> getListFunctionDefaultFreeform(@Param("appSystem") String appSystem);
}
