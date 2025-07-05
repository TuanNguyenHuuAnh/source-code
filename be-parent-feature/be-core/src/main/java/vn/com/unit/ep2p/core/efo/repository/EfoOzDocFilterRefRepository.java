/*******************************************************************************
 * Class        ：EfoOzDocFilterRefRepository
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterRef;

/**
 * EfoOzDocFilterRefRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface EfoOzDocFilterRefRepository extends DbRepository<EfoOzDocFilterRef, Long>{

    /**
     * <p>
     * Get document data result dto by filter ref.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @param refTypes
     *            type {@link List<String>}
     * @param efoDocumentFilterSearchDto
     *            type {@link EfoDocumentFilterSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<DocumentDataResultDto>}
     * @author taitt
     */
    public Page<DocumentDataResultDto>  getDocumentDataResultDtoByFilterRef(@Param("accountId") Long accountId,@Param("refTypes") List<String> refTypes
    		,@Param("efoDocumentFilterSearchDto")EfoDocumentFilterSearchDto efoDocumentFilterSearchDto,Pageable pageable,@Param("lang") String lang);
    
    /**
     * <p>
     * Count document data result dto by filter ref.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @param refType
     *            type {@link String}
     * @param efoDocumentFilterSearchDto
     *            type {@link EfoDocumentFilterSearchDto}
     * @return {@link int}
     * @author taitt
     */
    public int  countDocumentDataResultDtoByFilterRef(@Param("accountId") Long accountId,@Param("refTypes") List<String> refTypes,@Param("efoDocumentFilterSearchDto")EfoDocumentFilterSearchDto efoDocumentFilterSearchDto);
}
