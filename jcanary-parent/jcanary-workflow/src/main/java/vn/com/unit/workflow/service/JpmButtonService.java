/*******************************************************************************
* Class        JpmButtonService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.entity.JpmButton;

/**
 * JpmButtonService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmButtonService {

    /**
     * get JpmButtonDto by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link JpmButtonDto}
     * @author KhuongTH
     */
    JpmButtonDto getJpmButtonDtoById(Long id);

    /**
     * check flag DELETED_ID by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmButton with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmButton
     *            type {@link JpmButton}
     * @return {@link JpmButton}
     * @author KhuongTH
     */
    JpmButton saveJpmButton(JpmButton jpmButton);

    /**
     * save JpmButtonDto.
     *
     * @param jpmButtonDto
     *            type {@link JpmButtonDto}
     * @return {@link JpmButton}
     * @author KhuongTH
     */
    JpmButton saveJpmButtonDto(JpmButtonDto jpmButtonDto);

    /**
     * <p>
     * get list ButtonDtos and language by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmButtonDto>}
     * @author KhuongTH
     */
    List<JpmButtonDto> getButtonDtosByProcessId(Long processId);


    /**
     * <p>
     * Gets the jpm button dto by button id.
     * </p>
     *
     * @param buttonId
     *            type {@link Long}
     * @return the jpm button dto by button id
     * @author SonND
     */
    JpmButtonDto getJpmButtonDtoByButtonId(Long buttonId);

    /**
     * <p>
     * Save button dtos by process id. use for import
     * </p>
     *
     * @param buttonDtos
     *            type {@link List<JpmButtonDto>}
     * @param processId
     *            type {@link Long}
     * @return {@link Map<Long,Long>} map id old and new
     * @author KhuongTH
     */
    Map<Long, Long> saveButtonDtosByProcessId(List<JpmButtonDto> buttonDtos, Long processId);

}