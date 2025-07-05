/*******************************************************************************
 * Class        ：ActServiceTaskExecutorImpl
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.activiti.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import vn.com.unit.workflow.activiti.service.ActServiceTaskExecutor;
import vn.com.unit.workflow.core.WorkflowServiceTaskExecutor;

/**
 * <p>
 * ActServiceTaskExecutorImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service("actServiceTaskExecutor")
public class ActServiceTaskExecutorImpl implements ActServiceTaskExecutor {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * <p>
     * Execute.
     * </p>
     *
     * @param execution
     *            type {@link Execution}
     * @param beanExecuteName
     *            type {@link String}
     * @author KhuongTH
     */
    @Override
    public void execute(Execution execution, String beanExecuteName) {
        // TODO KhuongTH
        WorkflowServiceTaskExecutor serviceTaskExecutor = (WorkflowServiceTaskExecutor) applicationContext.getBean(beanExecuteName);
        Map<String, Object> params = new HashMap<>();

        serviceTaskExecutor.execute(params);
    }

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
    @Override
    public void notifyToExternal(Execution execution, String beanExecuteName) {
        // TODO KhuongTH
        WorkflowServiceTaskExecutor serviceTaskExecutor = (WorkflowServiceTaskExecutor) applicationContext.getBean(beanExecuteName);
        Map<String, Object> params = new HashMap<>();

        serviceTaskExecutor.notifyToExternal(params);
    }

}
