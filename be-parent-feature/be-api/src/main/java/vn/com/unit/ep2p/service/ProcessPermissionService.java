/*******************************************************************************
 * Class        ：ProcessParamService
 * Created date ：2021/01/12
 * Lasted date  ：2021/01/12
 * Author       ：SonND
 * Change log   ：2021/01/12：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.ProcessPermissionAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessPermissionUpdateInfoReq;
import vn.com.unit.workflow.dto.JpmPermissionDto;

/**
 * <p>
 * ProcessParamService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface ProcessPermissionService  {
   
    Long create(ProcessPermissionAddInfoReq processPermissionAddInfoReq) throws DetailException;

    void update(ProcessPermissionUpdateInfoReq processPermissionUpdateInfoReq) throws DetailException;

    void delete(Long processPermissionId) throws DetailException;
    
    public JpmPermissionDto save(JpmPermissionDto jpmPermissionDto);
    
    /**
     * <p>
     * Get permission dtos by process id.
     * </p>
     *
     * @author khadm
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmPermissionDto>}
     */
    List<JpmPermissionDto> getPermissionDtosByProcessId(Long processId);
    
    /**
     * <p>
     * Get jpm permission dto by permission id.
     * </p>
     *
     * @author khadm
     * @param processPermissionId
     *            type {@link Long}
     * @return {@link JpmPermissionDto}
     */
    JpmPermissionDto getJpmPermissionDtoByPermissionId(Long permissionId) throws DetailException;
}
