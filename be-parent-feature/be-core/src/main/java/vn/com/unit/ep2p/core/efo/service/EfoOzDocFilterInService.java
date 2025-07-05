/*******************************************************************************
 * Class        ：EfoOzDocFilterInService
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
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterIn;

/**
 * EfoOzDocFilterInService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface EfoOzDocFilterInService {

    /**
     * createAccRefWithRefType.
     *
     * @param efoOzDocFilterIn
     *            type {@link EfoOzDocFilterIn}
     * @author taitt
     */
    void saveDocFilterIn(EfoOzDocFilterIn efoOzDocFilterIn);

    /**
     * deleteRefInByCoreTaskId.
     *
     * @param jpmTaskId
     *            type {@link Long}
     * @author taitt
     */
    void deleteRefInByJpmTaskId(Long taskId, Long docId);

    /**
     * getListEfoOzDocFilterInByCoreTaskId.
     *
     * @param jpmTaskId
     *            type {@link Long}
     * @return {@link List<EfoOzDocFilterIn>}
     * @author taitt
     */
    List<EfoOzDocFilterIn> getListEfoOzDocFilterInByJpmTaskId(Long jpmTaskId);

    /**
     * getDocumentDataResultDto.
     *
     * @param searchDto
     *            type {@link EfoDocumentFilterSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return {@link void}
     * @author taitt
     */
    List<DocumentDataResultDto> getDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, Pageable pagable);

    /**
     * countDocumentDataResultDto.
     *
     * @param searchDto
     *            type {@link EfoDocumentFilterSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return {@link int}
     * @author taitt
     */
    int countDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto);

}
