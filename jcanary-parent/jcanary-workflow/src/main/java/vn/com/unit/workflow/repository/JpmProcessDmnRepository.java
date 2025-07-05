/*******************************************************************************
* Class        JpmProcessDmnRepository
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmProcessDmnDto;
import vn.com.unit.workflow.entity.JpmProcessDmn;

/**
 * JpmProcessDmnRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmProcessDmnRepository extends DbRepository<JpmProcessDmn, Long> {

    List<JpmProcessDmnDto> getJpmProcessDmnDtosByProcessId(@Param("processId") Long processId);
}