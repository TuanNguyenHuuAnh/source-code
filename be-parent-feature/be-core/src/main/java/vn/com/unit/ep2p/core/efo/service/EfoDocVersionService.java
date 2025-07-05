/*******************************************************************************
 * Class        ：EfoOzDocVersionService
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：tantm
 * Change log   ：2020/12/03：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import vn.com.unit.core.service.DocumentService;
import vn.com.unit.ep2p.core.efo.dto.EfoDocVersionDto;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.efo.entity.EfoDocVersion;

/**
 * EfoOzDocVersionService.
 *
 * @author tantm
 * @version 01-00
 * @since 01-00
 */
public interface EfoDocVersionService extends DocumentService<EfoDocVersion, EfoDocVersionDto, Long> {

    /**
     * Save EfoOzDocVersion from EfoOzDoc.
     *
     * @author tantm
     * @param efoDoc type {@link EfoDoc}
     * @return the efo hi oz doc
     */
	EfoDocVersion saveFromEfoDoc(EfoDoc efoDoc);

}
