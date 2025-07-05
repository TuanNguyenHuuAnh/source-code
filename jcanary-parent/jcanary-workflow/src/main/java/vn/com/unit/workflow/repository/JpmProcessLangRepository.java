/*******************************************************************************
* Class        JpmProcessLangRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmProcessLangDto;
import vn.com.unit.workflow.entity.JpmProcessLang;

/**
 * JpmProcessLangRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmProcessLangRepository extends DbRepository<JpmProcessLang, Long> {

    /**
     * <p>
     * get list process language by process id
     * </p>
     * 
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmProcessLangDto>}
     * @author KhuongTH
     */
    List<JpmProcessLangDto> getJpmProcessLangDtosByProcessId(@Param("processId") Long processId);

    /**
     * <p>
     * Delete jpm process lang dtos by process id.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @author KhuongTH
     */
    @Modifying
    void deleteJpmProcessLangDtosByProcessId(@Param("processId") Long processId);
}