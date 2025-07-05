/*******************************************************************************
 * Class        ：JpmStatusLangDeployService
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：KhuongTH
 * Change log   ：2020/12/14：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmStatusLangDeployDto;
import vn.com.unit.workflow.entity.JpmStatusLangDeploy;

/**
 * JpmStatusLangDeployService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStatusLangDeployService {

    /**
     * get JpmStatusLangDeployDto by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link JpmStatusLangDeployDto}
     * @author KhuongTH
     */
    JpmStatusLangDeployDto getJpmStatusLangDeployDtoById(Long id);

    /**
     * check flag DELETED_ID by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmStatusLangDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmStatusLangDeploy
     *            type {@link JpmStatusLangDeploy}
     * @return {@link JpmStatusLangDeploy}
     * @author KhuongTH
     */
    JpmStatusLangDeploy saveJpmStatusLangDeploy(JpmStatusLangDeploy jpmStatusLangDeploy);

    /**
     * save JpmStatusLangDeployDto.
     *
     * @param jpmStatusLangDeployDto
     *            type {@link JpmStatusLangDeployDto}
     * @return {@link JpmStatusLangDeploy}
     * @author KhuongTH
     */
    JpmStatusLangDeploy saveJpmStatusLangDeployDto(JpmStatusLangDeployDto jpmStatusLangDeployDto);

    /**
     * <p>
     * Save jpm status lang deploy dtos.
     * </p>
     *
     * @param statusLangDeployDtos
     *            type {@link List<JpmStatusLangDeployDto>}
     * @param statusDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveJpmStatusLangDeployDtos(List<JpmStatusLangDeployDto> statusLangDeployDtos, Long statusDeployId);

    /**
     * <p>
     * getStatusLangDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmStatusLangDeployDto>}
     * @author KhuongTH
     */
    List<JpmStatusLangDeployDto> getStatusLangDeployDtosByProcessDeployId(Long processDeployId);
    

    /**
     * <p>Gets the status lang deploy dtos by status deploy id.</p>
     *
     * @param statusDeployId type {@link Long}
     * @return the status lang deploy dtos by status deploy id
     * @author KhuongTH
     */
    List<JpmStatusLangDeployDto> getStatusLangDeployDtosByStatusDeployId(Long statusDeployId);

}