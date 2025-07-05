/*******************************************************************************
 * Class        ：ConstantService
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：tantm
 * Change log   ：2020/12/01：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
//import vn.com.unit.ep2p.api.req.dto.ConstantUpdateReq;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.ConstantAddReq;
import vn.com.unit.ep2p.dto.res.ConstantInfoRes;

/**
 * ConstantService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface ConstantService extends BaseRestService<ObjectDataRes<JcaConstantDto>, JcaConstantDto> {

    /**
     * Count group constant dto by condition.
     *
     * @param jcaConstantSearchDto
     *            the jca group constant search dto type JcaConstantSearchDto
     * @return the int
     * @author tantm
     */
    int countJcaConstantByCondition(JcaConstantSearchDto jcaConstantSearchDto);

    /**
     * Gets the group constant dto by condition.
     *
     * @param jcaConstantSearchDto
     *            the jca group constant search dto type JcaConstantSearchDto
     * @param pageable
     *            the pageable type Pageable
     * @return the group constant dto by condition
     * @author tantm
     */
    List<JcaConstantDto> getConstantDtoByCondition(JcaConstantSearchDto jcaConstantSearchDto, Pageable pageable);

    /**
     * Update Constant.
     *
     * @param reqConstantUpdateDto
     *            the req group constant update dto type ConstantUpdateReq
     * @throws Exception
     *             the exception
     * @author tantm
     */
    void update(ConstantAddReq reqConstantAddDto) throws Exception;

    /**
     * Creates the Constant.
     *
     * @param reqConstantAddDto
     *            the req group constant add dto type ConstantAddReq
     * @return the group constant info res
     * @throws Exception
     *             the exception
     * @author tantm
     */
    List<JcaConstantDto> create(ConstantAddReq reqConstantAddDto) throws Exception;

    /**
     * Gets the group constant info res by id.
     *
     * @param id
     *            the id type Long
     * @return the group constant info res by id
     * @throws Exception
     *             the exception
     * @author tantm
     */
    ConstantInfoRes getConstantInfoResById(Long id) throws Exception;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    List<EnumsParamSearchRes> getListEnumSearch();
    
    public ConstantInfoRes getConstantInfoResByCodeAndGroupCodeAndKind(String groupCode, String kind, String code,
            String langCode);
}
