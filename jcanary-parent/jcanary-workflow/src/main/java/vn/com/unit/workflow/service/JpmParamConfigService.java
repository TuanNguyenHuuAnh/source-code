/*******************************************************************************
* Class        JpmParamConfigService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.entity.JpmParamConfig;

/**
 * JpmParamConfigService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmParamConfigService {

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
     * save JpmParamConfig with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmParamConfig
     *            type {@link JpmParamConfig}
     * @return {@link JpmParamConfig}
     * @author KhuongTH
     */
    JpmParamConfig saveJpmParamConfig(JpmParamConfig jpmParamConfig);

    /**
     * save JpmParamConfigDto.
     *
     * @param jpmParamConfigDto
     *            type {@link JpmParamConfigDto}
     * @return {@link JpmParamConfig}
     * @author KhuongTH
     */
    JpmParamConfig saveJpmParamConfigDto(JpmParamConfigDto jpmParamConfigDto);

    /**
     * <p>
     * get list ParamConfigDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmParamConfigDto>}
     * @author KhuongTH
     */
    List<JpmParamConfigDto> getParamConfigDtosByProcessId(Long processId);

    /**
     * <p>
     * Save param config dtos by param id.
     * </p>
     *
     * @param paramConfigDtos
     *            type {@link List<JpmParamConfigDto>}
     * @param paramId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveParamConfigDtosByParamId(List<JpmParamConfigDto> paramConfigDtos, Long paramId);

    /**
     * <p>
     * Gets the param config dtos by process id and param id.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @param paramId
     *            type {@link Long}
     * @return the param config dtos by process id and param id
     * @author SonND
     */
    List<JpmParamConfigDto> getParamConfigDtosByProcessIdAndParamId(Long processId, Long paramId);

    /**
     * saveJpmParamConfigDtosByProcessId.
     *
     * @param paramConfigDtos
     *            type {@link List<JpmParamConfigDto>}
     * @param processId
     *            type {@link Long}
     * @param paramId
     *            type {@link Long}
     * @return {@link Map<Long,Long>}
     * @author ngannh
     */
    void saveJpmParamConfigDtosByProcessId(List<JpmParamConfigDto> paramConfigDtos, Long processId, Long paramId);

    /**
     * <p>
     * Delete jpm param config by param id.
     * </p>
     *
     * @param paramId
     *            type {@link Long}
     * @author sonnd
     */
    void deleteJpmParamConfigByParamId(Long paramId);
}