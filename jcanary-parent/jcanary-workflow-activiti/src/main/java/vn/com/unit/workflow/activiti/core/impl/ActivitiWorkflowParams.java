/*******************************************************************************
 * Class        ：ActivitiWorkflowParams
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：KhoaNA
 * Change log   ：2020/11/30：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.core.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import vn.com.unit.workflow.core.WorkflowParams;

/**
 * ActivitiWorkflowParams
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActivitiWorkflowParams implements WorkflowParams {
// TODO SCOPE_SESSION related quazt job.
    private Map<String, Object> params = new HashMap<>();

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.core.ParamsWorkflow#getParams()
     */
    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.core.ParamsWorkflow#setParams(java.util.Map)
     */
    @Override
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.core.ParamsWorkflow#putParam(java.lang.String, java.lang.Object)
     */
    @Override
    public Object putParam(String key, Object value) {
        return params.put(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.core.ParamsWorkflow#getParam(java.lang.String)
     */
    @Override
    public Object getParam(String key) {
        return params.get(key);
    }

}
