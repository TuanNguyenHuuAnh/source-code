/*******************************************************************************
 * Class        ：WorkflowTypeBuilder
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：tantm
 * Change log   ：2020/11/12：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.workflow.enumdef.ProcessType;

/**
 * WorkflowTypeBuilder
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Component
public class WorkflowTypeBuilder {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * Get a implement of WorkflowInstance by process type
     * 
     * @param type
     *            type of process
     * @return WorkflowInstance
     * @author tantm
     */
    public WorkflowInstance getWorkflowInstance(int type) {
        WorkflowInstance workflowInstance = null;
        ProcessType typeEnum = ProcessType.resolveByValue(type);
        switch (typeEnum) {
        case BPMN:
            workflowInstance = (WorkflowInstance) applicationContext.getBean(CoreConstant.BEAN_BPMN_DOCUMENT_WORKFLOW);
            break;
        case FREE:
            break;
        case INTEGRATE:
            break;

        default:
            break;
        }

        return workflowInstance;
    }
}
