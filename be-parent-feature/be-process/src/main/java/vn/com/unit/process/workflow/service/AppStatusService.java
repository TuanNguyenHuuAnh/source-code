package vn.com.unit.process.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.workflow.entity.JpmStatus;


public interface AppStatusService {
    AppStatusDto[] getListJpmStatusByProcessId(Long jpmProcessId);
    AppStatusDto getById(Long id);
    JpmStatus saveJpmStatus(AppStatusDto statusDto) throws Exception;
    void deleteJpmStatus(Long statusId);
    boolean validateStatusWithCodeAndProcessId(Long statusId, String statusCode, Long processId);
    boolean validateJpmStatusUsing(Long statusId);
    
    /**
     * Save List<JpmStatus> by listJpmStatus and processId
     *
     * @param  listJpmStatus
     * 			type List<AppStatusDto>
     * @param  processId
     * 			type Long
     * @return List<JpmStatus>
     * @author KhoaNA
     */
    List<JpmStatus> saveJpmStatus(List<AppStatusDto> listJpmStatus, Long processId);
    
    JpmStatus getByBusinessCodeAndStatusCode(Long processId);
    
    /**
     * saveJpmStatusByProcessId
     * @param listJpmStatus
     * @param processId
     * @param convertStatusId
     * @return List<JpmStatus>
     * @author KhuongTH
     */
    List<JpmStatus> saveJpmStatusByProcessId(List<AppStatusDto> listJpmStatus, Long processId, Map<Long, Long> convertStatusId);
    
    /**
     * getListStatusDefaultFreeform
     * @return List<AppStatusDto>
     * @author KhuongTH
     */
    List<AppStatusDto> getListStatusDefaultFreeform();
}
