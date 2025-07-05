/*******************************************************************************
* Class        JpmBusinessRepository
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
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmBusinessSearchDto;
import vn.com.unit.workflow.entity.JpmBusiness;

/**
 * JpmBusinessRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmBusinessRepository extends DbRepository<JpmBusiness, Long> {

    /**
     * <p>
     * count business by search condition
     * </p>
     * .
     *
     * @param searchDto
     *            type {@link JpmBusinessSearchDto}
     * @return int
     * @author KhuongTH
     */
    int countBySearchCondition(@Param("searchDto") JpmBusinessSearchDto searchDto);

    /**
     * <p>
     * get business list by condition
     * </p>
     * .
     *
     * @param searchDto
     *            type {@link JpmBusinessSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JpmBusinessDto>}
     * @author KhuongTH
     */
    Page<JpmBusinessDto> getBusinessDtosByCondition(@Param("searchDto") JpmBusinessSearchDto searchDto, Pageable pageable);
    
    /**
     * <p>
     * Gets the business dto by code and company id.
     * </p>
     *
     * @param businessCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return the business dto by code and company id
     * @author KhuongTH
     */
    JpmBusinessDto getBusinessDtoByCodeAndCompanyId(@Param("businessCode") String businessCode, @Param("companyId") Long companyId);

    /**
     * <p>
     * Get jpm business dto by business code.
     * </p>
     *
     * @param businessCode
     *            type {@link String}
     * @return {@link JpmBusinessDto}
     * @author SonND
     */
    JpmBusinessDto getJpmBusinessDtoByBusinessCode(@Param("businessCode")String businessCode);
}