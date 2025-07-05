/*******************************************************************************
 * Class        ：RepositoryService
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.RepositoryAddReq;
import vn.com.unit.ep2p.dto.req.RepositoryUpdateReq;
import vn.com.unit.ep2p.dto.res.RepositoryInfoRes;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.JcaRepositorySearchDto;

/**
 * RepositoryService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface FileRepositoryService extends BaseRestService<ObjectDataRes<JcaRepositoryDto>, JcaRepositoryDto> {

    /**
     * Count repository dto by condition.
     *
     * @param jcaRepositorySearchDto
     *            the jca repository search dto type JcaRepositorySearchDto
     * @return the int
     * @author tantm
     */
    int countRepositoryDtoByCondition(JcaRepositorySearchDto jcaRepositorySearchDto);

    /**
     * Gets the repository dto by condition.
     *
     * @param jcaRepositorySearchDto
     *            the jca repository search dto type JcaRepositorySearchDto
     * @param pageable
     *            the pageable type Pageable
     * @return the repository dto by condition
     * @author tantm
     */
    List<JcaRepositoryDto> getRepositoryDtoByCondition(JcaRepositorySearchDto jcaRepositorySearchDto, Pageable pageable);

    /**
     * Update Repository.
     *
     * @param reqRepositoryUpdateDto
     *            the req repository update dto type RepositoryUpdateReq
     * @throws Exception
     *             the exception
     * @author tantm
     */
    void update(RepositoryUpdateReq reqRepositoryUpdateDto) throws Exception;

    /**
     * Creates the Repository.
     *
     * @param reqRepositoryAddDto
     *            the req repository add dto type RepositoryAddReq
     * @return the repository info res
     * @throws Exception
     *             the exception
     * @author tantm
     */
    RepositoryInfoRes create(RepositoryAddReq reqRepositoryAddDto) throws Exception;

    /**
     * Gets the repository info res by id.
     *
     * @param id
     *            the id type Long
     * @return the repository info res by id
     * @throws Exception
     *             the exception
     * @author tantm
     */
    RepositoryInfoRes getRepositoryInfoResById(Long id) throws Exception;

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
