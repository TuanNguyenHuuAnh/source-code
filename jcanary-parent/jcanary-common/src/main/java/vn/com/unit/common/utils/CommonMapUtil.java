/*******************************************************************************
 * Class        ：CommonMapUtil
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：taitt
 * Change log   ：2020/11/10：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;

/**
 * CommonMapUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class CommonMapUtil {

    /**
     * 
     * isEmpty
     * @param map
     * @return
     * @author taitt
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }
    
    /**
     * 
     * isNotEmpty
     * @param map
     * @return
     * @author taitt
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return MapUtils.isNotEmpty(map);
    }
}
