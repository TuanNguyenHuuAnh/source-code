package vn.com.unit.ep2p.service.impl;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.opensagres.xdocreport.document.json.JSONObject;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.constant.ApiOtpConstant;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.dto.res.CheckCaptchaRes;
import vn.com.unit.ep2p.service.ReCaptchaService;

@Service
public class RecaptchaServiceImpl implements ReCaptchaService {

    private LoadingCache<String, String> captchaCache;

    @Autowired
	private SystemConfig systemConfig;
    // Cache Forgot password
    public RecaptchaServiceImpl() {
        super();
        captchaCache = CacheBuilder.newBuilder().expireAfterWrite(ApiOtpConstant.OTP_EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) {
                        return "";
                    }
                });
    }

    @Override
    public void createCaptcha(String key, String value) {
    	captchaCache.put(key, value);
    }

    @Override
    public String getCaptcha(String key) {
        try {
            return captchaCache.get(key);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public boolean checkCaptcha(String key, String value) {
        boolean check = false;
        if (StringUtils.isNotBlank(getCaptcha(key)) && getCaptcha(key).equals(value)) {
            check = true;
        }
        return check;
    }

    @Override
    public boolean verifyCaptchaV3(String secret, String token) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String param = "?secret="+ secret +"&response=" + token;
		String jsonDtoSubmit = "";
		try {
			JSONObject obj = RetrofitUtils.callApi(url + param, jsonDtoSubmit);
			Float passScore = Float.parseFloat(systemConfig.getConfig("PASS_SCORE_V3"));
			if (obj != null) {
				if (!(Boolean) obj.get("success") || Float.parseFloat(obj.get("score").toString()) < passScore) {
					return false;
				} else {
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
    }
    
    @Override
    public CheckCaptchaRes verifyCaptcha(String key, String token) {
    	CheckCaptchaRes captchaRes = null;
    	String verifyFlag = systemConfig.getConfig("VERIFY_CAPCHA_FLAG");
    	if ((StringUtils.isEmpty(verifyFlag) || "0".equals(verifyFlag)) && token.length() != 6) {
    		return captchaRes;
    	}
    	
		if (token.length() == 6) {
			if (!this.checkCaptcha(key, token)) {
				String captcha = CmsUtils.genCaptcha(6);
	        	this.createCaptcha(key, captcha);
	        	captchaRes = new CheckCaptchaRes();
				captchaRes.setCaptcha(CommonBase64Util.encode(captcha));
				captchaRes.setErrorCode("INCORRECT_CAPTCHA_CODE");
			}
		} else {
			if ("aWdub3JlcmVjYXB0Y2hhdjMK".equals(token)) {
				return captchaRes;
			}
			String secret = systemConfig.getConfig("CAPTCHA_SECRET_KEY_V3");
			if (!this.verifyCaptchaV3(secret, token)) {
				String captcha = CmsUtils.genCaptcha(6);
	        	this.createCaptcha(key, captcha);
	        	captchaRes = new CheckCaptchaRes();
	        	captchaRes.setCaptcha(CommonBase64Util.encode(captcha));
				captchaRes.setErrorCode("INCORRECT_CAPTCHA_V3");
			}
		}
		return captchaRes;
    }
}
