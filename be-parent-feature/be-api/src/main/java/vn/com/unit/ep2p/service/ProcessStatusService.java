/*******************************************************************************
 * Class        ：ProcessStatusService
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2021/01/13：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.ProcessStatusAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStatusUpdateInfoReq;
import vn.com.unit.workflow.dto.JpmStatusDto;

/**
 * <p>
 * ProcessStatusService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface ProcessStatusService  {
    
    Long create(ProcessStatusAddInfoReq processStatusAddInfoReq) throws DetailException;

    void update(ProcessStatusUpdateInfoReq processStatusUpdateInfoReq) throws DetailException;

    void delete(Long processStatusId) throws DetailException;
    
    public JpmStatusDto save(JpmStatusDto jpmStatusDto);
    
    /**
     * <p>
     * Get status dtos by process id.
     * </p>
     *
     * @author khadm
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStatusDto>}
     */
    List<JpmStatusDto> getStatusDtosByProcessId(Long processId);
    
    List<JpmStatusDto> getStatusDtosByBusinessCode(String businessCode);
    
    /**
     * <p>
     * Get status dto by status id.
     * </p>
     *
     * @author khadm
     * @param statusId
     *            type {@link Long}
     * @return {@link JpmStatusDto}
     */
    JpmStatusDto getStatusDtoByStatusId(Long statusId) throws DetailException;
}
