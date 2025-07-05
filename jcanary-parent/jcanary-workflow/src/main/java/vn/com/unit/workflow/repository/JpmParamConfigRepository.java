/*******************************************************************************
* Class        JpmParamConfigRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.entity.JpmParamConfig;

/**
 * JpmParamConfigRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmParamConfigRepository extends DbRepository<JpmParamConfig, Long> {

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
    List<JpmParamConfigDto> getParamConfigDtosByProcessId(@Param("processId") Long processId);

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
    List<JpmParamConfigDto> getParamConfigDtosByProcessIdAndParamId(@Param("processId") Long processId, @Param("paramId") Long paramId);

    /**
     * <p>
     * Delete jpm param config by param id.
     * </p>
     *
     * @param paramId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deleteJpmParamConfigByParamId(@Param("paramId") Long paramId);
}