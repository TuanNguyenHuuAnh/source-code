/*******************************************************************************
 * Class        ：FcmJsonUtils
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：TrieuVD
 * Change log   ：2021/01/20：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.fcm.utils;

import vn.com.unit.common.utils.CommonJsonUtil;

/**
 * FcmJsonUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class FcmJsonUtils extends CommonJsonUtil {

    /**
     * <p>
     * Fcm convert object to json.
     * </p>
     *
     * @param obj
     *            type {@link Object}
     * @return {@link String}
     * @author TrieuVD
     */
    public static String fcmConvertObjectToJson(Object obj) {
        String resultJson = null;
        try {
            resultJson = convertObjectToJSON(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }
}
