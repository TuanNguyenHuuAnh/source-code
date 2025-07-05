package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.workflow.entity.JpmStatus;
import vn.com.unit.workflow.repository.JpmStatusRepository;

public interface AppStatusRepository extends JpmStatusRepository {
    List<AppStatusDto> getListJpmStatusByProcessId(@Param("jpmProcessId") Long jpmProcessId);
    JpmStatus getById(@Param("id") Long id);
    boolean validateStatusWithCodeAndProcessId(@Param("statusId") Long statusId, @Param("statusCode") String statusCode, @Param("processId") Long processId);
    boolean validateJpmStatusUsing(@Param("statusId") Long statusId);
    
    JpmStatus getStatusByProcessId(@Param("processId") Long processId);
    
    List<JpmStatus> findJpmStatusByProcessId(@Param("jpmProcessId") Long jpmProcessId);
    
    List<AppStatusDto> getListStatusDefaultFreeform(@Param("appSystem") String appSystem);
}
