/*******************************************************************************
 * Class        ：ProcessButtonService
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2021/01/13：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.ProcessButtonAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessButtonUpdateInfoReq;
import vn.com.unit.workflow.dto.JpmButtonDto;

/**
 * <p>
 * ProcessButtonService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface ProcessButtonService  {
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param ProcessButtonAddInfoReq
     *            type {@link ProcessButtonAddInfoReq}
     * @return {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    Long create(ProcessButtonAddInfoReq ProcessButtonAddInfoReq) throws DetailException;

    /**
     * <p>
     * Update.
     * </p>
     *
     * @param processButtonUpdateInfoReq
     *            type {@link ProcessButtonUpdateInfoReq}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void update(ProcessButtonUpdateInfoReq processButtonUpdateInfoReq) throws DetailException;

    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param processButtonId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void delete(Long processButtonId) throws DetailException;
    
    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jpmButtonDto
     *            type {@link JpmButtonDto}
     * @return {@link JpmButtonDto}
     * @author SonND
     */
    public JpmButtonDto save(JpmButtonDto jpmButtonDto);
    
    /**
     * <p>
     * Get button dtos by process id.
     * </p>
     *
     * @author khadm
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmButtonDto>}
     */
    List<JpmButtonDto> getButtonDtosByProcessId(Long processId);
    
    /**
     * <p>
     * Get jpm button dto by button id.
     * </p>
     *
     * @author khadm
     * @param buttonId
     *            type {@link Long}
     * @return {@link JpmButtonDto}
     */
    JpmButtonDto getJpmButtonDtoByButtonId(Long buttonId) throws DetailException;
}
