/*******************************************************************************
 * Class        ：JcaAttachFileService
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaAttachFileEmailDto;
import vn.com.unit.core.dto.JcaAttachFileEmailSearchDto;

/**
 * JcaAttachFileService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaAttachFileEmailService {

    /**
     * <p>
     * Get jca attach file dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link JcaAttachFileEmailDto}
     * @author TrieuVD
     */
    public JcaAttachFileEmailDto getJcaAttachFileEmailDtoById(Long id);

    /**
     * <p>
     * Save jca attach file dto.
     * </p>
     *
     * @param JcaAttachFileEmailDto
     *            type {@link JcaAttachFileEmailDto}
     * @return {@link JcaAttachFileEmailDto}
     * @author TrieuVD
     */
    public JcaAttachFileEmailDto saveJcaAttachFileEmailDto(JcaAttachFileEmailDto JcaAttachFileEmailDto);

    /**
     * <p>
     * Count by search condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JcaAttachFileEmailSearchDto}
     * @return {@link int}
     * @author TrieuVD
     */
    public int countBySearchCondition(JcaAttachFileEmailSearchDto searchDto);

    /**
     * <p>
     * Get jca attach file dto list by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JcaAttachFileEmailSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaAttachFileEmailDto>}
     * @author TrieuVD
     */
    public List<JcaAttachFileEmailDto> getJcaAttachFileEmailDtoListByCondition(JcaAttachFileEmailSearchDto searchDto, Pageable pageable);

    /**
     * <p>
     * Update reference id.
     * </p>
     *
     * @param referenceKey
     *            type {@link String}
     * @param referenceId
     *            type {@link Long}
     * @author TrieuVD
     */
    public void updateReferenceId(String referenceKey, Long referenceId);

    /**
     * <p>
     * Get jca attach file dto list by reference id.
     * </p>
     *
     * @param referenceId
     *            type {@link Long}
     * @param attachType
     *            type {@link String}
     * @return {@link List<JcaAttachFileEmailDto>}
     * @author TrieuVD
     */
    public List<JcaAttachFileEmailDto> getJcaAttachFileEmailDtoListByReferenceId(Long referenceId, String attachType);

    /**
     * <p>
     * Delete jca attach file by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author TrieuVD
     */
    void deleteJcaAttachFileById(Long id);
}
