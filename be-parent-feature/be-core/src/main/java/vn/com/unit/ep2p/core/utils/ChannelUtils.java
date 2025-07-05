/**
 * @author TaiTM
 */
package vn.com.unit.ep2p.core.utils;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.constant.ParamConstant;

/**
 * @author TaiTM
 *
 */
public class ChannelUtils {
    public static String getUserChannel() {
        String channel = "";

        if (UserProfileUtils.hasRole(ParamConstant.ROLE_CHANNEL_BANCAS)) {
            channel = ParamConstant.BANCAS;
        } else if (UserProfileUtils.hasRole(ParamConstant.ROLE_CHANNEL_AGENCY)) {
            channel = ParamConstant.AGENCY;
        }

        return channel;
    }
}
