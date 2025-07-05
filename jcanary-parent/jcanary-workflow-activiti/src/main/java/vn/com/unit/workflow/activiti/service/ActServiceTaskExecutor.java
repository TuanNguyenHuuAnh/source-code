/*******************************************************************************
 * Class        ：ActServiceTaskExecutor
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.activiti.service;

import org.activiti.engine.runtime.Execution;

public interface ActServiceTaskExecutor {

    /**
     * <p>
     * Execute
     * </p>
     * .
     *
     * @param execution
     *            type {@link Execution}
     * @param beanExecuteName
     *            type {@link String}
     * @author KhuongTH
     */
    void execute(Execution execution, String beanExecuteName);

    /**
     * <p>
     * Notify to external.
     * </p>
     *
     * @param execution
     *            type {@link Execution}
     * @param beanExecuteName
     *            type {@link String}
     * @author KhuongTH
     */
    void notifyToExternal(Execution execution, String beanExecuteName);
}
