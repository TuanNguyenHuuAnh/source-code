/*******************************************************************************
* Class        JpmProcessDmnDeployRepository
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmProcessDmnDeployDto;
import vn.com.unit.workflow.entity.JpmProcessDmnDeploy;

/**
 * JpmProcessDmnDeployRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Repository
public interface JpmProcessDmnDeployRepository extends DbRepository<JpmProcessDmnDeploy, Long> {

    /**
     * <p>
     * Gets the jpm process dmn deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the jpm process dmn deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmProcessDmnDeployDto> getJpmProcessDmnDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);
}