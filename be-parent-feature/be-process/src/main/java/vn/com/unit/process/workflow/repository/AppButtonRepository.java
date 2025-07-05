package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppButtonDto;
import vn.com.unit.workflow.entity.JpmButton;
import vn.com.unit.workflow.repository.JpmButtonRepository;

public interface AppButtonRepository extends JpmButtonRepository {
    List<AppButtonDto> getListJpmButtonByProcessId(@Param("jpmProcessId") Long jpmProcessId);
    JpmButton getById(@Param("id") Long id);
    boolean validateButtonWithNameAndProcessId(@Param("buttonId") Long buttonId, @Param("buttonName") String buttonName, @Param("processId") Long processId);
    boolean validateJpmButtonUsing(@Param("buttonId") Long buttonId);
    
    /**
     * getListButtonDefaultFreeform
     * @param appSystem
     * @return List<AppButtonDto>
     * @author KhuongTH
     */
    List<AppButtonDto> getListButtonDefaultFreeform(@Param("processType") String processType, @Param("processKind") String processKind);
    
    /**
     * getSelect2DtoByProcess
     * @param processId
     * @param lang
     * @return
     * @author KhuongTH
     */
    List<Select2Dto> getSelect2DtoByProcess(@Param("processId")Long processId, @Param("lang") String lang);
}
