/*******************************************************************************
* Class        JpmHiTaskService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       Tan Tai
* Change log   2020/11/25 01-00 Tan Tai create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmHiTaskAssignee;

/**
 * <p>JpmHiTaskAssigneeService</p>.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */

public interface JpmHiTaskAssigneeService {

	/**
	 * <p>Save jpm hi task assignee.</p>
	 *
	 * @author Tan Tai
	 * @param jpmHiTaskAssignee type {@link JpmHiTaskAssignee}
	 * @return {@link JpmHiTaskAssignee}
	 */
	JpmHiTaskAssignee saveJpmHiTaskAssignee(JpmHiTaskAssignee jpmHiTaskAssignee);
	
	
	/**
	 * <p>Delete by task id.</p>
	 *
	 * @author Tan Tai
	 * @param taskId type {@link Long}
	 */
	public void deleteByTaskId(Long taskId);


	/**
	 * updateJpmHiTaskAssignee.
	 *
	 * @author Tan Tai
	 * @param jpmHiTaskAssignee type {@link JpmHiTaskAssignee}
	 * @return {@link JpmHiTaskAssignee}
	 */
	JpmHiTaskAssignee updateJpmHiTaskAssignee(JpmHiTaskAssignee jpmHiTaskAssignee);


    

}