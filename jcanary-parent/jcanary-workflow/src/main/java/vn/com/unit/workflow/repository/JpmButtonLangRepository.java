/*******************************************************************************
* Class        JpmButtonLangRepository
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
import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.entity.JpmButtonLang;

/**
 * JpmButtonLangRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmButtonLangRepository extends DbRepository<JpmButtonLang, Long>{

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
    List<JpmButtonLangDto> getButtonLangDtosByProcessId(@Param("processId") Long processId);

    /**
     * getButtonLangDtosByButtonId.
     *
     * @param buttonId
     *            type {@link Long}
     * @return the button lang dtos by button id
     * @author ngannh
     */
    List<JpmButtonLangDto> getButtonLangDtosByButtonId(@Param("buttonId") Long buttonId);

    
   
    /**
     * <p>
     * Delete button langs by button id.
     * </p>
     *
     * @param buttonId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deleteButtonLangsByButtonId(@Param("buttonId") Long buttonId);
}