/*******************************************************************************
 * Class        ：DtsCollectionUtil
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：taitt
 * Change log   ：2020/11/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

/**
 * DtsCollectionUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class DtsCollectionUtil {

    @SuppressWarnings("rawtypes")
    public static final Collection EMPTY_COLLECTION = CollectionUtils.EMPTY_COLLECTION;


    /**
     * 
     * isNotEmpty
     * @param collection
     * @return
     * @author taitt
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtils.isNotEmpty(collection);
    }


    /**
     * 
     * isEmpty
     * @param collection
     * @return
     * @author taitt
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }


    /**
     * 
     * size
     * @param collection
     * @return
     * @author taitt
     */
    public static int size(Collection<?> collection) {
        return CollectionUtils.size(collection);
    }
}
