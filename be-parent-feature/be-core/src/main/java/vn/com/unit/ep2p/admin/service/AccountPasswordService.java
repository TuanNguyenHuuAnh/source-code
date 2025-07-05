/*******************************************************************************
 * Class        : AccountPasswordService
 * Created date : 2018/06/22
 * Lasted date  : 2018/06/22
 * Author       : LongPNT
 * Change log   : 2018/06/22:01-00 LongPNT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import javassist.NotFoundException;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.ep2p.admin.entity.AccountPassword;

/**
 * AccountPasswordService
 * 
 * @version 01-00
 * @since 01-00
 * @author LongPNT
 */
public interface AccountPasswordService {

	/**
	 * 
	 * Call when login
	 * 
	 * @param accountId
	 * @throws NotFoundException
	 * @author LongPNT
	 * @return 
	 */
	boolean checkWhenLogin(Long accountId) throws NotFoundException;

	/**
	 * Create new account's password
	 * 
	 * @param accountId
	 * @param password
	 * @throws Exception
	 * @author LongPNT
	 */
	void createNewPassword(Long accountId, String password) throws Exception;

	/**
	 * Get list account's history password
	 * 
	 * @param accountId
	 * @return List<String>
	 * @author LongPNT
	 */
	List<String> getListHistoryPassword(Long accountId);

	/**
	 * Check the account first time login
	 * 
	 * @param accountId
	 * @return
	 * @author LongPNT
	 */
	boolean isFirstLogin(Long accountId);

	/**
	 * If password have been used then return true
	 * 
	 * @param accountId
	 * @param password
	 *            (MD5 encrypted)
	 * @return boolean
	 * @author LongPNT
	 */
	boolean passwordHaveBeenUsed(Long accountId, String password);

	/**
	 * Update account when first login
	 * 
	 * @param accountId
	 * @author LongPNT
	 */
	void updateWhenFirstLogin(Long accountId);

    /** checkPassExpired
     *
     * @param accountId
     * @return
     * @author phatvt
     */
    boolean checkPassExpired(JcaAccount account);

    /** updateAccountPass
     *
     * @param accountId
     * @param enPassword
     * @author phatvt
     * @throws NotFoundException 
     */
    void updateAccountPass(Long accountId, String enPassword) throws NotFoundException;

    /** getHistoryPassword
     *
     * @param accountId
     * @return
     * @author phatvt
     */
    AccountPassword getHistoryPassword(Long accountId);
    
    /**
     * updateExpiredDateAccountPass
     * @param accountId
     * @author DaiTrieu
     */
    void updateExpiredDateAccountPass(Long accountId);
}
