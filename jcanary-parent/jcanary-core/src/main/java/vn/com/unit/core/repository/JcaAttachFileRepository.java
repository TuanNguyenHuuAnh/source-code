/*******************************************************************************
 * Class        ：JcaAttachFileRepository
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaAttachFileDto;
import vn.com.unit.core.dto.JcaAttachFileSearchDto;
import vn.com.unit.core.entity.JcaAttachFile;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaAttachFileRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaAttachFileRepository extends DbRepository<JcaAttachFile, Long> {
    
    /**
     * <p>
     * Get jca attach file dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link JcaAttachFileDto}
     * @author TrieuVD
     */
    public JcaAttachFileDto getJcaAttachFileDtoById(@Param("id") Long id);
    
    /**
     * <p>
     * Count by search condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JcaAttachFileSearchDto}
     * @return {@link int}
     * @author TrieuVD
     */
    public int countBySearchCondition(@Param("searchDto") JcaAttachFileSearchDto searchDto);

    /**
     * <p>
     * Get jca atta pagech file dto list by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JcaAttachFileSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<JcaAttachFileDto>}
     * @author TrieuVD
     */
    public Page<JcaAttachFileDto> getJcaAttachFileDtoPageListByCondition(@Param("searchDto") JcaAttachFileSearchDto searchDto, @Param("pageable") Pageable pageable);
    
    /**
     * <p>
     * Get jca atta pagech file dto list by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JcaAttachFileSearchDto}
     * @return {@link Page<JcaAttachFileDto>}
     * @author TrieuVD
     */
    public List<JcaAttachFileDto> getJcaAttachFileDtoListByCondition(@Param("searchDto") JcaAttachFileSearchDto searchDto);
}
