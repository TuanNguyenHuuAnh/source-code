/*******************************************************************************
 * Class        ：AppProcessDmnDeployService
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmProcessDmnDeployDto;

public interface AppProcessDmnDeployService {
    
    /**
     * <p>Gets the jpm process dmn deploy dtos by process deploy id.</p>
     *
     * @param processDeployId type {@link Long}
     * @return the jpm process dmn deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmProcessDmnDeployDto> getJpmProcessDmnDeployDtosByProcessDeployId(Long processDeployId);
}
