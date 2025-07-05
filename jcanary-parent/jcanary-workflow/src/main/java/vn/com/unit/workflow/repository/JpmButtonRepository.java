/*******************************************************************************
* Class        JpmButtonRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.entity.JpmButton;

/**
 * JpmButtonRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmButtonRepository extends DbRepository<JpmButton, Long> {

    /**
     * <p>
     * get list ButtonDtos and language by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmButtonDto>}
     * @author KhuongTH
     */
    List<JpmButtonDto> getButtonDtosByProcessId(@Param("processId") Long processId);

    /**
     * <p>
     * Gets the jpm button dto by button id.
     * </p>
     *
     * @param buttonId
     *            type {@link Long}
     * @return the jpm button dto by button id
     * @author SonND
     */
    JpmButtonDto getJpmButtonDtoByButtonId(@Param("buttonId")Long buttonId);
}