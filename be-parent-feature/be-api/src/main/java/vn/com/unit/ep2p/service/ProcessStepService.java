/*******************************************************************************
 * Class        ：ProcessStepService
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2021/01/13：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.ProcessStepAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStepUpdateInfoReq;
import vn.com.unit.workflow.dto.JpmStepDto;

/**
 * <p>
 * ProcessStepService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface ProcessStepService  {
    
    Long create(ProcessStepAddInfoReq processStepAddInfoReq) throws DetailException;

    void update(ProcessStepUpdateInfoReq processStepUpdateInfoReq) throws DetailException;

    void delete(Long processStepId) throws DetailException;
    
    public JpmStepDto save(JpmStepDto jpmStepDto);
}
