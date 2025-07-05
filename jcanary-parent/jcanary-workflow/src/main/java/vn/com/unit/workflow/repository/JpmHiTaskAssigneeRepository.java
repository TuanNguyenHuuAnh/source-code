/*******************************************************************************
 * Class        ：JpmTaskAssigneeRepository
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：Tan Tai
 * Change log   ：2021/03/04：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.entity.JpmHiTaskAssignee;

/**
 * JpmTaskAssigneeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
public interface JpmHiTaskAssigneeRepository extends DbRepository<JpmHiTaskAssignee, Long> {
	
	
	@Modifying
	public void deleteByTaskId(@Param("taskId")Long taskId);
}	
