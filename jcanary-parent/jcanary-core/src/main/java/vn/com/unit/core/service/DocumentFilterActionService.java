/*******************************************************************************
 * Class        ：DocumentFilterActionService
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.AbstractDocumentFilterDto;

/**
 * DocumentFilterActionService.
 *
 * @author taitt
 * @version 01-00
 * @since 01-00
 */
public abstract interface DocumentFilterActionService {

    /**
     * <p>Submit doc filter.</p>
     *
     * @author Tan Tai
     * @param <T> the generic type
     * @param docFilterActionDto type {@link T}
     */
    <T extends AbstractDocumentFilterDto> void submitDocFilter(T docFilterActionDto);

    /**
     * <p>Complete doc filter.</p>
     *
     * @author Tan Tai
     * @param <T> the generic type
     * @param docFilterActionDto type {@link T}
     */
    <T extends AbstractDocumentFilterDto> void completeDocFilter(T docFilterActionDto,Long taskNewId);

}
