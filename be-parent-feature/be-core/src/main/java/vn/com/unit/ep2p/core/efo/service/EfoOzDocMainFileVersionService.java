/*******************************************************************************
 * Class        ：EfoOzDocMainFileVersionService
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：tantm
 * Change log   ：2020/12/03：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import vn.com.unit.core.enumdef.DocumentActionFlag;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileVersionDto;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFile;

/**
 * EfoOzDocMainFileVersionService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface EfoOzDocMainFileVersionService {

    /**
     * Save EfoHiOzDocMainFile
     * 
     * @param efoOzDocMainFile
     *            type {@link EfoOzDocMainFile}
     * @param actionFlag
     *            type {@link DocumentActionFlag}
     * @author tantm
     */
    void saveFromEfoOzDocMainFile(EfoOzDocMainFile efoOzDocMainFile, DocumentActionFlag actionFlag);

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
    List<EfoOzDocMainFileVersionDto> getListEfoOzDocMainFileVersionDtoByOzDocId(Long docId);

}
