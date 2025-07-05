/*******************************************************************************
 * Class        ：JpmTaskAssigneeRepository
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：Tan Tai
 * Change log   ：2021/03/04：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.entity.JpmTaskAssignee;

/**
 * JpmTaskAssigneeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
public interface JpmTaskAssigneeRepository extends DbRepository<JpmTaskAssignee, Long> {

	public List<Long> getAccIdsByTaskId(@Param("taskId")Long taskId);
	
	@Modifying
	public void deleteByTaskId(@Param("taskId")Long taskId);
	
	
	public JpmTaskAssignee getListJpmTaskAssigneeByTaskIdAndAccountId(@Param("taskId")Long taskId, @Param("stepDeployId")Long stepDeployId
			,@Param("processDeployId")Long processDeployId,@Param("accountId")Long accountId);

    /**
     * <p>
     * Get list account by task id and type.
     * </p>
     *
     * @param taskId
     *            type {@link Long}
     * @param type
     *            type {@link Long}
     * @return {@link List<Long>}
     * @author tantm
     */
    public List<Long> getListAccountByTaskIdAndType(@Param("taskId")Long taskId, @Param("type")Long type);

	
}	
