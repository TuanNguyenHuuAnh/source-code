/*******************************************************************************
 * Class        ：DocumentActionEventService
 * Created date ：2020/12/04
 * Lasted date  ：2020/12/04
 * Author       ：tantm
 * Change log   ：2020/12/04：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.core.entity.AbstractDocument;
import vn.com.unit.core.workflow.dto.DocumentAction;

/**
 * DocumentActionEventService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public abstract interface DocumentActionEventService<E extends AbstractDocument, DTO extends AbstractDocumentDto> {

    /**
     * Processing some logic of business before handling the saving data.
     * 
     * @param objectDto
     *            All of objects extends AbstractDocumentDto
     * 
     * @author NhanNV
     */
    void processDataBeforeSave(DTO objectDto);

    /**
     * Processing some logic of business before handling the saving data.
     * 
     * @param entity
     *            All of objects extends AbstractDocument
     * @param objectDto
     *            All of objects extends AbstractDocumentDto
     * 
     * @author NhanNV
     */
    void processDataBeforeSave(E entity, DTO objectDto);

    /**
     * Processing some logic of business after saved data.
     * 
     * @param objectDto
     *            All of objects extends AbstractDocumentDto
     * @param documentAction
     *            All action of document
     * 
     * @author NhanNV
     */
    void processDataAfterSave(DTO objectDto, DocumentAction documentAction);

    /**
     * Processing some logic of business after saved data.
     * 
     * @param entity
     *            All of objects extends AbstractDocument
     * @param objectDto
     *            All of objects extends AbstractDocumentDto
     * 
     * @author NhanNV
     */
    void processDataAfterSave(E entity, DTO objectDto);
}
