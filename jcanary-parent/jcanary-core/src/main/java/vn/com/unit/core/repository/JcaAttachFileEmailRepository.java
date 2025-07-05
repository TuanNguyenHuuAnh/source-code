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

import vn.com.unit.core.dto.JcaAttachFileEmailDto;
import vn.com.unit.core.dto.JcaAttachFileEmailSearchDto;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * JcaAttachFileEmailRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JcaAttachFileEmailRepository extends DbRepository<JcaAttachFileEmail, Long> {
    
    /**
     * <p>
     * Get jca attach file email dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link JcaAttachFileEmailDto}
     */
    public JcaAttachFileEmailDto getJcaAttachFileEmailDtoById(@Param("id") Long id);
    
    /**
     * <p>
     * Count by search condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaAttachFileEmailSearchDto}
     * @return {@link int}
     */
    public int countBySearchCondition(@Param("searchDto") JcaAttachFileEmailSearchDto searchDto);

    /**
     * <p>
     * Get jca attach file email dto page list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaAttachFileEmailSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<JcaAttachFileEmailDto>}
     */
    public Page<JcaAttachFileEmailDto> getJcaAttachFileEmailDtoPageListByCondition(@Param("searchDto") JcaAttachFileEmailSearchDto searchDto, @Param("pageable") Pageable pageable);
    
    /**
     * <p>
     * Get jca attach file email dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param searchDto
     *            type {@link JcaAttachFileEmailSearchDto}
     * @return {@link List<JcaAttachFileEmailDto>}
     */
    public List<JcaAttachFileEmailDto> getJcaAttachFileEmailDtoListByCondition(@Param("searchDto") JcaAttachFileEmailSearchDto searchDto);
}
