/*******************************************************************************
 * Class        ：DocumentService
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：KhoaNA
 * Change log   ：2020/11/12：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.io.Serializable;

import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.core.entity.AbstractDocument;

/**
 * DocumentService.
 *
 * @version 01-00
 * @param <E>
 *            the element type
 * @param <DTO>
 *            the generic type
 * @param <ID>
 *            the generic type
 * @since 01-00
 * @author KhoaNA
 */
public abstract interface DocumentService<E extends AbstractDocument, DTO extends AbstractDocumentDto, ID extends Serializable> {

    /**
     * Generate a ID for document generateId.
     *
     * @return type DocumentId
     * @author tantm
     */
    ID generateId();

    /**
     * Save all of objects that extends AbstractDocument into database.
     *
     * @param entity
     *            All of objects extends AbstractDocument
     * @return E All of objects extends AbstractDocument
     * @author NhanNV
     */
    E save(E entity);

    /**
     * Save all of objects that extends AbstractDocumentDto into database but it will be call save(E entity) to save the data.
     *
     * @param objectDto
     *            All of objects extends AbstractDocumentDto
     * @return E All of objects extends AbstractDocumentDto
     * @author NhanNV
     */
    E save(DTO objectDto);

    E updateVersion(DTO documentDto, boolean isMajor);

}
