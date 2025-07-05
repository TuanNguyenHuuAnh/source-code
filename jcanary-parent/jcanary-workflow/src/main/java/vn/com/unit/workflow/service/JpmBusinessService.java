/*******************************************************************************
* Class        JpmBusinessService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmBusinessSearchDto;
import vn.com.unit.workflow.entity.JpmBusiness;

/**
 * JpmBusinessService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmBusinessService {

    /** The Constant TABLE_ALIAS_JPM_BUSINESS. */
    static final String TABLE_ALIAS_JPM_BUSINESS = "bus";

    /**
     * get JpmBusinessDto by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link JpmBusinessDto}
     * @author KhuongTH
     */
    JpmBusinessDto getJpmBusinessDtoById(Long id);

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
     * save JpmBusiness with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmBusiness
     *            type {@link JpmBusiness}
     * @return {@link JpmBusiness}
     * @author KhuongTH
     */
    JpmBusiness saveJpmBusiness(JpmBusiness jpmBusiness);

    /**
     * save JpmBusinessDto.
     *
     * @param jpmBusinessDto
     *            type {@link JpmBusinessDto}
     * @return {@link JpmBusiness}
     * @author KhuongTH
     */
    JpmBusiness saveJpmBusinessDto(JpmBusinessDto jpmBusinessDto);

    /**
     * <p>
     * count business by search condition
     * </p>
     * .
     *
     * @param jpmBusinessSearchDto
     *            type {@link JpmBusinessSearchDto}
     * @return int
     * @author KhuongTH
     */
    int countBySearchCondition(JpmBusinessSearchDto jpmBusinessSearchDto);

    /**
     * <p>
     * get list business by condition
     * </p>
     * .
     *
     * @param jpmBusinessSearchDto
     *            type {@link JpmBusinessSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JpmBusinessDto>}
     * @author KhuongTH
     */
    List<JpmBusinessDto> getBusinessDtosByCondition(JpmBusinessSearchDto jpmBusinessSearchDto, Pageable pageable);

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
    JpmBusinessDto getBusinessDtoByCodeAndCompanyId(String businessCode, Long companyId);

    /**
     * <p>
     * Get jpm business dto by business code.
     * </p>
     *
     * @param businessCode
     *            type {@link String}
     *            
     * @return {@link JpmBusinessDto}
     * @author SonND
     */
    JpmBusinessDto getJpmBusinessDtoByBusinessCode(String businessCode);
    
}