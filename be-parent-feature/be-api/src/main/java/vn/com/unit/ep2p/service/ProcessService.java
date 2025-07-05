/*******************************************************************************
 * Class        ：ProcessService
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.ProcessAddReq;
import vn.com.unit.ep2p.dto.req.ProcessImportReq;
import vn.com.unit.ep2p.dto.req.ProcessUpdateReq;
import vn.com.unit.workflow.dto.JpmProcessDto;

/**
 * <p>
 * ProcessService
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface ProcessService extends BaseRestService<ObjectDataRes<JpmProcessDto>, JpmProcessDto> {

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param reqProcessAddDto
     *            type {@link ProcessAddReq}
     * @return {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    Long create(ProcessAddReq reqProcessAddDto) throws DetailException;

    /**
     * <p>
     * Update.
     * </p>
     *
     * @param reqProcessUpdateDto
     *            type {@link ProcessUpdateReq}
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    void update(ProcessUpdateReq reqProcessUpdateDto) throws DetailException;

    /**
     * <p>
     * Deploy.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @param oldProcessDeployId
     *            type {@link Long}
     * @param cloneSlaFlag
     *            type {@link Boolean}         
     * @param cloneRoleFlag
     *            type {@link Boolean}         
     * @return {@link Long}: processDeployId
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    Long deploy(Long processId, Long oldProcessDeployId, boolean cloneSlaFlag, boolean cloneRoleFlag) throws DetailException;
    
    /**
     * <p>
     * Import process.
     * </p>
     *
     * @param reqProcessImportDto
     *            type {@link ProcessImportReq}
     * @return {@link Long}
     * @author KhuongTH
     * @throws DetailException 
     */
    Long importProcess(ProcessImportReq reqProcessImportDto) throws DetailException;
    
    
   
}
