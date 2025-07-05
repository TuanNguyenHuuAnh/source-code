/*******************************************************************************
 * Class        ：CaManagementService
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.CaManagementAddReq;
import vn.com.unit.ep2p.dto.req.CaManagementUpdateReq;
import vn.com.unit.ep2p.dto.res.CaManagementInfoRes;

/**
 * <p>
 * CaManagementService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface CaManagementService extends BaseRestService<ObjectDataRes<JcaCaManagementDto>, JcaCaManagementDto> {


    /**
     * <p>
     * Update.
     * </p>
     *
     * @param caManagementUpdateReq
     *            type {@link CaManagementUpdateReq}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    void update(CaManagementUpdateReq caManagementUpdateReq) throws Exception;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param caManagementAddReq
     *            type {@link CaManagementAddReq}
     * @return {@link CaManagementInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    CaManagementInfoRes create(CaManagementAddReq caManagementAddReq) throws Exception;


    /**
     * <p>
     * Get ca management info res by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link CaManagementInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    CaManagementInfoRes getCaManagementInfoResById(Long id) throws Exception;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    List<EnumsParamSearchRes> getListEnumSearch();
}
