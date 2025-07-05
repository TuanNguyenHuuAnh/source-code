/*******************************************************************************
 * Class        :JcaRoleForAccountService
 * Created date :2021/01/22
 * Lasted date  :2021/01/22
 * Author       :SonND
 * Change log   :2021/01/22 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.entity.JcaRoleForAccount;

/**
 * JcaRoleForAccountService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaRoleForAccountService {

	/**
     * <p>
     * Save jca role for account.
     * </p>
     *
     * @param jcaRoleForAccount
     *            type {@link JcaRoleForAccount}
     * @return {@link JcaRoleForAccount}
     * @author sonnd
     */
	public JcaRoleForAccount saveJcaRoleForAccount(JcaRoleForAccount jcaRoleForAccount);
	
	/**
     * <p>
     * Save jca role for account dto.
     * </p>
     *
     * @param jcaRoleForAccountDto
     *            type {@link JcaRoleForAccountDto}
     * @return {@link JcaRoleForAccount}
     * @author sonnd
     */
	public JcaRoleForAccount saveJcaRoleForAccountDto(JcaRoleForAccountDto jcaRoleForAccountDto);
	
	/**
     * <p>
     * Delete jca role for account by user id.
     * </p>
     *
     * @param userId
     *            type {@link Long}
     * @author sonnd
     */
	public void deleteJcaRoleForAccountByUserId(Long userId);
	
	/**
     * <p>
     * Gets the jca role for account dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca role for account dto by id
     * @author sonnd
     */
	public JcaRoleForAccountDto getJcaRoleForAccountDtoById(Long id);
	
	/**
     * <p>
     * Gets the jca role for account dto by user id.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @return the jca role for account dto by user id
     * @author sonnd
     */
	public List<JcaRoleForAccountDto> getJcaRoleForAccountDtoByUserId(Long accountId);
	
	/**
     * <p>
     * Delete jca role for account by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author sonnd
     */
	public void deleteJcaRoleForAccountById(Long id);
	
}
