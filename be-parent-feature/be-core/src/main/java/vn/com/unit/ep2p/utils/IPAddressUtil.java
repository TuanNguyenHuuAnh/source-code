/*******************************************************************************
 * Class        IPAddressUtil
 * Created date 2018/01/08
 * Lasted date  2018/01/08
 * Author       HUNGHT
 * Change log   2018/01/0801-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * IPAddressUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
public class IPAddressUtil {

    /** IP_HEADER_CANDIDATES */
    private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

    /** 
     * getClientIpAddress
     *
     * @param request
     * @return
     * @author HUNGHT
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = "";
        for (String header : IP_HEADER_CANDIDATES) {
            ipAddress = request.getHeader(header);
            if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                return ipAddress;
            }
        }
        return request.getRemoteAddr();
    }
}
