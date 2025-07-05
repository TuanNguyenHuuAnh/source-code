/*******************************************************************************
* Class        JpmStatusLangService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.entity.JpmStatusLang;

/**
 * JpmStatusLangService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStatusLangService {

    /**
     * get JpmStatusLangDto by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link JpmStatusLangDto}
     * @author KhuongTH
     */
    JpmStatusLangDto getJpmStatusLangDtoById(Long id);

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
     * save JpmStatusLang with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmStatusLang
     *            type {@link JpmStatusLang}
     * @return {@link JpmStatusLang}
     * @author KhuongTH
     */
    JpmStatusLang saveJpmStatusLang(JpmStatusLang jpmStatusLang);

    /**
     * save JpmStatusLangDto.
     *
     * @param jpmStatusLangDto
     *            type {@link JpmStatusLangDto}
     * @return {@link JpmStatusLang}
     * @author KhuongTH
     */
    JpmStatusLang saveJpmStatusLangDto(JpmStatusLangDto jpmStatusLangDto);

    /**
     * <p>
     * get list StatusLangDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStatusLangDto>}
     * @author KhuongTH
     */
    List<JpmStatusLangDto> getStatusLangDtosByProcessId(Long processId);
    
    List<JpmStatusLangDto> getStatusLangDtosByBusinessCode(String businessCode);

    /**
     * savestatusLangDtosByStatusId.
     *
     * @param statusLangDtos
     *            type {@link List<JpmStatusLangDto>}
     * @param statusId
     *            type {@link Long}
     * @author ngannh
     */
    void saveStatusLangDtosByStatusId(List<JpmStatusLangDto> statusLangDtos, Long statusId);

    /**
     * getStatusLangDtosByStatusId.
     *
     * @param statusId
     *            type {@link Long}
     * @return the status lang dtos by status id
     * @author ngannh
     */
    List<JpmStatusLangDto> getStatusLangDtosByStatusId(Long statusId);
    
    
    /**
     * <p>
     * Delete status lang by status id.
     * </p>
     *
     * @param statusId
     *            type {@link Long}
     * @author sonnd
     */
    void deleteStatusLangByStatusId(Long statusId);

}