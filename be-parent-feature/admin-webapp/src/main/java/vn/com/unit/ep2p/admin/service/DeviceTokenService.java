package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.ep2p.admin.dto.DeviceTokenDto;
import vn.com.unit.ep2p.admin.entity.DeviceToken;


public interface DeviceTokenService {

	   /**
     * findByDeviceToken
     * 
     * @param deviceToken
     * @return
     * @author TaiTT
     */
    DeviceTokenDto findByDeviceToken(String deviceToken);
    
    /**
     * saveDeiveToken
     * 
     * @param deviceTokenObj
     * @param userName
     * @throws Exception
     * @author TaiTT
     */
    void saveDeviceToken(DeviceTokenDto deviceTokenObj, String userName,String lang) throws Exception;
    
    /**
     * findDeviceTokenDtoByAccountId
     * @param accountId
     * @return List<DeviceToken>
     * @author KhuongTH
     */
    List<DeviceToken> findDeviceTokenDtoByAccountId(Long accountId);
    
    /**
     * 
     * deletedDeivceToken
     * @param deviceToken
     * @return
     * @throws AppException
     * @author taitt
     */
    boolean deletedDeivceTokenAndRefreshToken(String deviceToken,Long accountId,String refreshToken) throws AppException;
    
    /**
     * 
     * deletedRefreshToken
     * @param accountId
     * @param refreshToken
     * @return
     * @throws AppException
     * @author taitt
     */
    void deletedRefreshToken(Long accountId,String refreshToken) throws AppException;
    
    /**
     * 
     * checkLoginAccountIdForUpdate
     * @param deviceToken, accountId
     * @return
     * @throws AppException
     * @author datnv
     */
    void checkLoginAccountIdForUpdate(Long loginAccountId, String deviceToken)  throws Exception;
    
    /**
     * saveDeviceTokenForWeb
     *
     * @param deviceTokenObj
     * @throws Exception void
     * @author KhuongTH
     */
    void saveDeviceTokenForWeb(DeviceTokenDto deviceTokenObj) throws Exception;
    
    /**
     * deleteDeviceTokenForWeb
     *
     * @param deviceTokenObj
     * @throws Exception void
     * @author KhuongTH
     */
    void deleteDeviceTokenForWeb(DeviceTokenDto deviceTokenObj) throws Exception;
}
