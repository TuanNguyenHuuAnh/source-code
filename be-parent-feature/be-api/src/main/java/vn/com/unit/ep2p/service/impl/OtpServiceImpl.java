package vn.com.unit.ep2p.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import vn.com.unit.ep2p.constant.ApiOtpConstant;
import vn.com.unit.ep2p.service.OtpService;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@Service
public class OtpServiceImpl implements OtpService {
    
    private LoadingCache<String, String> otpCache;

    public OtpServiceImpl() {
        super();
        otpCache = CacheBuilder.newBuilder().expireAfterWrite(ApiOtpConstant.OTP_EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) {
                        return "0";
                    }
                });
    }

    // This method is used to push the opt number against Key. Rewrite the OTP if it
    // exists
    // Using user id as key
    @Override
    public String generateOTP(String key) {

//        Random random = new Random();
//        int otp = 100000 + random.nextInt(900000);
        String otp = RandomStringUtils.randomNumeric(6);
        otpCache.put(key, otp);
        return otp;
    }

    // This method is used to return the OPT number against Key->Key values is
    // username
    @Override
    public String getOtp(String key) {
        try {
            return otpCache.get(key);
        } catch (Exception e) {
            return "0";
        }
    }

    // This method is used to clear the OTP catched already
    @Override
    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }
    
    @Override
    public String checkKey(String otp) {
        String result = "";
        Map<String, String> map = otpCache.asMap();
        if (StringUtils.isNotBlank(otp) && map.values().contains(otp)) {
            for (String key : otpCache.asMap().keySet()) {
                if (map.get(key).equals(otp)) {
                    result = key;
                    break;
                }
            }
        }

        return result;
    }
    
    @Override
    public void setIncorrectOtp(String key, String count) {
        otpCache.put(key, count);
    }
    
    @Override
    public int getIncorrectOtp(String key) {
        try {
            return Integer.valueOf(otpCache.get(key));
        } catch (Exception e) {
            return 0;
        }
    }
}
