/*******************************************************************************
 * Class        ：DocumentWorkflowService
 * Created date ：2020/11/13
 * Lasted date  ：2020/11/13
 * Author       ：tantm
 * Change log   ：2020/11/13：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.workflow.service;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.core.workflow.dto.DocumentAction;

/**
 * DocumentWorkflowService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface DocumentWorkflowService {

    /**
     * 
     * Submit Document
     * 
     * @param <T>
     *            instance of DocumentDto
     * @param documentDto
     *            type DocumentDto
     * @param documentAction
     *            type DocumentAction
     * @throws Exception
     *             type Exception
     * @author tantm
     */
    <T extends AbstractDocumentDto> void submitDocument(T documentDto, DocumentAction documentAction) throws Exception;

    /**
     * Save document.
     *
     * @param <T>
     *            the generic type
     * @param documentDto
     *            the document dto type T
     * @param documentAction
     *            the document action type DocumentAction
     * @throws Exception
     *             the exception
     * @author tantm
     */
    <T extends AbstractDocumentDto> T saveDocument(T documentDto, DocumentAction documentAction) throws Exception;

    /**
     * actionDocument
     * 
     * @param <T>
     *            instance of DocumentDto
     * @param documentDto
     *            type {@link DocumentDto}
     * @param actionDto
     *            type {@link ActionDto}
     * @param documentAction
     *            type DocumentAction
     * @throws Exception
     * @author KhuongTH
     */
    <T extends AbstractDocumentDto> void actionDocument(T documentDto, ActionDto actionDto, DocumentAction documentAction) throws Exception;

}
