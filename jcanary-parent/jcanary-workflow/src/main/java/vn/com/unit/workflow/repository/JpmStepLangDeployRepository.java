/*******************************************************************************
* Class        JpmStepLangDeployRepository
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
import vn.com.unit.workflow.dto.JpmStepLangDeployDto;
import vn.com.unit.workflow.entity.JpmStepLangDeploy;

/**
 * JpmStepLangDeployRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStepLangDeployRepository extends DbRepository<JpmStepLangDeploy, Long>{
    
    /**
     * <p>
     * Gets the step lang deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the step lang deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmStepLangDeployDto> getStepLangDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * <p>
     * Gets the step lang deploy dtos by step deploy id.
     * </p>
     *
     * @param stepDeployId
     *            type {@link Long}
     * @return the step lang deploy dtos by step deploy id
     * @author KhuongTH
     */
    List<JpmStepLangDeployDto> getStepLangDeployDtosByStepDeployId(@Param("stepDeployId") Long stepDeployId);
}