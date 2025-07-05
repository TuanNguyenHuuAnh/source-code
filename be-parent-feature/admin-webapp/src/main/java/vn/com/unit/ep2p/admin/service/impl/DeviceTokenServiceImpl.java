package vn.com.unit.ep2p.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.utils.CommonDateUtil
;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.dto.DeviceTokenDto;
import vn.com.unit.ep2p.admin.entity.DeviceToken;
import vn.com.unit.ep2p.admin.repository.DeviceTokenRepository;
import vn.com.unit.ep2p.admin.repository.RefreshTokenRepository;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.DeviceTokenService;

@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceTokenServiceImpl implements DeviceTokenService {

    @Autowired
    CommonService commonService;
    
    @Autowired
    DeviceTokenRepository deviceTokenRepository;
    
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    
    // Model mapper
    ModelMapper modelMapper = new ModelMapper();
	
    
	 /**
     * findByDeviceToken
     * 
     * @param deviceToken
     * @return
     * @author TaiTT
     */
    public DeviceTokenDto findByDeviceToken(String deviceToken) {
        return deviceTokenRepository.findByDeviceToken(deviceToken);
    }

    
	  /**
     * saveDeviceToken
     * 
     * @param deviceTokenObj
     * @param userName
     * @throws Exception
     * @author TaiTT
     */
    public void saveDeviceToken(DeviceTokenDto deviceTokenObj, String userName,String lang) throws Exception {
    	  Date sysDate = commonService.getSystemDateTime();
          if (null != deviceTokenObj) {
              Long accountId = deviceTokenObj.getAccountId();
              checkAndDeleteDeviceToken(accountId);
              DeviceToken objectCurrent = deviceTokenRepository.findByAccountIdAndDeviceToken(accountId, deviceTokenObj.getDeviceToken());
              if (null != objectCurrent) {
                  objectCurrent.setUpdatedBy(userName);
                  objectCurrent.setUpdatedDate(sysDate);
                  objectCurrent.setAccountId(deviceTokenObj.getAccountId());
                  objectCurrent.setLangCode(lang.toUpperCase());
              } else {
                  objectCurrent = modelMapper.map(deviceTokenObj, DeviceToken.class);
                  objectCurrent.setId(null);
                  objectCurrent.setLangCode(lang);
                  objectCurrent.setCreatedBy(userName);
                  objectCurrent.setCreatedDate(sysDate);
              }
              deviceTokenRepository.save(objectCurrent);
          } else {
              throw new Exception();
          }
    }


    /**
     * @author KhuongTH
     */
    @Override
    public List<DeviceToken> findDeviceTokenDtoByAccountId(Long accountId) {
        return deviceTokenRepository.findDeviceTokenDtoByAccountId(accountId);
    }


	@Override
	@Transactional
	public boolean deletedDeivceTokenAndRefreshToken(String deviceToken,Long accountId,String refreshToken) throws AppException{
		boolean result = false;
        DeviceTokenDto objectDto = this.findByDeviceToken(deviceToken);
        if (null !=objectDto){
        	try {
        		deviceTokenRepository.deletedDeviceToken(deviceToken,accountId);
        		refreshTokenRepository.deletedRefreshToken(refreshToken,accountId);
        		result = true;
        	}catch (AppException e){
        		throw new  AppException("B109",null);
        	}
        }                       
		return result;
	}
	
	@Override
	@Transactional
	public void deletedRefreshToken(Long accountId,String refreshToken) throws AppException{
		try{
    		refreshTokenRepository.deletedRefreshToken(refreshToken,accountId);
    	}catch (AppException e){
    		throw new  AppException("B109",null);
    	}                    
	}


	/**
     * @author datnv
	 * @throws Exception 
     */
	@Override
	public void checkLoginAccountIdForUpdate(Long loginAccountId, String deviceToken) throws Exception {
		DeviceTokenDto existDeviceTokenDto = deviceTokenRepository.findByDeviceToken(deviceToken.trim());
		if(existDeviceTokenDto != null) {
			Date sysDate = commonService.getSystemDateTime();
			
			Long existAccountId = existDeviceTokenDto.getAccountId();
			if(!loginAccountId.equals(existAccountId)) {
				existDeviceTokenDto.setAccountId(loginAccountId);
				existDeviceTokenDto.setUpdatedBy(UserProfileUtils.getUserNameLogin());
				existDeviceTokenDto.setUpdatedDate(sysDate);
				
				DeviceToken currentObject = modelMapper.map(existDeviceTokenDto, DeviceToken.class);
				deviceTokenRepository.save(currentObject);
			}
		}else {
			throw new Exception("Oh dear why device token not exist in DB");
		}
	}

    /**
     * @author KhuongTH
     */
    @Override
    public void saveDeviceTokenForWeb(DeviceTokenDto deviceTokenObj) throws Exception {
        String deviceToken = deviceTokenObj.getDeviceToken();
        DeviceTokenDto existDeviceTokenDto = deviceTokenRepository.findByDeviceToken(deviceToken.trim());
        DeviceToken existDeviceToken = null;
        Long accountId = deviceTokenObj.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        String userLogin = UserProfileUtils.getUserNameLogin();
        checkAndDeleteDeviceToken(accountId);
        if (null != existDeviceTokenDto) {
            existDeviceToken = modelMapper.map(existDeviceTokenDto, DeviceToken.class);
            existDeviceToken.setAccountId(accountId);
            existDeviceToken.setUpdatedBy(userLogin);
            existDeviceToken.setUpdatedDate(sysDate);
        } else {
            existDeviceToken = new DeviceToken();
            existDeviceToken.setAccountId(accountId);
            existDeviceToken.setDeviceToken(deviceToken.trim());
            existDeviceToken.setCreatedBy(userLogin);
            existDeviceToken.setCreatedDate(sysDate);
            existDeviceToken.setUpdatedBy(userLogin);
            existDeviceToken.setUpdatedDate(sysDate);
        }
        deviceTokenRepository.save(existDeviceToken);
    }

    /**
     * @author KhuongTH
     */
    @Override
    public void deleteDeviceTokenForWeb(DeviceTokenDto deviceTokenObj) throws Exception {
        String deviceToken = deviceTokenObj.getDeviceToken();
        deviceTokenRepository.deletedDeviceToken(deviceToken, null);
    }
    
    /**
     * checkAndDeleteDeviceToken
     * @param accountId
     * @author vynt
     */
    private void checkAndDeleteDeviceToken(Long accountId) {
//        List<Long> listDeviceTokenDelete = new ArrayList<Long>();
//        List<DeviceToken> listDeviceToken = this.findDeviceTokenDtoByAccountId(accountId);
//        Date today = CommonDateUtil.getSystemDateTime();
//        for (DeviceToken deviceToken : listDeviceToken) {
//            Date createDate = deviceToken.getCreatedDate();
//            Date updateDate = deviceToken.getUpdatedDate();
//            if (updateDate == null ) {
//                createDate = CommonDateUtil.addDate(createDate, ConstantCore.DAY_CHECK_DEVICE_TOKEN);
//                if (CommonDateUtil.dateAfterDate(today, createDate)) {
//                    listDeviceTokenDelete.add(deviceToken.getId());
//                }
//            }else {
//                updateDate = CommonDateUtil.addDate(updateDate, ConstantCore.DAY_CHECK_DEVICE_TOKEN);
//                if (CommonDateUtil.dateAfterDate(today, updateDate)) {
//                    listDeviceTokenDelete.add(deviceToken.getId());
//                }
//            }
//        }
//        if (CollectionUtils.isNotEmpty(listDeviceTokenDelete)) {
//            deviceTokenRepository.deletedListDeviceToken(listDeviceTokenDelete);
//        }
    }
}
