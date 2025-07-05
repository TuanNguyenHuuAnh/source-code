/*******************************************************************************
 * Class        UserProfileUtils
 * Created date 2017/02/15
 * Lasted date  2017/02/15
 * Author       KhoaNA
 * Change log   2017/02/1501-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.com.unit.common.constant.CommonConstant;

/**
 * UserPrincipalUtils.
 *
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class UserProfileUtils {

    /**
     * Get Authentication.
     *
     * @return Authentication
     * @author KhoaNA
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Get UserPrincipal.
     *
     * @return UserPrincipal
     * @author KhoaNA
     */
    public static UserPrincipal getUserPrincipal() {
        UserPrincipal userPrincipal = null;

        if (getAuthentication() != null && getAuthentication().getPrincipal() instanceof UserPrincipal) {
            userPrincipal = (UserPrincipal) getAuthentication().getPrincipal();
        }
        return userPrincipal;
    }

    /**
     * getListItem.
     *
     * @return {@link List<String>}
     * @author vinhlt
     */
    public static List<String> getListItem() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    /**
     * valid role of account login.
     *
     * @param role
     *            type {@link String}
     * @return boolean
     * @author KhoaNA
     */
    public static boolean hasRole(String role) {
        boolean hasRole = false;
        UserPrincipal userPrincipal = getUserPrincipal();
        if (userPrincipal != null) {
            hasRole = userPrincipal.hasPermission(role);
        }
        return hasRole;
    }

    /**
     * Get UserName login.
     *
     * @return String
     * @author KhoaNA
     */
    public static String getUserNameLogin() {
        return getUserPrincipal() != null ? getUserPrincipal().getUsername() : null;
    }

    /**
     * <p>
     * Get full name.
     * </p>
     *
     * @return {@link String}
     * @author taitt
     */
    public static String getFullName() {
        return getUserPrincipal() != null ? getUserPrincipal().getFullname() : null;
    }
    
    /**
     * getCompanyId.
     *
     * @return {@link Long}
     * @author HungHT
     */
    public static Long getCompanyId() {
        return getUserPrincipal() != null ? getUserPrincipal().getCompanyId() : null;
    }

    /**
     * Get account id.
     *
     * @return String
     * @author KhoaNA
     */
    public static Long getAccountId() {
        return getUserPrincipal() != null ? getUserPrincipal().getAccountId() : null;
    }
    
    /**
     * getActUserId.
     *
     * @return String
     * @author KhoaNA
     */
    public static String getActUserId() {
        return getAccountId().toString();
    }
    
    /**
     * Find only list function code.
     *
     * @return listRole : List<String>
     * @author trieunh <trieunh@unit.com.vn>
     */
    public static List<String> findOnlyFunctionCode() {
        List<String> listFunctionCode = new ArrayList<String>();
        if (getAuthentication() != null && null != getAuthentication().getAuthorities()) {
            @SuppressWarnings("unchecked")
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) getAuthentication().getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority() != null && !"".equals(grantedAuthority.getAuthority())
                        && !"ROLE_AUTHED".equals(grantedAuthority.getAuthority())) {
                    String[] splitList = grantedAuthority.getAuthority().split(":");
                    if (splitList.length > 0) {
                        // System.out.println("FUNCTION CODE : " +
                        // splitList[0]);
                        listFunctionCode.add(splitList[0]);
                    }
                }
            }
        }
        return listFunctionCode;
    }
    
    /**
     * isCompanyAdmin.
     *
     * @return true, if is company admin
     * @author HungHT
     */
    public static boolean isCompanyAdmin() {
        return getUserPrincipal() != null ? getUserPrincipal().isCompanyAdmin() : null;
    }
    
    /**
     * getCompanyIdList.
     *
     * @return {@link List<Long>}
     * @author HungHT
     */
    public static List<Long> getCompanyIdList() {
        return getUserPrincipal() != null ? getUserPrincipal().getCompanyIdList() : null;
    }
    
    /**
     * <p>
     * Get locale request.
     * </p>
     *
     * @return {@link Locale}
     * @author taitt
     */
    public static Locale getLocaleRequest() {
        return getUserPrincipal() != null ? getUserPrincipal().getLocale() : null;
    }
    
    /**
     * <p>
     * Get language.
     * </p>
     *
     * @return {@link String}
     * @author taitt
     */
    public static String getLanguage() {
        if(getUserPrincipal() != null && getUserPrincipal().getLocale() != null) {
            return getUserPrincipal().getLocale().getLanguage();
        }
        return CommonConstant.JWT_LANGUAGE_DEFAULT;
//        return getUserPrincipal() != null ? (null != getUserPrincipal().getLocale() ? getUserPrincipal().getLocale().getLanguage() : CommonConstant.JWT_LANGUAGE_DEFAULT)  : null;
    }
    
    
    // LocLT fix tạm
    public static String getCostCenterName() {
        return "";
    }

    public static String getCostCenterCode() {
        return "";
    }

    public static String getDepartmentCode() {
        return "";
    }

    public static Long getDepartmentId() {
        return 0L;
    }

    public static Long getBranchId() {
        return 0L;
    }

    public static String getFaceMask() {
        if(getUserPrincipal() != null){
            return StringUtils.isNotBlank(getUserPrincipal().getFaceMask())
                    ? getUserPrincipal().getFaceMask()
                    : getUserPrincipal().getUsername();
        }
        return null;
    }
    
    public static String getApiToken() {
        return getUserPrincipal() != null ? getUserPrincipal().getApiToken() : null;
    }
    public static String getDeviceId() { return getUserPrincipal() != null ? getUserPrincipal().getDeviceId() : null; }
    public static String getDeviceToken() { return getUserPrincipal() != null ? getUserPrincipal().getDeviceToken() : null; }
    public static String getChannel() {
    	return getUserPrincipal() != null ? getUserPrincipal().getChannel() : null;
    }
}