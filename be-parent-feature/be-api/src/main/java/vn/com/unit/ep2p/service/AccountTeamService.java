/*******************************************************************************
 * Class        ：AccountTeamService
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.AccountTeamUpdateAccountReq;
import vn.com.unit.ep2p.dto.res.AccountTeamInfoRes;

/**
 * AccountTeamService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface AccountTeamService{

    /**
     * addAccountForTeam.
     *
     * @param accountTeamAddAccountReq
     *            type {@link AccountTeamUpdateAccountReq}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    void addAccountForTeam(AccountTeamUpdateAccountReq accountTeamAddAccountReq) throws DetailException;

    /**
     * deleteListUserForTeam.
     *
     * @param accountTeamUpdateAccountReq
     *            type {@link AccountTeamUpdateAccountReq}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    void deleteListUserForTeam(AccountTeamUpdateAccountReq accountTeamUpdateAccountReq) throws DetailException;

    /**
     * search.
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<JcaTeamDto>}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    ObjectDataRes<JcaTeamDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException;

    /**
     * detail.
     *
     * @param teamId
     *            type {@link Long}
     * @return {@link AccountTeamInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    AccountTeamInfoRes detail(Long teamId) throws DetailException;

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
