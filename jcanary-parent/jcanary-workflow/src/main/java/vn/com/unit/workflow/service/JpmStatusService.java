/*******************************************************************************
* Class        JpmStatusService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.entity.JpmStatus;

/**
 * JpmStatusService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStatusService {

    /**
     * get JpmStatusDto by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link JpmStatusDto}
     * @author KhuongTH
     */
    JpmStatusDto getJpmStatusDtoById(Long id);

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
     * save JpmStatus with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmStatus
     *            type {@link JpmStatus}
     * @return {@link JpmStatus}
     * @author KhuongTH
     */
    JpmStatus saveJpmStatus(JpmStatus jpmStatus);

    /**
     * save JpmStatusDto.
     *
     * @param jpmStatusDto
     *            type {@link JpmStatusDto}
     * @return {@link JpmStatus}
     * @author KhuongTH
     */
    JpmStatus saveJpmStatusDto(JpmStatusDto jpmStatusDto);

    /**
     * <p>
     * get list StatusDtos and language by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStatusDto>}
     * @author KhuongTH
     */
    List<JpmStatusDto> getStatusDtosByProcessId(Long processId);
    
    List<JpmStatusDto> getStatusDtosByBusinessCode(String businessCode);

    /**
     * <p>
     * Gets the status dto by status id.
     * </p>
     *
     * @param statusId
     *            type {@link Long}
     * @return the status dto by status id
     * @author SonND
     */
    JpmStatusDto getStatusDtoByStatusId(Long statusId);

    /**
     * savestatusDtosByProcessId.
     *
     * @param statusDtos
     *            type {@link List<JpmStatusDto>}
     * @param processId
     *            type {@link Long}
     * @return {@link Map<Long,Long>}
     * @author ngannh
     */
    Map<Long, Long> saveStatusDtosByProcessId(List<JpmStatusDto> statusDtos, Long processId);

}