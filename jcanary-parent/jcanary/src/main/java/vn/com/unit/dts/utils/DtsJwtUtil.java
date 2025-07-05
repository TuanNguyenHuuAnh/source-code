/*******************************************************************************
 * Class        ：DtsJwtUtil
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：taitt
 * Change log   ：2020/11/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.utils;

import javax.servlet.http.HttpServletRequest;

import vn.com.unit.dts.constant.DtsConstant;

/**
 * DtsJwtUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class DtsJwtUtil {

    /**
     * <p>Get jwt from request.</p>
     * @param request
     *          type HttpServletRequest
     * @return String
     * @author KhoaNA
     */
    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(DtsConstant.JWT_AUTHORIZATION);
        if (DtsStringUtil.isNotEmpty(bearerToken) && bearerToken.startsWith(DtsConstant.JWT_BEARER)) {
            return bearerToken.substring(DtsConstant.JWT_BEARER.length() + 1);
        }
        return DtsStringUtil.EMPTY;
    }
    
    
}
