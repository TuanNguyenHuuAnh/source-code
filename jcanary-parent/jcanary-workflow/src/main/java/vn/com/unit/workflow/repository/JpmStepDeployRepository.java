/*******************************************************************************
 * Class        ：JpmStepDeployRepository
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.entity.JpmStepDeploy;

/**
 * JpmStepDeployRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStepDeployRepository extends DbRepository<JpmStepDeploy, Long> {

    /**
     * Get first JpmStepDeploy by processDeployId
     * 
     * @param processDeployId
     *            type {@link Long}: id of process deploy
     * @return {@link JpmStepDeploy}
     * @author KhuongTH
     */
    JpmStepDeploy getJpmStepDeployFirstByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * Get JpmStepDeployDto by processDeployId and stepCode
     * 
     * @param processDeployId
     *            type Long
     * @param stepCode
     *            type String
     * @return {@link JpmStepDeployDto}
     * @author KhoaNA
     */
    JpmStepDeployDto getJpmStepDeployDtoByProcessDeployIdAndStepCode(@Param("processDeployId") Long processDeployId,
            @Param("stepCode") String stepCode);

    /**
     * <p>
     * Gets the step deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the step deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmStepDeployDto> getStepDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);
}