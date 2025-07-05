/*******************************************************************************
* Class        JpmStepLangService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmStepLangDto;
import vn.com.unit.workflow.entity.JpmStepLang;

/**
 * JpmStepLangService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStepLangService {

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
     * save JpmStepLang with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmStepLang
     *            type {@link JpmStepLang}
     * @return {@link JpmStepLang}
     * @author KhuongTH
     */
    JpmStepLang saveJpmStepLang(JpmStepLang jpmStepLang);

    /**
     * save JpmStepLangDto.
     *
     * @param jpmStepLangDto
     *            type {@link JpmStepLangDto}
     * @return {@link JpmStepLang}
     * @author KhuongTH
     */
    JpmStepLang saveJpmStepLangDto(JpmStepLangDto jpmStepLangDto);

    /**
     * <p>
     * get list StepLangDto by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStepLangDto>}
     * @author KhuongTH
     */
    List<JpmStepLangDto> getStepLangDtosByProcessId(Long processId);

    /**
     * saveStepLangDtosByStepId.
     *
     * @param stepDtos
     *            type {@link List<JpmStepLangDto>}
     * @param stepId
     *            type {@link Long}
     * @author ngannh
     */
    void saveStepLangDtosByStepId(List<JpmStepLangDto> stepDtos, Long stepId);

    /**
     * getStepLangDtosByStepId.
     *
     * @param stepId
     *            type {@link Long}
     * @return the step lang dtos by step id
     * @author ngannh
     */
    List<JpmStepLangDto> getStepLangDtosByStepId(Long stepId);

    
    /**
     * <p>
     * Delete step lang by step id.
     * </p>
     *
     * @param stepId
     *            type {@link Long}
     * @author sonnd
     */
    void deleteStepLangByStepId(Long stepId);
}