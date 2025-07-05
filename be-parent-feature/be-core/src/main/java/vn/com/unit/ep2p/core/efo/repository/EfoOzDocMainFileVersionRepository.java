/*******************************************************************************
 * Class        ：EfoOzDocMainFileVersionRepository
 * Created date ：2019/08/07
 * Lasted date  ：2019/08/07
 * Author       ：NhanNV
 * Change log   ：2019/08/07：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileVersionDto;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFileVersion;

/**
 * EfoOzDocMainFileVersionRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface EfoOzDocMainFileVersionRepository extends DbRepository<EfoOzDocMainFileVersion, String> {

    /**
     * <p>
     * Get list efo oz doc main file version dto by oz doc id.
     * </p>
     *
     * @param docId
     *            type {@link Long}
     * @return {@link List<EfoOzDocMainFileVersionDto>}
     * @author tantm
     */
    List<EfoOzDocMainFileVersionDto> getListEfoOzDocMainFileVersionDtoByOzDocId(@Param("docId")Long docId);

}
