/*******************************************************************************
* Class        JpmButtonForStepRepository
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
import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.entity.JpmButtonForStep;

/**
 * JpmButtonForStepRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmButtonForStepRepository extends DbRepository<JpmButtonForStep, Long> {

    /**
     * <p>
     * get list ButtonForStepDtos by process id
     * </p>
     * 
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmButtonForStepDto>}
     * @author KhuongTH
     */
    List<JpmButtonForStepDto> getButtonForStepDtosByProcessId(@Param("processId") Long processId);

    /**
     * getButtonForStepDtosByStepId
     * 
     * @param stepId
     * @return
     * @author ngannh
     */
    List<JpmButtonForStepDto> getButtonForStepDtosByStepId(@Param("stepId") Long stepId);

    /**
     * <p>
     * Delete button for step by step id.
     * </p>
     *
     * @param stepId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deleteButtonForStepByStepId(@Param("stepId") Long stepId);
}