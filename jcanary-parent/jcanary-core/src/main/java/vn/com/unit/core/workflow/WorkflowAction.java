/*******************************************************************************
 * Class        ：WorkflowAction
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：tantm
 * Change log   ：2020/11/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.workflow;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.dts.exception.DetailException;

/**
 * WorkflowAction
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface WorkflowAction {

    /**
     * Start workflow for document
     * 
     * @param documentDto
     *            type <T> extends {@link AbstractDocumentDto}
     * @throws Exception
     *             type Exception
     * @author tantm
     */
    <T extends AbstractDocumentDto> void startWorkflowForDocument(T documentDto) throws Exception;

    /**
     * complete Task
     * 
     * @param documentDto
     *            type <T> extends {@link AbstractDocumentDto}
     * @param actionDto
     *            type {@link ActionDto}
     * @author KhuongTH
     * @throws DetailException
     */
    <T extends AbstractDocumentDto> void completeTask(T documentDto, ActionDto actionDto) throws DetailException;

}
