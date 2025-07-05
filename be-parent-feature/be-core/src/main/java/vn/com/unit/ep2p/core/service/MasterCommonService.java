/*******************************************************************************
 * Class        ：MasterCommonService
 * Created date ：2021/01/26
 * Lasted date  ：2021/01/26
 * Author       ：taitt
 * Change log   ：2021/01/26：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;

/**
 * MasterCommonService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface MasterCommonService {

    /**
     * getMapOrgAndPosByAccountId.
     *
     * @param accountId
     *            type {@link Long}
     * @return {@link Map<String,List<Long>>}
     * @author taitt
     */
    Map<String, List<Long>> getMapOrgAndPosByAccountId(Long accountId);

    /**
     * getEnumsParamSearchResForEnumClass.
     *
     * @param <T>
     *            the generic type
     * @param enumData
     *            type {@link Class<Enum<?>>}
     * @return {@link List<EnumsParamSearchRes>}
     * @author taitt
     */
    public <T extends Enum<T>> List<EnumsParamSearchRes> getEnumsParamSearchResForEnumClass(T[] enumData);

}
