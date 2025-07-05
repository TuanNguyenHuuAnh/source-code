/*******************************************************************************
 * Class        ：AppParamConfigStepService
 * Created date ：2019/11/29
 * Lasted date  ：2019/11/29
 * Author       ：KhuongTH
 * Change log   ：2019/11/29：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmParamConfigDto;

/**
 * AppParamConfigStepService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppParamConfigStepService {

    /**
     * getConfigsByParamId
     *
     * @param paramId
     * @param processId
     * @return List<JpmParamConfigDto>
     * @author KhuongTH
     */
    List<JpmParamConfigDto> getConfigsByParamId(Long paramId, Long processId);
    
    /**
     * deleteByParamId
     *
     * @param paramId void
     * @author KhuongTH
     */
    void deleteByParamId(Long paramId);
}
