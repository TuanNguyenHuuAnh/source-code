/*******************************************************************************
* Class        JpmButtonLangService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.entity.JpmButtonLang;

/**
 * JpmButtonLangService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmButtonLangService {

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
     * save JpmButtonLang with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmButtonLang
     *            type {@link JpmButtonLang}
     * @return {@link JpmButtonLang}
     * @author KhuongTH
     */
    JpmButtonLang saveJpmButtonLang(JpmButtonLang jpmButtonLang);

    /**
     * save JpmButtonLangDto.
     *
     * @param jpmButtonLangDto
     *            type {@link JpmButtonLangDto}
     * @return {@link JpmButtonLang}
     * @author KhuongTH
     */
    JpmButtonLang saveJpmButtonLangDto(JpmButtonLangDto jpmButtonLangDto);

    /**
     * <p>
     * get list ButtonLangDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmButtonLangDto>}
     * @author KhuongTH
     */
    List<JpmButtonLangDto> getButtonLangDtosByProcessId(Long processId);

    /**
     * saveButtonLangsByButtonId.
     *
     * @param jpmButtonLangDto
     *            type {@link List<JpmButtonLangDto>}
     * @param butonId
     *            type {@link Long}
     * @author ngannh
     */
    void saveButtonLangsByButtonId(@NotNull List<JpmButtonLangDto> jpmButtonLangDto, @NotNull Long butonId);

    /**
     * getButtonLangDtosByButtonId.
     *
     * @param buttonId
     *            type {@link Long}
     * @return the button lang dtos by button id
     * @author ngannh
     */
    List<JpmButtonLangDto> getButtonLangDtosByButtonId(Long buttonId);
    
    
    /**
     * <p>
     * Delete button langs by button id.
     * </p>
     *
     * @param buttonId
     *            type {@link Long}
     * @author sonnd
     */
    void deleteButtonLangsByButtonId(Long buttonId);

}