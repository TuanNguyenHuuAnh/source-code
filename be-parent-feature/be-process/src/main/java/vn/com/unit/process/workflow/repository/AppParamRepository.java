package vn.com.unit.process.workflow.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.workflow.repository.JpmParamRepository;

public interface AppParamRepository extends JpmParamRepository {
    boolean validateParamWithNameAndProcessId(@Param("paramId") Long paramId, @Param("fieldName") String fieldName,
            @Param("processId") Long processId);
}
