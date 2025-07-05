/*******************************************************************************
* Class        JpmStatusRepository
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
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.entity.JpmStatus;

/**
 * JpmStatusRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStatusRepository extends DbRepository<JpmStatus, Long> {

    /**
     * <p>
     * get list StatusDtos and language by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStatusDto>}
     * @author KhuongTH
     */
    List<JpmStatusDto> getStatusDtosByProcessId(@Param("processId") Long processId);
    
    List<JpmStatusDto> findStatusDtosByBusinessCode(@Param("businessCode") String businessCode);

    
    /**
     * <p>
     * Gets the status dto by status id.
     * </p>
     *
     * @param statusId
     *            type {@link Long}
     * @return the status dto by status id
     * @author SonND
     */
    JpmStatusDto getStatusDtoByStatusId(@Param("statusId")Long statusId);
}