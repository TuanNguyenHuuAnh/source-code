/*******************************************************************************
 * Class        ：JpmStepLangDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmStepLangDeployDto;
import vn.com.unit.workflow.entity.JpmStepLangDeploy;

/**
 * JpmStepLangDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStepLangDeployService {

    /**
     * save JpmStepLangDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmStepLangDeploy
     *            type {@link JpmStepLangDeploy}
     * @return {@link JpmStepLangDeploy}
     * @author KhuongTH
     */
    JpmStepLangDeploy saveJpmStepLangDeploy(JpmStepLangDeploy jpmStepLangDeploy);

    /**
     * save JpmStepLangDeployDto
     * 
     * @param jpmStepLangDeployDto
     *            type {@link JpmStepLangDeployDto}
     * @return {@link JpmStepLangDeploy}
     * @author KhuongTH
     */
    JpmStepLangDeploy saveJpmStepLangDeployDto(JpmStepLangDeployDto jpmStepLangDeployDto);

    /**
     * <p>
     * save list JpmStepLangDeployDto by step deploy id
     * </p>
     * 
     * @param stepLangDeployDtos
     *            type {@link List<JpmStepLangDeployDto>}
     * @param stepDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveJpmStepLangDeployDtos(List<JpmStepLangDeployDto> stepLangDeployDtos, Long stepDeployId);

    /**
     * <p>
     * getStepLangDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmStepLangDeployDto>}
     * @author KhuongTH
     */
    List<JpmStepLangDeployDto> getStepLangDeployDtosByProcessDeployId(Long processDeployId);

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
    List<JpmStepLangDeployDto> getStepLangDeployDtosByStepDeployId(Long stepDeployId);

}