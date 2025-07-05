/*******************************************************************************
* Class        JpmStepRepository
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
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.entity.JpmStep;

/**
 * JpmStepRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStepRepository extends DbRepository<JpmStep, Long> {

    /**
     * <p>
     * getStepDtosByProcessId
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStepDto>}
     * @author KhuongTH
     */
    List<JpmStepDto> getStepDtosByProcessId(@Param("processId") Long processId);

    
    /**
     * <p>
     * Gets the step dto by step id.
     * </p>
     *
     * @param processStepId
     *            type {@link Long}
     * @return the step dto by step id
     * @author sonnd
     */
    JpmStepDto getStepDtoByStepId(@Param("processStepId")Long processStepId);
}