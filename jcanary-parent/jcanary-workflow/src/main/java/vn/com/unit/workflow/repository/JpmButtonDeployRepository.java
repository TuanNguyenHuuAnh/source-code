/*******************************************************************************
* Class        JpmButtonDeployRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.entity.JpmButtonDeploy;

/**
 * JpmButtonDeployRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmButtonDeployRepository extends DbRepository<JpmButtonDeploy, Long> {

    /**
     * <p>
     * Gets the button deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the button deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmButtonDeployDto> getButtonDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * <p>
     * Get action dto by process deploy id and button id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param buttonDeployId
     *            type {@link Long}
     * @return {@link ActionDto}
     * @author tantm
     */
    ActionDto getActionDtoByProcessDeployIdAndButtonDeployId(@Param("processDeployId") Long processDeployId, @Param("buttonDeployId") Long buttonDeployId, @Param("dbType") String dbType);

	/**
	 * @param buttonText
	 * @param docId
	 * @return
	 */
	JpmButtonDeployDto getJpmButtonDeployDtoByButtonTextAndProcessDeployId(@Param("buttonText")String buttonText, @Param("processDeployId")Long processDeployId);
 
}