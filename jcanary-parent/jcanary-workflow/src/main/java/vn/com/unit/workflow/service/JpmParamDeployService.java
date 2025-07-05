/*******************************************************************************
 * Class        ：JpmParamDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmParamDeployDto;
import vn.com.unit.workflow.dto.LanguageMapDto;
import vn.com.unit.workflow.entity.JpmParamDeploy;

/**
 * JpmParamDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmParamDeployService {

    /**
     * get JpmParamDeployDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmParamDeployDto}
     * @author KhuongTH
     */
    JpmParamDeployDto getJpmParamDeployDtoById(Long id);

    /**
     * check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmParamDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmParamDeploy
     *            type {@link JpmParamDeploy}
     * @return {@link JpmParamDeploy}
     * @author KhuongTH
     */
    JpmParamDeploy saveJpmParamDeploy(JpmParamDeploy jpmParamDeploy);

    /**
     * save JpmParamDeployDto
     * 
     * @param jpmParamDeployDto
     *            type {@link JpmParamDeployDto}
     * @return {@link JpmParamDeploy}
     * @author KhuongTH
     */
    JpmParamDeploy saveJpmParamDeployDto(JpmParamDeployDto jpmParamDeployDto);

    /**
     * get list of NameInPassive by buttonId
     * 
     * @param buttonId
     *            type {@link Long}: id of button in process config
     * @return {@link List<LanguageMapDto>}
     * @author KhuongTH
     */
    List<LanguageMapDto> getListNameInPassiveByButtonId(Long buttonId);

    /**
     * <p>
     * save list JpmParamDeployDtos by processDeployId
     * </p>
     * 
     * @param paramDeployDtos
     *            type {@link List<JpmParamDeployDto>}
     * @param processDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveJpmParamDeployDtos(List<JpmParamDeployDto> paramDeployDtos, Long processDeployId);

    /**
     * <p>
     * getParamDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmParamDeployDto>}
     * @author KhuongTH
     */
    List<JpmParamDeployDto> getParamDeployDtosByProcessDeployId(Long processDeployId);

}