/*******************************************************************************
 * Class        ：JpmProcessDeployLangService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import vn.com.unit.workflow.dto.JpmProcessDeployLangDto;
import vn.com.unit.workflow.entity.JpmProcessDeployLang;

/**
 * JpmProcessDeployLangService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmProcessDeployLangService {

    /**
     * get JpmProcessDeployLangDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmProcessDeployLangDto}
     * @author KhuongTH
     */
    JpmProcessDeployLangDto getJpmProcessDeployLangDtoById(Long id);

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
     * save JpmProcessDeployLang with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmProcessDeployLang
     *            type {@link JpmProcessDeployLang}
     * @return {@link JpmProcessDeployLang}
     * @author KhuongTH
     */
    JpmProcessDeployLang saveJpmProcessDeployLang(JpmProcessDeployLang jpmProcessDeployLang);

    /**
     * save JpmProcessDeployLangDto
     * 
     * @param jpmProcessDeployLangDto
     *            type {@link JpmProcessDeployLangDto}
     * @return {@link JpmProcessDeployLang}
     * @author KhuongTH
     */
    JpmProcessDeployLang saveJpmProcessDeployLangDto(JpmProcessDeployLangDto jpmProcessDeployLangDto);

    /**
     * <p>
     * save list JpmProcessDeployLangDtos by process deploy id
     * </p>
     * 
     * @param processDeployLangDtos
     *            type {@link List<JpmProcessDeployLangDto>}
     * @param processDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveJpmProcessDeployLangDtos(List<JpmProcessDeployLangDto> processDeployLangDtos, Long processDeployId);

    /**
     * <p>
     * getJpmProcessDeployLangDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmProcessDeployLangDto>}
     * @author KhuongTH
     */
    List<JpmProcessDeployLangDto> getJpmProcessDeployLangDtosByProcessDeployId(@NotNull Long processDeployId);

}