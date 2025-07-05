/*******************************************************************************
 * Class        ：EfoOzDocFilterOutRepository
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

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterOut;

/**
 * EfoOzDocFilterOutRepository.
 *
 * @author taitt
 * @version 01-00
 * @since 01-00
 */
public interface EfoOzDocFilterOutRepository extends DbRepository<EfoOzDocFilterOut, Long>{

    
    /**
     * <p>
     * Get ids exist acc action id by efo oz doc id and acc action id.
     * </p>
     *
     * @author taitt
     * @param efoOzDocId            type {@link Long}
     * @param accActionId            type {@link Long}
     * @return {@link List<Long>}
     */
    public List<Long> getIdsExistAccActionIdByEfoOzDocIdAndAccActionId(@Param("efoOzDocId") Long efoOzDocId, @Param("accActionId") Long accActionId);
    
    
    /**
     * <p>
     * Delete efo oz doc filter out by ids.
     * </p>
     *
     * @author taitt
     * @param ids            type {@link List<Long>}
     */
    @Modifying
    public void deleteEfoOzDocFilterOutByIds(List<Long> ids);
    
    /**
     * <p>
     * Get document data result dto by filter out.
     * </p>
     *
     * @author taitt
     * @param accountId            type {@link Long}
     * @param efoDocumentFilterSearchDto            type {@link EfoDocumentFilterSearchDto}
     * @param pageable            type {@link Pageable}
     * @return {@link List<DocumentDataResultDto>}
     */
    public Page<DocumentDataResultDto>  getDocumentDataResultDtoByFilterOut(@Param("accountId") Long accountId
    		,@Param("efoDocumentFilterSearchDto")EfoDocumentFilterSearchDto efoDocumentFilterSearchDto,Pageable pageable,@Param("lang") String lang);
    
    /**
     * <p>
     * Count document data result dto by filter out.
     * </p>
     *
     * @author taitt
     * @param accountId            type {@link Long}
     * @param efoDocumentFilterSearchDto            type {@link EfoDocumentFilterSearchDto}
     * @return {@link int}
     */
    public int  countDocumentDataResultDtoByFilterOut(@Param("accountId") Long accountId,@Param("efoDocumentFilterSearchDto")EfoDocumentFilterSearchDto efoDocumentFilterSearchDto);
    
    
    /**
     * <p>Get efo oz doc filter out by task id.</p>
     *
     * @author Tan Tai
     * @param taskId type {@link Long}
     * @return {@link EfoOzDocFilterOut}
     */
    public EfoOzDocFilterOut getEfoOzDocFilterOutByTaskId(@Param("taskId") Long taskId);
}
