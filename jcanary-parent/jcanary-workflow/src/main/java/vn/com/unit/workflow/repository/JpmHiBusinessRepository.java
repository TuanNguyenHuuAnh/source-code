/*******************************************************************************
* Class        JpmHiBusinessRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.entity.JpmHiBusiness;

/**
 * JpmHiBusinessRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmHiBusinessRepository extends DbRepository<JpmHiBusiness, Long>{

}