/*******************************************************************************
 * Class        : AccountPasswordRepository
 * Created date : 2018/06/22
 * Lasted date  : 2018/06/22
 * Author       : LongPNT
 * Change log   : 2018/06/22:01-00 LongPNT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.entity.AccountPassword;

/**
 * AccountPasswordRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author LongPNT
 */
public interface AccountPasswordRepository extends DbRepository<AccountPassword, Long> {

	/**
	 * 
	 * Get active account's password by account ID. Use for SQLServer, MySQL
	 * 
	 * @param accountId
	 * @return AccountPassword
	 * @author LongPNT
	 */
	public AccountPassword getActiveDataByAccountID(@Param("accountId") Long accountId);

	/**
	 * 
 	 * Get active account's password by account ID. Use for ORACLE
	 * 
	 * @param accountId
	 * @return AccountPassword
	 * @author LongPNT
	 */
	public AccountPassword getActiveDataByAccountIDOracle(@Param("accountId") Long accountId);

	/**
	 * 
	 * Get list account's history password. Used for SQLServer
	 * 
	 * @param accountId
	 * @param numberOfOldPassword number of old passwords
	 * @return List<String> list encrypted password
	 * @author LongPNT
	 */
	public List<String> getListHistoryPassword(@Param("accountId") Long accountId, @Param("numberOfOldPassword") int numberOfOldPassword);
	
	/**
	 * 
	 * Get list account's history password. Used for MySQL
	 * 
	 * @param accountId
	 * @param numberOfOldPassword number of old passwords
	 * @return List<String> list encrypted password
	 * @author LongPNT
	 */
	public List<String> getListHistoryPasswordMySQL(@Param("accountId") Long accountId, @Param("numberOfOldPassword") int numberOfOldPassword);
	
	/**
	 * 
	 * Get list account's history password. Used for ORACLE
	 * 
	 * @param accountId
	 * @param numberOfOldPassword number of old passwords
	 * @return List<String> list encrypted password
	 * @author LongPNT
	 */
	public List<String> getListHistoryPasswordOracle(@Param("accountId") Long accountId, @Param("numberOfOldPassword") int numberOfOldPassword);
	/**
	 * getAccountPasswordByAccountId
	 *
	 * @param accountId
	 * @return
	 * @author phatvt
	 */
	public AccountPassword getAccountPasswordByAccountId(@Param("accountId")Long accountId);
	
}
