/*******************************************************************************
 * Class        :RefreshTokenRepository
 * Created date :2019/07/01
 * Lasted date  :2019/07/01
 * Author       :HungHT
 * Change log   :2019/07/01:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.RefreshTokenDto;
import vn.com.unit.ep2p.admin.entity.RefreshToken;



/**
 * RefreshTokenRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface RefreshTokenRepository extends DbRepository<RefreshToken, Long> {

    /**
     * findByRefreshToken
     * 
     * @param refreshToken
     * @return
     * @author HungHT
     */
    RefreshTokenDto findByRefreshToken(@Param("refreshToken") String refreshToken);
    
    
    @Modifying
    void deletedRefreshToken(@Param("refreshToken") String refreshToken,@Param("accountId")Long accountId) throws AppException;

    @Modifying
    void clearRefreshToken(@Param("os") String os, @Param("versionApp")String versionApp);
}