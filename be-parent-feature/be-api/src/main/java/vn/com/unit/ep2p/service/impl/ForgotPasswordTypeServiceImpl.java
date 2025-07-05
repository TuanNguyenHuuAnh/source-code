package vn.com.unit.ep2p.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import vn.com.unit.ep2p.constant.ApiOtpConstant;
import vn.com.unit.ep2p.service.ForgotPasswordTypeService;

@Service
public class ForgotPasswordTypeServiceImpl implements ForgotPasswordTypeService {

    private LoadingCache<String, String> forgotCache;

    // Cache Forgot password
    public ForgotPasswordTypeServiceImpl() {
        super();
        forgotCache = CacheBuilder.newBuilder().expireAfterWrite(ApiOtpConstant.OTP_TYPE_EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) {
                        return "";
                    }
                });
    }

    @Override
    public void createForgotType(String key, String type) {
        forgotCache.put(key, type);
    }

    @Override
    public String getForgotType(String key) {
        try {
            return forgotCache.get(key);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public boolean checkForgotType(String key, String typeCheck) throws ExecutionException {
        boolean check = false;
        if (StringUtils.isNotBlank(getForgotType(key)) && getForgotType(key).equals(typeCheck)) {
            check = true;
        }
        return check;
    }

}
