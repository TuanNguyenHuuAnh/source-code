/*******************************************************************************
 * Class        ：GroupSystemConfigService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.core.dto.JcaGroupSystemConfigDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.GroupSystemConfigAddReq;
import vn.com.unit.ep2p.dto.req.GroupSystemConfigUpdateReq;
import vn.com.unit.ep2p.dto.res.GroupSystemConfigInfoRes;

/**
 * GroupSystemConfigService.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface GroupSystemConfigService extends BaseRestService<ObjectDataRes<JcaGroupSystemConfigDto>, JcaGroupSystemConfigDto>{

 
    /**
     * <p>
     * Create new group systemConfig
     * </p>
     * .
     *
     * @param groupSystemConfigAddDtoReq
     *            the group system config add dto req
     * @return the group system config info res
     * @throws Exception
     *             the exception
     * @author ngannh
     */
     public GroupSystemConfigInfoRes create(GroupSystemConfigAddReq groupSystemConfigAddDtoReq) throws Exception;

    /**
     * <p>
     * Update groupSystemconfig
     * </p>
     * .
     *
     * @param groupSystemConfigUpdateDtoReq
     *            type {@link GroupSystemConfigUpdateReq}
     * @throws Exception
     *             the exception
     * @author ngannh
     */
    void update(GroupSystemConfigUpdateReq groupSystemConfigUpdateDtoReq) throws Exception;

    /**
     * <p>
     * Delete groupSystemConfig
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author ngannh
     */
    public void delete(Long id) throws DetailException;

    /**
     * <p>
     * get GroupSystemConfigInfoRes By Id
     * </p>
     * .
     *
     * @param userId
     *            type {@link Long}
     * @return {@link GroupSystemConfigInfoRes}
     * @throws Exception
     *             the exception
     * @author ngannh
     */
    public GroupSystemConfigInfoRes getGroupSystemConfigInfoResById(Long userId) throws Exception;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    public List<EnumsParamSearchRes> getListEnumSearch();



}
