/*******************************************************************************
 * Class        ：AccountAppService
 * Created date ：2021/03/11
 * Lasted date  ：2021/03/11
 * Author       ：Tan Tai
 * Change log   ：2021/03/11：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.AccountAppUpdateReq;
import vn.com.unit.ep2p.dto.res.AccountInfoRes;

/**
 * AccountAppService.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public interface AccountAppService{

	/**
	 * getUserProfile.
	 *
	 * @author Tan Tai
	 * @return {@link AccountInfoRes}
	 * @throws DetailException the detail exception
	 */
	AccountInfoRes getUserProfile() throws DetailException;

	/**
	 * updateUserProfile.
	 *
	 * @author Tan Tai
	 * @param accountUpdateDtoReq type {@link AccountAppUpdateReq}
	 * @throws DetailException the detail exception
	 */
	void updateUserProfile(AccountAppUpdateReq accountUpdateDtoReq) throws DetailException;

}
