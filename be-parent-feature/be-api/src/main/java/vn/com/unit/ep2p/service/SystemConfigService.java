/*******************************************************************************
 * Class        ：SystemConfigService
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：ngannh
 * Change log   ：2020/12/11：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.SystemConfigAddReq;
import vn.com.unit.ep2p.dto.req.SystemConfigUpdateReq;
import vn.com.unit.ep2p.dto.res.SystemConfigInfoRes;

/**
 * SystemConfigService.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface SystemConfigService extends BaseRestService<ObjectDataRes<JcaSystemConfigDto>, JcaSystemConfigDto> {



    /**
     * create.
     *
     * @param configParamsAddReq
     *            type {@link SystemConfigAddReq}
     * @return {@link SystemConfigInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author ngannh
     */
    SystemConfigInfoRes create(SystemConfigAddReq configParamsAddReq) throws DetailException;

    /**
     * delete.
     *
     * @param configParamsId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author ngannh
     */
    void delete(Long configParamsId) throws DetailException;

    /**
     * update.
     *
     * @param configParamsUpdateReq
     *            type {@link SystemConfigUpdateReq}
     * @throws Exception
     *             the exception
     * @author ngannh
     */
    void update(SystemConfigUpdateReq configParamsUpdateReq) throws Exception;

    /**
     * getSystemConfigInfoResById.
     *
     * @param id
     *            type {@link Long}
     * @return {@link SystemConfigInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author ngannh
     */
    SystemConfigInfoRes getSystemConfigInfoResById(Long id) throws DetailException;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    List<EnumsParamSearchRes> getListEnumSearch();

	/**
	 * @author vunt
	 * @param settingKey
	 * @return
	 */
    JcaSystemConfigDto getConfigByKey(String settingKey, Long companyId);

}
