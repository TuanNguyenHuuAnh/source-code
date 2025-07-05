/*******************************************************************************
* Class        JpmStatusDeployRepository
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
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.entity.JpmStatusDeploy;

/**
 * JpmStatusDeployRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStatusDeployRepository extends DbRepository<JpmStatusDeploy, Long> {

    /**
     * <p>
     * Gets the status deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the status deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmStatusDeployDto> getStatusDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * <p>
     * Count status deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link int}
     * @author tantm
     */
    int countStatusDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);
    
    public JpmStatusDeployDto findStatusDeploy(@Param("docId") Long docId, @Param("languageCode") String languageCode);
    
    public JpmStatusDeployDto findStatusDeployByStatusCode(@Param("docId") Long docId,
            @Param("statusCode") String statusCode, @Param("languageCode") String languageCode);
}