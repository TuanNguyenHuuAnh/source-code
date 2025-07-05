/*******************************************************************************
* Class        JpmProcessDmnService
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmProcessDmnDto;
import vn.com.unit.workflow.entity.JpmProcessDmn;

/**
 * JpmProcessDmnService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmProcessDmnService {

    /**
     * get JpmProcessDmnDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmProcessDmnDto}
     * @author KhuongTH
     */
    JpmProcessDmnDto getJpmProcessDmnDtoById(Long id);

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
     * save JpmProcessDmn with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmProcessDmn
     *            type {@link JpmProcessDmn}
     * @return {@link JpmProcessDmn}
     * @author KhuongTH
     */
    JpmProcessDmn saveJpmProcessDmn(JpmProcessDmn jpmProcessDmn);

    /**
     * save JpmProcessDmnDto
     * 
     * @param jpmProcessDmnDto
     *            type {@link JpmProcessDmnDto}
     * @return {@link JpmProcessDmn}
     * @author KhuongTH
     */
    JpmProcessDmn saveJpmProcessDmnDto(JpmProcessDmnDto jpmProcessDmnDto);

    /**
     * <p>
     * Gets the jpm process dmn dtos by process id.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @return the jpm process dmn dtos by process id
     * @author KhuongTH
     */
    List<JpmProcessDmnDto> getJpmProcessDmnDtosByProcessId(Long processId);

}