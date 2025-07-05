/*******************************************************************************
 * Class        ：WorkflowIntegrateService
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.core;

import java.util.Map;

/**
 * <p>
 * WorkflowIntegrateService
 * </p>
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface WorkflowIntegrateService {

    void notifyContinueWorkflow(String executionId, String messageName, Map<String, Object> variables);
}
