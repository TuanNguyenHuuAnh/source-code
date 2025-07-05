/*******************************************************************************
 * Class        ：EfoOzDocFilterRefService
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterRef;

/**
 * EfoOzDocFilterRefService.
 *
 * @author taitt
 * @version 01-00
 * @since 01-00
 */
public interface EfoOzDocFilterRefService {

    /**
     * createAccRefWithRefType.
     *
     * @author taitt
     * @param efoOzDocFilterRef            type {@link EfoOzDocFilterRef}
     */
    void saveDocFilterRef(EfoOzDocFilterRef efoOzDocFilterRef);

    /**
     * getDocumentDataResultDto.
     *
     * @author taitt
     * @param searchDto            type {@link EfoDocumentFilterSearchDto}
     * @param pagable            type {@link Pageable}
     * @param refTypes type {@link List<String>}
     * @return {@link void}
     */
    List<DocumentDataResultDto> getDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, Pageable pagable, List<String> refTypes);

    /**
     * countDocumentDataResultDto.
     *
     * @author taitt
     * @param searchDto            type {@link EfoDocumentFilterSearchDto}
     * @param refTypes type {@link List<String>}
     * @return {@link int}
     */
    int countDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, List<String> refTypes);

}
