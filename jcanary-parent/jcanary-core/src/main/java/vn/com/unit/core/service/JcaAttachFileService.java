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

import vn.com.unit.core.dto.JcaAttachFileDto;
import vn.com.unit.core.dto.JcaAttachFileSearchDto;

/**
 * JcaAttachFileService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaAttachFileService {

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
    public JcaAttachFileDto getJcaAttachFileDtoById(Long id);

    /**
     * <p>
     * Save jca attach file dto.
     * </p>
     *
     * @param jcaAttachFileDto
     *            type {@link JcaAttachFileDto}
     * @return {@link JcaAttachFileDto}
     * @author TrieuVD
     */
    public JcaAttachFileDto saveJcaAttachFileDto(JcaAttachFileDto jcaAttachFileDto);

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
    public int countBySearchCondition(JcaAttachFileSearchDto searchDto);

    /**
     * <p>
     * Get jca attach file dto list by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JcaAttachFileSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaAttachFileDto>}
     * @author TrieuVD
     */
    public List<JcaAttachFileDto> getJcaAttachFileDtoListByCondition(JcaAttachFileSearchDto searchDto, Pageable pageable);

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
     * @return {@link List<JcaAttachFileDto>}
     * @author TrieuVD
     */
    public List<JcaAttachFileDto> getJcaAttachFileDtoListByReferenceId(Long referenceId, String attachType);

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
