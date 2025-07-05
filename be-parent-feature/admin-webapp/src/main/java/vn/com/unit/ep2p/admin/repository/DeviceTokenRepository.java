package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.DeviceTokenDto;
import vn.com.unit.ep2p.admin.entity.DeviceToken;

	
/**
 * DeviceTokenRepository
 * @author TaiTT
 */
public interface DeviceTokenRepository extends DbRepository<DeviceToken, Long> {

    /**
     * findByRefreshToken
     * 
     * @param refreshToken
     * @return
     * @author HungHT
     */
    DeviceTokenDto findByDeviceToken(@Param("deviceToken") String deviceToken);
    
    /**
     * findListDeviceTokenDtoByAccountId
     * @param accountId
     * @return List<DeviceTokenDto>
     * @author KhuongTH
     */
    List<DeviceToken> findDeviceTokenDtoByAccountId(@Param("accountId")Long accountId);
    
    @Modifying
    void deletedDeviceToken (@Param("deviceToken") String deviceToken,@Param("accountId")Long accountId) throws AppException;
    
    /**
     * findByAccountIdAndDeviceToken
     * @param accountId
     * @param deviceToken
     * @return DeviceToken
     * @author KhuongTH
     */
    DeviceToken findByAccountIdAndDeviceToken(@Param("accountId")Long accountId, @Param("deviceToken") String deviceToken);
    
    /**
     * deletedListDeviceToken
     * @param listDeviceTokenDelete
     * @author vynt
     */
    @Modifying
    void deletedListDeviceToken(@Param("listDeviceTokenDelete") List<Long> listDeviceTokenDelete);
}
