package vn.com.unit.ep2p.service;

import vn.com.unit.ep2p.dto.res.CheckCaptchaRes;

public interface ReCaptchaService {

    public void createCaptcha(String key, String value);

    public String getCaptcha(String key);

    public boolean checkCaptcha(String key, String value);
    
    public boolean verifyCaptchaV3(String secret, String token);
    
    public CheckCaptchaRes verifyCaptcha(String key, String token);
}
