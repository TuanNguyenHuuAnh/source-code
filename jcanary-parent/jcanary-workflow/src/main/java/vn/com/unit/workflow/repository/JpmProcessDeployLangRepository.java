/*******************************************************************************
* Class        JpmProcessDeployLangRepository
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
import vn.com.unit.workflow.dto.JpmProcessDeployLangDto;
import vn.com.unit.workflow.entity.JpmProcessDeployLang;

/**
 * JpmProcessDeployLangRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmProcessDeployLangRepository extends DbRepository<JpmProcessDeployLang, Long>{
    
    /**
     * <p>
     * Gets the jpm process deploy lang dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the jpm process deploy lang dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmProcessDeployLangDto> getJpmProcessDeployLangDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);
}