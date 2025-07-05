/*******************************************************************************
 * Class        ：SlaJsonUtils
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：TrieuVD
 * Change log   ：2021/01/20：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.utils;

import java.util.HashMap;
import java.util.Map;

import vn.com.unit.common.utils.CommonJsonUtil;

/**
 * SlaJsonUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class SlaJsonUtils extends CommonJsonUtil {

    /**
     * <p>
     * Sla convert object to json.
     * </p>
     *
     * @param obj
     *            type {@link Object}
     * @return {@link String}
     * @author TrieuVD
     */
    public static String slaConvertObjectToJson(Object obj) {
        String resultJson = null;
        try {
            resultJson = convertObjectToJSON(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }
    
    /**
     * <p>
     * Sla convert JSON to map.
     * </p>
     *
     * @author TrieuVD
     * @param json
     *            type {@link String}
     * @return {@link Map<String,String>}
     */
    public static Map<String, String> slaConvertJSONToMap(String json) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap = convertJSONToMap(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
