/*******************************************************************************
 * Class        ：ProcessDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;

/**
 * <p>
 * ProcessDeployService
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface ProcessDeployService extends BaseRestService<ObjectDataRes<JpmProcessDeployDto>, JpmProcessDeployDto> {

    /**
     * <p>
     * Export process deploy.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link JpmProcessImportExportDto}
     * @author KhuongTH
     * @throws DetailException 
     */
    JpmProcessImportExportDto exportProcessDeploy(Long processDeployId) throws DetailException;

    /**
     * getJpmProcessDeployDtoByFormId
     * @param formId
     * @return
     * @throws DetailException
     * @author taitt
     */
    List<JpmProcessDeployDto> getJpmProcessDeployDtoByFormId(Long formId) throws DetailException;
}
