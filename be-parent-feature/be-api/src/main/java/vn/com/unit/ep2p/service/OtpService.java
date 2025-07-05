package vn.com.unit.ep2p.service;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
public interface OtpService {

    public String generateOTP(String key);
    
    public String getOtp(String key);
    
    public void clearOTP(String key);
    
    public String checkKey(String otp);
    
    public void setIncorrectOtp(String key, String count);
    
    public int getIncorrectOtp(String key);
}
