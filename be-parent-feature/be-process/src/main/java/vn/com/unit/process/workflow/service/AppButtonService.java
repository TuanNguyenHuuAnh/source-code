package vn.com.unit.process.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppButtonDto;
import vn.com.unit.workflow.entity.JpmButton;

public interface AppButtonService {
    AppButtonDto[] getListJpmButtonByProcessId(Long jpmProcessId);
    AppButtonDto getById(Long id);
    JpmButton saveJpmButton(AppButtonDto buttonDto) throws Exception;
    void deleteJpmButton(Long buttonId);
    boolean validateButtonWithNameAndProcessId(Long buttonId, String buttonName, Long processId);
    boolean validateJpmButtonUsing(Long buttonId);
    
    /**
     * Get all button default
     * @return List<AppButtonDto>
     * @author KhoaNA
     */
    List<AppButtonDto> getListButtonDefault();
    
    /**
     * Get list button default by kinds
     * 
     * @param type
     * 			type String
     * @param kinds
     * 			type List<String>
     * @return List<AppButtonDto>
     * @author KhoaNA
     */
    List<AppButtonDto> getListButtonDefaultByTypeAndKinds(String type, List<String> kinds);
    
    List<JpmButton> saveJpmButtonByProcessId(List<AppButtonDto> buttonDtoList, Long processId, Map<Long, Long> convertButtonId);
 
    /**
     * getListButtonDefaultFreeform
     *
     * @param processType
     * @param processKind
     * @return List<AppButtonDto>
     * @author KhuongTH
     */
    List<AppButtonDto> getListButtonDefaultFreeform(String processType, String processKind);
    
    /**
     * getSelect2DtoByProcess
     * @param processId
     * @param lang
     * @return
     * @author KhuongTH
     */
    List<Select2Dto> getSelect2DtoByProcess(Long processId, String lang);
}
