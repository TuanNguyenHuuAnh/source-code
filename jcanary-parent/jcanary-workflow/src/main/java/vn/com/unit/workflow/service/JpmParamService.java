/*******************************************************************************
* Class        JpmParamService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.entity.JpmParam;

/**
 * JpmParamService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmParamService {

    /**
     * get JpmParamDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmParamDto}
     * @author KhuongTH
     */
    JpmParamDto getJpmParamDtoById(Long id);

    /**
     * check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmParam with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmParam
     *            type {@link JpmParam}
     * @return {@link JpmParam}
     * @author KhuongTH
     */
    JpmParam saveJpmParam(JpmParam jpmParam);

    /**
     * save JpmParamDto
     * 
     * @param jpmParamDto
     *            type {@link JpmParamDto}
     * @return {@link JpmParam}
     * @author KhuongTH
     */
    JpmParam saveJpmParamDto(JpmParamDto jpmParamDto);

    /**
     * <p>
     * get list ParamDtos by process id
     * </p>
     * 
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmParamDto>}
     * @author KhuongTH
     */
    List<JpmParamDto> getParamDtosByProcessId(Long processId);
    
    /**
     * <p>
     * Save param dtos by process id.
     * </p>
     *
     * @param paramDtos
     *            type {@link List<JpmParamDto>}
     * @param processId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveParamDtosByProcessId(List<JpmParamDto> paramDtos, Long processId);
    
    /**
     * <p>
     * Gets the jpm param dto by param id.
     * </p>
     *
     * @param paramId
     *            type {@link Long}
     * @return the jpm param dto by param id
     * @author SonND
     */
    JpmParamDto getJpmParamDtoByParamId(Long paramId);

}