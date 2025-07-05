/*******************************************************************************
 * Class        ：JpmButtonLangDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmButtonLangDeployDto;
import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.entity.JpmButtonLangDeploy;

/**
 * JpmButtonLangDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmButtonLangDeployService {

    /**
     * Get JpmButtonLangDeployDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmButtonLangDeployDto}
     * @author KhuongTH
     */
    JpmButtonLangDeployDto getJpmButtonLangDeployDtoById(Long id);

    /**
     * Check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * Save JpmButtonLangDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmButtonLangDeploy
     *            type {@link JpmButtonLangDeploy}
     * @return {@link JpmButtonLangDeploy}
     * @author KhuongTH
     */
    JpmButtonLangDeploy saveJpmButtonLangDeploy(JpmButtonLangDeploy jpmButtonLangDeploy);

    /**
     * Save JpmButtonLangDeployDto
     * 
     * @param jpmButtonLangDeployDto
     *            type {@link JpmButtonLangDeployDto}
     * @return {@link JpmButtonLangDeploy}
     * @author KhuongTH
     */
    JpmButtonLangDeploy saveJpmButtonLangDeployDto(JpmButtonLangDeployDto jpmButtonLangDeployDto);

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
    String getButtonNameInPassiveByButtonDeployIdAndLangCode(Long buttonDeployId, String langCode);

    /**
     * <p>
     * Save jpm button lang deploy dtos.
     * </p>
     *
     * @param buttonLangDeployDtos
     *            type {@link List<JpmButtonLangDeployDto>}
     * @param buttonDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveJpmButtonLangDeployDtos(List<JpmButtonLangDeployDto> buttonLangDeployDtos, Long buttonDeployId);

    /**
     * <p>
     * getButtonLangDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmButtonLangDeployDto>}
     * @author KhuongTH
     */
    List<JpmButtonLangDeployDto> getButtonLangDeployDtosByProcessDeployId(Long processDeployId);

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
    List<JpmButtonLangDeployDto> getButtonLangDeployDtosByButtonDeployId(Long buttonDeployId);
}