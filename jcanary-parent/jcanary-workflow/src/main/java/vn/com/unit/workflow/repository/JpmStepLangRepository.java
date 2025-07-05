/*******************************************************************************
* Class        JpmStepLangRepository
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
import vn.com.unit.workflow.dto.JpmStepLangDto;
import vn.com.unit.workflow.entity.JpmStepLang;

/**
 * JpmStepLangRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStepLangRepository extends DbRepository<JpmStepLang, Long> {

    /**
     * <p>
     * get list StepLangDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStepLangDto>}
     * @author KhuongTH
     */
    List<JpmStepLangDto> getStepLangDtosByProcessId(@Param("processId") Long processId);

    /**
     * getStepLangDtosByStepId.
     *
     * @param stepId
     *            type {@link Long}
     * @return the step lang dtos by step id
     * @author ngannh
     */
    List<JpmStepLangDto> getStepLangDtosByStepId(@Param("stepId") Long stepId);

    /**
     * <p>
     * Delete step lang by step id.
     * </p>
     *
     * @param stepId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deleteStepLangByStepId(@Param("stepId") Long stepId);
}