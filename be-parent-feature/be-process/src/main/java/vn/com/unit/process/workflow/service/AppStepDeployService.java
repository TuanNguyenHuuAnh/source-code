/*******************************************************************************
 * Class        AppStepDeployService
 * Created date 2019/07/05
 * Lasted date  2019/07/05
 * Author       KhuongTH
 * Change log   2019/07/05 01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.sql.SQLException;
import java.util.List;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppStepDeployDto;
import vn.com.unit.workflow.entity.JpmStepDeploy;

/**
 * AppStepDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppStepDeployService {

    AppStepDeployDto getById(Long id);

    /**
     *
     * @param
     * @return List<JpmStepDeploy>
     * @author KhuongTH
     */
    public List<JpmStepDeploy> getJpmStepDeployByProcessId(Long processId);

    public List<AppStepDeployDto> getJpmStepDeployDtoDetailByProcessId(Long processId, String lang);

    /**
     * getMinJpmStepDeployByProcessId
     * 
     * @param processDeployId
     *            type Long
     * @return JpmStepDeploy
     * @author KhoaNA
     */
    public JpmStepDeploy getMinJpmStepDeployByProcessId(Long processDeployId);

    /**
     * @param processDeployId
     * @param stepcode
     * @return
     * @throws Exception
     */
    JpmStepDeploy findJpmStepDeployByProcessIdAndStepCode(Long processDeployId, String stepcode) throws Exception;

    /**
     * @param processId
     * @return
     * @throws Exception
     */
    public List<AppStepDeployDto> getJpmStepDeployDtoByProcessId(Long processId) throws Exception;

    /**
     * @param processDeployId
     * @param statusCode
     * @return
     * @throws Exception
     */
    JpmStepDeploy getJpmStepDeployByProcessIdAndStatusCode(Long processDeployId, String statusCode) throws Exception;

    /**
     * @param processDeployId
     * @param stepcode
     * @return
     */
    boolean isSurvey(Long processDeployId, String stepCode) throws NullPointerException, SQLException;

    /**
     * 
     * getStepCodeIntegActionTask
     * 
     * @param docState
     * @param processDeployId
     * @param actTaskId
     * @param businessKey
     * @return
     * @author taitt
     */
    public List<String> getStatusByStatusCommonAndProcess(String statusCommon, Long processDeployId);

    /**
     * findStepsByProcessId
     * 
     * @param newProcessDeployId
     * @return
     * @throws SQLException
     */
    List<Select2Dto> findStepsByProcessId(Long newProcessDeployId) throws SQLException;

    /**
     * findCodeById
     * 
     * @param Id
     * @return
     * @throws SQLException
     */
    String findCodeById(Long Id) throws SQLException;

    /**
     * getStepCodeByStatusCommonAndProcess
     * 
     * @param statusCommon
     * @param processDeployId
     * @return
     * @author taitt
     */
    List<String> getStepCodeByStatusCommonAndProcess(String statusCommon, Long processDeployId);

}
