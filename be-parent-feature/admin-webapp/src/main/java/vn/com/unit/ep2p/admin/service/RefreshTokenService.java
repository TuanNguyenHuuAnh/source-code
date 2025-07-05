/*******************************************************************************
 * Class        :RefreshTokenService
 * Created date :2019/07/01
 * Lasted date  :2019/07/01
 * Author       :HungHT
 * Change log   :2019/07/01:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import vn.com.unit.ep2p.admin.dto.RefreshTokenDto;

/**
 * RefreshTokenService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface RefreshTokenService {

    /**
     * findByRefreshToken
     * 
     * @param refreshToken
     * @return
     * @author HungHT
     */
    RefreshTokenDto findByRefreshToken(String refreshToken);
    
    /**
     * saveRefreshToken
     * 
     * @param refreshTokenObj
     * @param userName
     * @throws Exception
     * @author HungHT
     */
    void saveRefreshToken(RefreshTokenDto refreshTokenObj, String userName) throws Exception;
    
    /**
     * clearRefreshToken
     *
     * @param os
     * @param version void
     * @author KhuongTH
     */
    void clearRefreshToken(String os, String version);
}