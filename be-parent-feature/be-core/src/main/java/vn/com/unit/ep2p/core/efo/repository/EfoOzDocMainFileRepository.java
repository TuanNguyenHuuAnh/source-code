/*******************************************************************************
 * Class        ：OZDocMainFileRepository
 * Created date ：2019/08/07
 * Lasted date  ：2019/08/07
 * Author       ：NhanNV
 * Change log   ：2019/08/07：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFile;

/**
 * OZDocMainFileRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface EfoOzDocMainFileRepository extends DbRepository<EfoOzDocMainFile, Long> {

    /**
     * <p>
     * Get efo oz doc main file dto by doc id.
     * </p>
     *
     * @param docId
     *            type {@link Long}
     * @return {@link EfoOzDocMainFileDto}
     * @author tantm
     */
    EfoOzDocMainFileDto getEfoOzDocMainFileDtoByDocId(@Param("docId") Long docId);
    
    /**
     * <p>
     * Get efo oz doc main file dto by version id.
     * </p>
     *
     * @param docMainFileVersionId
     *            type {@link Long}
     * @return {@link EfoOzDocMainFileDto}
     * @author taitt
     */
    EfoOzDocMainFileDto getEfoOzDocMainFileDtoByVersionId(@Param("docMainFileVersionId") Long docMainFileVersionId);
}
