/*******************************************************************************
* Class        JpmParamConfigDeployRepository
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
import vn.com.unit.workflow.dto.JpmParamConfigDeployDto;
import vn.com.unit.workflow.entity.JpmParamConfigDeploy;

/**
 * JpmParamConfigDeployRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmParamConfigDeployRepository extends DbRepository<JpmParamConfigDeploy, Long> {

    /**
     * <p>
     * Gets the param config deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the param config deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmParamConfigDeployDto> getParamConfigDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * <p>
     * Gets the param config deploy dtos by param deploy id.
     * </p>
     *
     * @param paramDeployId
     *            type {@link Long}
     * @return the param config deploy dtos by param deploy id
     * @author KhuongTH
     */
    List<JpmParamConfigDeployDto> getParamConfigDeployDtosByParamDeployId(@Param("paramDeployId") Long paramDeployId);
}