/*******************************************************************************
 * Class        ：SlaService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.sla.service;

import vn.com.unit.dts.exception.DetailException;

/**
 * <p>
 * SlaService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaService {

    /**
     * <p>
     * Clone sla config.
     * </p>
     *
     * @author TrieuVD
     * @param slaConfigId
     *            type {@link Long}
     * @return {@link Long}
     * @throws DetailException 
     */
    public Long cloneSlaConfig(Long slaConfigId) throws DetailException;
}
