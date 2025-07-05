package vn.com.unit.process.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.process.workflow.dto.AppButtonForStepDto;
import vn.com.unit.workflow.entity.JpmButtonForStep;
import vn.com.unit.workflow.repository.JpmButtonForStepRepository;

public interface AppButtonForStepRepository extends JpmButtonForStepRepository {
    AppButtonForStepDto[] getButtonForStepByStepId(@Param("stepId") Long stepId);
    @Modifying
    void removeJpmButtonForStepByIds(@Param("stepId") Long stepId, @Param("ids")List<Long> ids, @Param("user") String user, @Param("sysDate")Date sysDate);
    List<JpmButtonForStep> getButtonForStepByIds(@Param("ids") List<Long> ids);
    List<JpmButtonForStep> getButtonForStepByProcessId(@Param("processId") Long processId);
    
    AppButtonForStepDto[] getListButtonForStepDefaultFreeform(@Param("stepId") Long stepId
            , @Param("processType") String processType, @Param("processKind") String processKind);
}
