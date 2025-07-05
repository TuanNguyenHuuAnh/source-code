/*******************************************************************************
 * Class        ：WorkflowServiceTaskExecutor
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.core;

import java.util.Map;

public interface WorkflowServiceTaskExecutor {

    /**
     * <p>
     * Execute
     * </p>
     * .
     *
     * @param params
     *            type {@link Map<String,Object>}
     * @author KhuongTH
     */
    void execute(Map<String, Object> params);

    /**
     * <p>
     * Notify to external.
     * </p>
     *
     * @param execution
     *            type {@link Execution}
     * @author KhuongTH
     */
    void notifyToExternal(Map<String, Object> params);
}
