/*******************************************************************************
* Class        JpmStatusLangDeployRepository
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
import vn.com.unit.workflow.dto.JpmStatusLangDeployDto;
import vn.com.unit.workflow.entity.JpmStatusLangDeploy;

/**
 * JpmStatusLangDeployRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStatusLangDeployRepository extends DbRepository<JpmStatusLangDeploy, Long> {

    /**
     * <p>
     * Gets the status lang deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the status lang deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmStatusLangDeployDto> getStatusLangDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * <p>
     * Gets the status lang deploy dtos by status deploy id.
     * </p>
     *
     * @param statusDeployId
     *            type {@link Long}
     * @return the status lang deploy dtos by status deploy id
     * @author KhuongTH
     */
    List<JpmStatusLangDeployDto> getStatusLangDeployDtosByStatusDeployId(@Param("statusDeployId") Long statusDeployId);
}