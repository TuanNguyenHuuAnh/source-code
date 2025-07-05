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
import vn.com.unit.ep2p.core.req.dto.ProcessParamInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessParamUpdateInfoReq;
import vn.com.unit.workflow.dto.JpmParamDto;

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
public interface ProcessParamService  {
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param processParamInfoReq
     *            type {@link ProcessParamInfoReq}
     * @return {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    Long create(ProcessParamInfoReq processParamInfoReq) throws DetailException;

    /**
     * <p>
     * Update.
     * </p>
     *
     * @param processParamUpdateInfoReq
     *            type {@link ProcessParamUpdateInfoReq}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    void update(ProcessParamUpdateInfoReq processParamUpdateInfoReq) throws DetailException;

    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param processParamId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    void delete(Long processParamId) throws DetailException;
    
    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jpmParamDto
     *            type {@link JpmParamDto}
     * @return {@link JpmParamDto}
     * @author sonnd
     */
    public JpmParamDto save(JpmParamDto jpmParamDto);
    
    
    /**
     * <p>
     * Get param dtos by process id.
     * </p>
     *
     * @author khadm
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmParamDto>}
     */
    List<JpmParamDto> getParamDtosByProcessId(Long processId);
    
    /**
     * <p>
     * Get jpm param dto by param id.
     * </p>
     *
     * @author khadm
     * @param paramId
     *            type {@link Long}
     * @return {@link JpmParamDto}
     */
    JpmParamDto getJpmParamDtoByParamId(Long paramId)throws DetailException;
}
