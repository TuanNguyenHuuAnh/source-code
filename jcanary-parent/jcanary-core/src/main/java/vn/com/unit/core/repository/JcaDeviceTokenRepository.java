/*******************************************************************************
 * Class        ：JcaDeviceTokenRepository
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.entity.JcaDeviceToken;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * JcaDeviceTokenRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface JcaDeviceTokenRepository extends DbRepository<JcaDeviceToken, Long>{
    
    public List<String> getListTokenByAccountId(@Param("accountId")Long accountId);
    
    public List<String> getListTokenByListAccountId(@Param("accountIds")List<Long> accountId);
}
