/*******************************************************************************
 * Class        ：EfoOzDocFilterInRepository
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterIn;

/**
 * EfoOzDocFilterInRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface EfoOzDocFilterInRepository extends DbRepository<EfoOzDocFilterIn, Long>{

    /**
     * <p>
     * Get ref in by jpm task id and not task type assignee.
     * </p>
     *
     * @param jpmTaskId
     *            type {@link Long}
     * @param type
     *            type {@link String}
     * @return {@link List<EfoOzDocFilterIn>}
     * @author taitt
     */
    public List<EfoOzDocFilterIn> getRefInByJpmTaskIdAndNotTaskTypeAssignee(@Param("jpmTaskId") Long jpmTaskId,@Param("type") String type);
    
    /**
     * <p>
     * Delete ref in by jpm task id.
     * </p>
     *
     * @param jpmTaskId
     *            type {@link Long}
     * @param accountId
     *            type {@link long}
     * @param sysDate
     *            type {@link Date}
     * @author taitt
     */
    @Modifying
    public void deleteRefInByJpmTaskId(@Param("taskId") Long taskId,@Param("docId")  Long docId);
    
    
    /**
     * <p>
     * Get list efo oz doc filter in by jpm task id.
     * </p>
     *
     * @param jpmTaskId
     *            type {@link Long}
     * @return {@link List<EfoOzDocFilterIn>}
     * @author taitt
     */
    public List<EfoOzDocFilterIn> getListEfoOzDocFilterInByJpmTaskId(@Param("jpmTaskId") Long jpmTaskId);
    
    
    /**
     * <p>
     * Get document data result dto by filter in.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @param efoDocumentFilterSearchDto
     *            type {@link EfoDocumentFilterSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<DocumentDataResultDto>}
     * @author taitt
     */
    public Page<DocumentDataResultDto>  getDocumentDataResultDtoByFilterIn(@Param("accountId") Long accountId
    		,@Param("efoDocumentFilterSearchDto")EfoDocumentFilterSearchDto efoDocumentFilterSearchDto,Pageable pageable,@Param("lang") String lang);
    
    /**
     * <p>
     * Count document data result dto by filter in.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @param efoDocumentFilterSearchDto
     *            type {@link EfoDocumentFilterSearchDto}
     * @return {@link int}
     * @author taitt
     */
    public int  countDocumentDataResultDtoByFilterIn(@Param("accountId") Long accountId,@Param("efoDocumentFilterSearchDto")EfoDocumentFilterSearchDto efoDocumentFilterSearchDto);
}
