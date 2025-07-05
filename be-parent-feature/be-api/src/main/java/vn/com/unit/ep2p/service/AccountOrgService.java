/*******************************************************************************
 * Class        ：AccountOrgService
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：SonND
 * Change log   ：2020/12/16：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountOrgSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.AccountOrgInfoReq;
import vn.com.unit.ep2p.core.req.dto.AccountOrgUpdateInfoReq;
import vn.com.unit.ep2p.dto.res.AccountOrgInfoRes;

/**
 * AccountOrgService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface AccountOrgService extends BaseRestService<ObjectDataRes<JcaAccountOrgDto>, JcaAccountOrgDto>{
    
    /**
     * <p>
     * Count account org dto by condition.
     * </p>
     *
     * @param jcaAccountOrgSearchDto
     *            type {@link JcaAccountOrgSearchDto}
     * @return {@link int}
     * @author sonnd
     */
    int countAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto);
    
    /**
     * <p>
     * Gets the account org dto by condition.
     * </p>
     *
     * @param jcaAccountOrgSearchDto
     *            type {@link JcaAccountOrgSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the account org dto by condition
     * @author sonnd
     */
    List<JcaAccountOrgDto> getAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto, Pageable pageable);

    /**
     * <p>
     * Update.
     * </p>
     *
     * @param accountOrgUpdateInfoReq
     *            type {@link AccountOrgUpdateInfoReq}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    void update(AccountOrgUpdateInfoReq accountOrgUpdateInfoReq) throws DetailException;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param accountOrgInfoReq
     *            type {@link AccountOrgInfoReq}
     * @return {@link AccountOrgInfoRes}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    AccountOrgInfoRes create(AccountOrgInfoReq accountOrgInfoReq) throws DetailException;

   

}
