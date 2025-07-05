/*******************************************************************************
* Class        JpmPermissionRepository
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
import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.entity.JpmPermission;

/**
 * JpmPermissionRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmPermissionRepository extends DbRepository<JpmPermission, Long> {

    /**
     * <p>
     * get list PermissionDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmPermissionDto>}
     * @author KhuongTH
     */
    List<JpmPermissionDto> getPermissionDtosByProcessId(@Param("processId") Long processId);

    
    /**
     * <p>
     * Gets the jpm permission dto by permission id.
     * </p>
     *
     * @param processPermissionId
     *            type {@link Long}
     * @return the jpm permission dto by permission id
     * @author SonND
     */
    JpmPermissionDto getJpmPermissionDtoByPermissionId(@Param("processPermissionId")Long processPermissionId);
   
    /**
     * <p>Gets the max permission code by process.</p>
     *
     * @param processId type {@link Long}
     * @return the max permission code by process
     * @author KhuongTH
     */
    int getMaxPermissionCodeByProcess(@Param("processId") Long processId);
}