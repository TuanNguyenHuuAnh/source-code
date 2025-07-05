/*******************************************************************************
* Class        JpmProcessRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmProcessSearchDto;
import vn.com.unit.workflow.entity.JpmProcess;

/**
 * JpmProcessRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmProcessRepository extends DbRepository<JpmProcess, Long> {

    /**
     * <p>
     * count process by search condition
     * </p>
     * .
     *
     * @param searchDto
     *            type {@link JpmProcessSearchDto}
     * @return int
     * @author KhuongTH
     */
    int countBySearchCondition(@Param("searchDto") JpmProcessSearchDto searchDto);

    /**
     * <p>
     * get process list by condition
     * </p>
     * .
     *
     * @param searchDto
     *            type {@link JpmProcessSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JpmProcessDto>}
     * @author KhuongTH
     */
    Page<JpmProcessDto> getProcessDtosByCondition(@Param("searchDto") JpmProcessSearchDto searchDto, Pageable pageable);

    /**
     * <p>
     * Gets the process dto by code and company id.
     * </p>
     *
     * @param processCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return the process dto by code and company id
     * @author KhuongTH
     */
    JpmProcessDto getProcessDtoByCodeAndCompanyId(@Param("processCode") String processCode, @Param("companyId") Long companyId);
    
    JpmProcessDto getProcessDtoByBusinessIdAndCompanyId(@Param("businessId") Long businessId, @Param("companyId") Long companyId);

    JpmProcessDto getJpmProcessDtoByProcessId(@Param("processId") Long processId);
}