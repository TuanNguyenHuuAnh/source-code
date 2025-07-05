/*******************************************************************************
 * Class        ：EfoOzDocRepository
 * Created date ：2020/11/13
 * Lasted date  ：2020/11/13
 * Author       ：KhoaNA
 * Change log   ：2020/11/13：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.core.dto.EfoOzDocStatisticsDto;
import vn.com.unit.core.dto.EfoOzDocStatisticsSearchDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

/**
 * EfoOzDocRepository.
 *
 * @author KhoaNA
 * @version 01-00
 * @since 01-00
 */
public interface EfoDocRepository extends DbRepository<EfoDoc, Long> {

    /**
     * <p>
     * Get efo oz doc by id.
     * </p>
     *
     * @author taitt
     * @param docId            type {@link Long}
     * @return {@link EfoDoc}
     */
    public EfoDoc getEfoDocById(@Param("id") Long docId);
    
    /**
     * <p>Get efo doc dto by id.</p>
     *
     * @author Tan Tai
     * @param docId type {@link Long}
     * @return {@link EfoDocDto}
     */
    public EfoDocDto getEfoDocDtoById(@Param("id") Long docId);
    
    /**
     * <p>Get detail document by role.</p>
     *
     * @author Tan Tai
     * @param orgIds type {@link List<Long>}
     * @param positionIds type {@link List<Long>}
     * @param accountId type {@link Long}
     * @param refTypes type {@link List<String>}
     * @param documentId type {@link Long}
     * @return {@link DocumentDataResultDto}
     */
    public DocumentDataResultDto getDetailDocumentByRole(@Param("orgIds") List<Long> orgIds,
            @Param("positionIds") List<Long> positionIds, @Param("accountId") Long accountId,           
            @Param("refTypes") List<String> refTypes,
            @Param("documentId") Long documentId, @Param("lang") String lang);
    
    /**
     * <p>Get document data result dto by draft.</p>
     *
     * @author Tan Tai
     * @param accountId type {@link Long}
     * @param efoDocumentFilterSearchDto type {@link EfoDocumentFilterSearchDto}
     * @param pageable type {@link Pageable}
     * @return {@link Page<DocumentDataResultDto>}
     */
    public Page<DocumentDataResultDto> getDocumentDataResultDtoByDraft(@Param("accountId") Long accountId,
            @Param("efoDocumentFilterSearchDto") EfoDocumentFilterSearchDto efoDocumentFilterSearchDto, Pageable pageable,@Param("lang")String lang);
    
    /**
     * <p>Count document data result dto by draft.</p>
     *
     * @author Tan Tai
     * @param accountId type {@link Long}
     * @param efoDocumentFilterSearchDto type {@link EfoDocumentFilterSearchDto}
     * @return {@link int}
     */
    public int countDocumentDataResultDtoByDraft(@Param("accountId") Long accountId,
            @Param("efoDocumentFilterSearchDto") EfoDocumentFilterSearchDto efoDocumentFilterSearchDto);
    
    /**
     * <p>Count document data result dto by my document.</p>
     *
     * @author Tan Tai
     * @param orgIds type {@link List<Long>}
     * @param positionIds type {@link List<Long>}
     * @param accountId type {@link Long}
     * @param efoDocumentFilterSearchDto type {@link EfoDocumentFilterSearchDto}
     * @param refTypes type {@link List<String>}
     * @return {@link int}
     */
    public int countDocumentDataResultDtoByMyDocument(@Param("orgIds") List<Long> orgIds, @Param("positionIds") List<Long> positionIds,
            @Param("accountId") Long accountId, @Param("efoDocumentFilterSearchDto") EfoDocumentFilterSearchDto efoDocumentFilterSearchDto,
            @Param("refTypes") List<String> refTypes);
    
    /**
     * <p>Get document data result dto by my document.</p>
     *
     * @author Tan Tai
     * @param orgIds type {@link List<Long>}
     * @param positionIds type {@link List<Long>}
     * @param accountId type {@link Long}
     * @param efoDocumentFilterSearchDto type {@link EfoDocumentFilterSearchDto}
     * @param refTypes type {@link List<String>}
     * @param pageable type {@link Pageable}
     * @return {@link Page<DocumentDataResultDto>}
     */
    public Page<DocumentDataResultDto> getDocumentDataResultDtoByMyDocument(@Param("orgIds") List<Long> orgIds,
            @Param("positionIds") List<Long> positionIds, @Param("accountId") Long accountId,
            @Param("efoDocumentFilterSearchDto") EfoDocumentFilterSearchDto efoDocumentFilterSearchDto,
            @Param("refTypes") List<String> refTypes, Pageable pageable, @Param("lang") String lang);
    
    /**
     * <p>Count statistics doc by my document.</p>
     *
     * @author Tan Tai
     * @param orgIds type {@link List<Long>}
     * @param positionIds type {@link List<Long>}
     * @param accountId type {@link Long}
     * @param efoOzDocStatisticsSearchDto type {@link EfoOzDocStatisticsSearchDto}
     * @param refTypes type {@link List<String>}
     * @return {@link EfoOzDocStatisticsDto}
     */
    public EfoOzDocStatisticsDto countStatisticsDocByMyDocument(@Param("orgIds") List<Long> orgIds, @Param("positionIds") List<Long> positionIds,
            @Param("accountId") Long accountId, @Param("efoOzDocStatisticsSearchDto") EfoOzDocStatisticsSearchDto efoOzDocStatisticsSearchDto,
            @Param("refTypes") List<String> refTypes);

	/**
	 * @param docId
	 * @param buttonText
	 * @param accountId
	 * @param lang
	 * @return
	 */
	public DocumentActionReq getDetailDocumentCurrentStep(@Param("docId")Long docId, @Param("stepCode")String stepCode, @Param("buttonText")String buttonText
			, @Param("accountId")Long accountId, @Param("lang")String lang);
	
	@Modifying
    public void updateItemFunctionForDoc(@Param("id") Long id, @Param("itemFunctionCode") String itemFunctionCode);
}