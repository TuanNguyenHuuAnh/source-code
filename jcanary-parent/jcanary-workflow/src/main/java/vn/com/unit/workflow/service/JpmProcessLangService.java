/*******************************************************************************
* Class        JpmProcessLangService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import vn.com.unit.workflow.dto.JpmProcessLangDto;
import vn.com.unit.workflow.entity.JpmProcessLang;

/**
 * JpmProcessLangService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmProcessLangService {

    /**
     * get JpmProcessLangDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmProcessLangDto}
     * @author KhuongTH
     */
    JpmProcessLangDto getJpmProcessLangDtoById(Long id);

    /**
     * check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmProcessLang with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmProcessLang
     *            type {@link JpmProcessLang}
     * @return {@link JpmProcessLang}
     * @author KhuongTH
     */
    JpmProcessLang saveJpmProcessLang(JpmProcessLang jpmProcessLang);

    /**
     * save JpmProcessLangDto
     * 
     * @param jpmProcessLangDto
     *            type {@link JpmProcessLangDto}
     * @return {@link JpmProcessLang}
     * @author KhuongTH
     */
    JpmProcessLang saveJpmProcessLangDto(JpmProcessLangDto jpmProcessLangDto);

    /**
     * <p>
     * get list process language by process id
     * </p>
     * 
     * @param processId
     *            type {@link Long} is not null
     * @return {@link List<JpmProcessLangDto>}
     * @author KhuongTH
     */
    List<JpmProcessLangDto> getJpmProcessLangDtosByProcessId(@NotNull Long processId);

    /**
     * saveProcessLangDtosByProcessId
     * 
     * @param processLangDtos
     * @param processId
     * @return
     * @author ngannh
     */
    void saveProcessLangDtosByProcessId(List<JpmProcessLangDto> processLangDtos, Long processId);

    /**
     * <p>
     * Delete jpm process lang dtos by process id.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @author KhuongTH
     */
    void deleteJpmProcessLangDtosByProcessId(Long processId);
}