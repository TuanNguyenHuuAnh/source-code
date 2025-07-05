/*******************************************************************************
* Class        JpmParamRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.entity.JpmParam;

/**
 * JpmParamRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmParamRepository extends DbRepository<JpmParam, Long> {

    /**
     * <p>
     * get list ParamDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmParamDto>}
     * @author KhuongTH
     */
    List<JpmParamDto> getParamDtosByProcessId(@Param("processId") Long processId);
    
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
    JpmParamDto getJpmParamDtoByParamId(@Param("paramId") Long paramId);
}