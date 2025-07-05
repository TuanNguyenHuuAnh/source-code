/*******************************************************************************
 * Class        JCanaryPasswordUtil
 * Created date 2018/01/10
 * Lasted date  2018/01/10
 * Author       CongDT
 * Change log   2018/01/1001-00 CongDT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.utils;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.unit.common.crypto.AesUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.utils.PasswordUtil;

/**
 * JCanaryPasswordUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author CongDT
 */

@Component
public class JCanaryPasswordUtil extends PasswordUtil{

    @Autowired
    private SystemConfig systemConfig;
    
    /** 
     * isStrongPassword
     *
     * @param password
     * @return
     * @author HUNGHT
     */
    public boolean isStrongPassword(String password) {
        /*
         * ^                The password string will start this way
         * (?=.*[a-z])      The string must contain at least 1 lower case alphabetical character
         * (?=.*[A-Z])      The string must contain at least 1 upper case alphabetical character
         * (?=.*[0-9])      The string must contain at least 1 numeric character
         * (?=.[!@#$%^&*])  The string must contain at least one special character
         * .{7,}            The string must be seven characters or long
        */
        
        String userName = UserProfileUtils.getUserNameLogin();
        String fullName = UserProfileUtils.getUserPrincipal().getFullname();
        if(password.contains(userName) || password.contains(fullName)){
            return false;
        }
        String lowerCase = "(?=.*[a-z])";
        String upperCase = "(?=.*[A-Z])";
        String numberic = "(?=.*[0-9])";
        String specialCharacter = "(?=.*[!@#$%^&*])";
        Integer sysLowerCase = Integer.parseInt(
                systemConfig.getConfig(SystemConfig.FLAG_LOWER_CASE) == null ? "0" : systemConfig.getConfig(SystemConfig.FLAG_LOWER_CASE));
        Integer sysUpperCase = Integer.parseInt(
                systemConfig.getConfig(SystemConfig.FLAG_UPPER_CASE) == null ? "0" : systemConfig.getConfig(SystemConfig.FLAG_UPPER_CASE));
        Integer sysNumbericCase = Integer.parseInt(systemConfig.getConfig(SystemConfig.FLAG_NUMBER_CASE) == null ? "0"
                : systemConfig.getConfig(SystemConfig.FLAG_NUMBER_CASE));
        Integer sysSpecialCase = Integer.parseInt(systemConfig.getConfig(SystemConfig.FLAG_SPECIAL_CASE) == null ? "0"
                : systemConfig.getConfig(SystemConfig.FLAG_SPECIAL_CASE));

        Integer sysLengthCase = Integer.parseInt(systemConfig.getConfig(SystemConfig.MIN_PASSWORD_LENGTH) != null
                ? systemConfig.getConfig(SystemConfig.MIN_PASSWORD_LENGTH) : "6"); // default 6
        
        if(sysLowerCase != 1){
            lowerCase = "";
        }
        
        if(sysUpperCase != 1){
            upperCase = "";
        }
        
        if(sysNumbericCase != 1){
            numberic = "";
        }
        
        if(sysSpecialCase != 1){
            specialCharacter = "";
        }
        
        return password.matches("^"+lowerCase+upperCase+numberic+specialCharacter+".{"+sysLengthCase+",}");

    }

}
