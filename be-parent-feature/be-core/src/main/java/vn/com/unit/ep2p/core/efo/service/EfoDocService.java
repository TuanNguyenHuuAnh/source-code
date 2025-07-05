/*******************************************************************************
 * Class        ：EfoOzDocService
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：KhoaNA
 * Change log   ：2020/11/12：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.core.dto.EfoOzDocStatisticsDto;
import vn.com.unit.core.dto.EfoOzDocStatisticsSearchDto;
import vn.com.unit.core.service.DocumentService;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

/**
 * <p>EfoDocService</p>.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public interface EfoDocService extends DocumentService<EfoDoc, EfoDocDto, Long> {

    /** The Constant TABLE_ALIAS_EFO_DOC. */
    static final String TABLE_ALIAS_EFO_DOC = "doc";
    
    /**
     * <p>Get efo doc by id.</p>
     *
     * @author Tan Tai
     * @param id type {@link Long}
     * @return {@link EfoDoc}
     */
    public EfoDoc getEfoDocById(Long id);

    /**
     * <p>Update efo doc by oz doc main file.</p>
     *
     * @author Tan Tai
     * @param resDto type {@link EfoOzDocMainFileDto}
     * @return {@link EfoDoc}
     */
    public EfoDoc updateEfoDocByOzDocMainFile(EfoOzDocMainFileDto resDto);

	/**
	 * <p>Get detail document by role.</p>
	 *
	 * @author Tan Tai
	 * @param posIds type {@link List<Long>}
	 * @param orgIds type {@link List<Long>}
	 * @param refTypes type {@link List<String>}
	 * @param documentId type {@link Long}
	 * @return {@link DocumentDataResultDto}
	 */
	DocumentDataResultDto getDetailDocumentByRole(List<Long> posIds, List<Long> orgIds, List<String> refTypes,
			Long documentId);

	/**
	 * <p>Count document data result dto by status draft.</p>
	 *
	 * @author Tan Tai
	 * @param searchDto type {@link EfoDocumentFilterSearchDto}
	 * @return {@link int}
	 */
	int countDocumentDataResultDtoByStatusDraft(EfoDocumentFilterSearchDto searchDto);

	/**
	 * <p>Get document data result dto by status draft.</p>
	 *
	 * @author Tan Tai
	 * @param searchDto type {@link EfoDocumentFilterSearchDto}
	 * @param pagable type {@link Pageable}
	 * @return {@link List<DocumentDataResultDto>}
	 */
	List<DocumentDataResultDto> getDocumentDataResultDtoByStatusDraft(EfoDocumentFilterSearchDto searchDto,
			Pageable pagable);

	/**
	 * <p>Get document data result dto.</p>
	 *
	 * @author Tan Tai
	 * @param searchDto type {@link EfoDocumentFilterSearchDto}
	 * @param posIds type {@link List<Long>}
	 * @param orgIds type {@link List<Long>}
	 * @param refTypes type {@link List<String>}
	 * @param pagable type {@link Pageable}
	 * @return {@link List<DocumentDataResultDto>}
	 */
	List<DocumentDataResultDto> getDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, List<Long> posIds,
			List<Long> orgIds, List<String> refTypes, Pageable pagable);

	/**
	 * <p>Count document data result dto.</p>
	 *
	 * @author Tan Tai
	 * @param searchDto type {@link EfoDocumentFilterSearchDto}
	 * @param posIds type {@link List<Long>}
	 * @param orgIds type {@link List<Long>}
	 * @param refTypes type {@link List<String>}
	 * @return {@link int}
	 */
	int countDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, List<Long> posIds, List<Long> orgIds,
			List<String> refTypes);

	/**
	 * <p>Count statistics doc by my document.</p>
	 *
	 * @author Tan Tai
	 * @param searchDto type {@link EfoOzDocStatisticsSearchDto}
	 * @param posIds type {@link List<Long>}
	 * @param orgIds type {@link List<Long>}
	 * @param refTypes type {@link List<String>}
	 * @return {@link EfoOzDocStatisticsDto}
	 */
	EfoOzDocStatisticsDto countStatisticsDocByMyDocument(EfoOzDocStatisticsSearchDto searchDto, List<Long> posIds,
			List<Long> orgIds, List<String> refTypes);

	/**
	 * <p>Get efo doc dto by id.</p>
	 *
	 * @author Tan Tai
	 * @param id type {@link Long}
	 * @return {@link EfoDocDto}
	 */
	EfoDocDto getEfoDocDtoById(Long id);

	/**
	 * @param docId
	 * @param buttonText
	 * @return
	 */
	DocumentActionReq getDetailDocumentCurrentStep(Long docId, String stepCode, String buttonText);

}
