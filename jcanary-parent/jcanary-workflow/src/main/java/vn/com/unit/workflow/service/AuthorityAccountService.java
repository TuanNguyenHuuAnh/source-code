/*******************************************************************************
 * Class        ：AuthorityAccountService
 * Created date ：2021/03/07
 * Lasted date  ：2021/03/07
 * Author       ：Tan Tai
 * Change log   ：2021/03/07：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.AuthorityAccountDto;

/**
 * AuthorityAccountService.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public interface AuthorityAccountService {

	/**
	 * getAssigneeIdsByStepDeployId.
	 *
	 * @author Tan Tai
	 * @param stepDeployId type {@link Long}
	 * @return {@link List<Long>}
	 */
	public default List<Long> getAssigneeIdsByStepDeployId(Long stepDeployId) throws Exception{
		return null;
	}

	/**
	 * getInfoAccountById.
	 *
	 * @author Tan Tai
	 * @param accountId type {@link Long}
	 * @return {@link AuthorityAccountDto}
	 */
	public default AuthorityAccountDto getInfoAccountById(Long accountId) {
		return null;
	}

	/**
	 * getNamePositionById.
	 *
	 * @author Tan Tai
	 * @param positionId type {@link Long}
	 * @return {@link String}
	 */
	public default String getNamePositionById(Long positionId) {
		return null;
	}

	/**
	 * getNameOrgById.
	 *
	 * @author Tan Tai
	 * @param orgId type {@link Long}
	 * @return {@link String}
	 */
	public default  String getNameOrgById(Long orgId) {
		return null;
	}

}
