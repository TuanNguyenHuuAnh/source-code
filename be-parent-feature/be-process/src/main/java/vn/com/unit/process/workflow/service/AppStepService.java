/*******************************************************************************
 * Class        AppStepService
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.process.workflow.dto.AppStepDto;
import vn.com.unit.workflow.entity.JpmStatus;
import vn.com.unit.workflow.entity.JpmStep;

/**
 * AppStepService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppStepService {
    AppStepDto getById(Long id);
    JpmStep saveJpmStep(AppStepDto appStepDto);
    void deleteJpmStep(Long id);
    
    /**
     * Save List<AppStepDto> by jpmStatusList and processId
     *
     * @param  jpmStepDtoMap
     * 			type Map<String,List<AppStepDto>>
     * @return List<JpmStep>
     * @author KhoaNA
     */
    public List<JpmStep> saveJpmStepByProcessId(Map<String, List<AppStepDto>> jpmStepDtoMap, List<JpmStatus> jpmStatusList, Long processId);
    
    public List<JpmStep> getJpmStepByProcessId(Long processId);
    
    public List<AppStepDto> getJpmStepDtoDetailByProcessId(Long processId, String lang);

    /**
     * getListStepDefaultFreeform
     *
     * @param processType
     * @param processKind
     * @return List<AppStepDto>
     * @author KhuongTH
     */
    List<AppStepDto> getListStepDefaultFreeform(String processType, String processKind);
}
