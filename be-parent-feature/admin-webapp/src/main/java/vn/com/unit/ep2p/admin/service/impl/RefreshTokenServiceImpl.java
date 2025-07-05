/*******************************************************************************
 * Class        :RefreshTokenServiceImpl
 * Created date :2019/07/01
 * Lasted date  :2019/07/01
 * Author       :HungHT
 * Change log   :2019/07/01:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.dto.RefreshTokenDto;
import vn.com.unit.ep2p.admin.entity.RefreshToken;
import vn.com.unit.ep2p.admin.repository.RefreshTokenRepository;
import vn.com.unit.ep2p.admin.service.RefreshTokenService;

/**
 * RefreshTokenServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
/**
 * RefreshTokenServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    CommonService commonService;

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    /**
     * findByRefreshToken
     * 
     * @param refreshToken
     * @return
     * @author HungHT
     */
    public RefreshTokenDto findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    /**
     * saveRefreshToken
     * 
     * @param refreshTokenObj
     * @param userName
     * @throws Exception
     * @author HungHT
     */
    public void saveRefreshToken(RefreshTokenDto refreshTokenObj, String userName) throws Exception {
        RefreshToken objectResult = null;
        Date sysDate = commonService.getSystemDateTime();
        if (null != refreshTokenObj) {
            objectResult = modelMapper.map(refreshTokenObj, RefreshToken.class);
            if (null != objectResult) {
                if (null != objectResult.getId()) {
                    RefreshToken objectCurrent = refreshTokenRepository.findOne(objectResult.getId());
                    objectResult.setCreatedBy(objectCurrent.getCreatedBy());
                    objectResult.setCreatedDate(objectCurrent.getCreatedDate());
                    objectResult.setUpdatedBy(userName);
                    objectResult.setUpdatedDate(sysDate);
                    if(StringUtils.isBlank(objectResult.getOs())) {
                        objectResult.setOs(objectCurrent.getOs());
                        objectResult.setVersionApp(objectCurrent.getVersionApp());
                    }
                } else {
                    objectResult.setCreatedBy(userName);
                    objectResult.setCreatedDate(sysDate);
                }
                objectResult = refreshTokenRepository.save(objectResult);
            }
        } else {
            throw new Exception();
        }
    }

    /**
     * @author KhuongTH
     */
    @Override
    public void clearRefreshToken(String os, String version) {
        refreshTokenRepository.clearRefreshToken(os, version);
    }

}