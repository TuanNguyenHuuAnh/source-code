/*******************************************************************************
 * Class        ActDocWorkflowListener
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.service;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.runtime.Execution;

import vn.com.unit.workflow.entity.JpmHiTask;

/**
 * The listener interface for receiving actDocWorkflow events. The class that is interested in processing a actDocWorkflow event implements
 * this interface, and the object created with that class is registered with a component using the component's
 * <code>addActDocWorkflowListener<code> method. When the actDocWorkflow event occurs, that object's appropriate method is invoked.
 *
 * @see ActDocWorkflowEvent
 */
public interface ActDocWorkflowListener {

    /**
     * <p>
     * On create task.
     * </p>
     *
     * @param execution
     *            type {@link Execution}
     * @param task
     *            type {@link DelegateTask}
     * @author tantm
     */
    public void onCreateTask(Execution execution, DelegateTask task);

    /**
     * <p>
     * On complete task.
     * </p>
     *
     * @param execution
     *            type {@link Execution}
     * @param task
     *            type {@link DelegateTask}
     * @author tantm
     */
    void onCompleteTask(Execution execution, DelegateTask task);
    
    /**
     * <p>
     * On complete.
     * </p>
     *
     * @param execution
     *            type {@link Execution}
     * @author tantm
     */
    public void onComplete(Execution execution);

    /**
     * <p>
     * Build jpm hi task.
     * </p>
     *
     * @param taskId
     *            type {@link Long}
     * @param eslapTime
     *            type {@link int}
     * @param displayHis
     *            type {@link boolean}
     * @return {@link JpmHiTask}
     * @author tantm
     */
    JpmHiTask buildJpmHiTask(Long taskId);
}
