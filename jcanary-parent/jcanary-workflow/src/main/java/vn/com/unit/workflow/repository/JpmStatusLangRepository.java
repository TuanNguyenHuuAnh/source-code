/*******************************************************************************
* Class        JpmStatusLangRepository
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
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.entity.JpmStatusLang;

/**
 * JpmStatusLangRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStatusLangRepository extends DbRepository<JpmStatusLang, Long> {

    /**
     * <p>
     * get list StatusLangDtos by process id
     * </p>
     * 
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStatusLangDto>}
     * @author KhuongTH
     */
    List<JpmStatusLangDto> getStatusLangDtosByProcessId(@Param("processId") Long processId);
    
    List<JpmStatusLangDto> findStatusLangDtosByBusinessCode(@Param("businessCode") String businessCode);
    
    /**
     * getStatusLangDtosByStatusId
     * 
     * @param statusId
     * @return
     * @author ngannh
     */
    List<JpmStatusLangDto> getStatusLangDtosByStatusId(@Param("statusId") Long statusId);

    /**
     * <p>
     * Delete status lang by status id.
     * </p>
     *
     * @param statusId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deleteStatusLangByStatusId(@Param("statusId") Long statusId);
}