/*******************************************************************************
 * Class        ：EfoOzDocFilterOutService
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
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterOut;

/**
 * EfoOzDocFilterOutService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface EfoOzDocFilterOutService {

    /**
     * createAccRefWithRefType.
     *
     * @param efoOzDocFilterOut
     *            type {@link EfoOzDocFilterOut}
     * @param accActionId
     *            type {@link Long}
     * @author taitt
     */
    void saveDocFilterOut(EfoOzDocFilterOut efoOzDocFilterOut);

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

	/**
	 * getEfoOzDocFilterOutByTaskId
	 * @param taskId
	 * @return
	 * @author Tan Tai
	 */
	EfoOzDocFilterOut getEfoOzDocFilterOutByTaskId(Long taskId);

}
