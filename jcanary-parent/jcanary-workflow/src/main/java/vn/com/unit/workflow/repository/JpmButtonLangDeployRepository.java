/*******************************************************************************
* Class        JpmButtonLangDeployRepository
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
import vn.com.unit.workflow.dto.JpmButtonLangDeployDto;
import vn.com.unit.workflow.entity.JpmButtonLangDeploy;

/**
 * JpmButtonLangDeployRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmButtonLangDeployRepository extends DbRepository<JpmButtonLangDeploy, Long> {

    /**
     * Get ButtonNameInPassive by buttonDeployId and langCode
     * 
     * @param buttonDeployId
     *            type {@link Long}
     * @param langCode
     *            type {@link String}
     * @return ButtonNameInPassive
     * @author tantm
     */
    String getButtonNameInPassiveByButtonDeployIdAndLangCode(@Param("buttonDeployId") Long buttonDeployId,
            @Param("langCode") String langCode);

    /**
     * <p>
     * Gets the button lang deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the button lang deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmButtonLangDeployDto> getButtonLangDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * <p>
     * Gets the button lang deploy dtos by button deploy id.
     * </p>
     *
     * @param buttonDeployId
     *            type {@link Long}
     * @return the button lang deploy dtos by button deploy id
     * @author KhuongTH
     */
    List<JpmButtonLangDeployDto> getButtonLangDeployDtosByButtonDeployId(@Param("buttonDeployId") Long buttonDeployId);
}