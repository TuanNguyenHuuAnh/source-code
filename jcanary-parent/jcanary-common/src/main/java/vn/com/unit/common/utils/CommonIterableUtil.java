/*******************************************************************************
 * Class        ：CommonIterableUtil
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：KhuongTH
 * Change log   ：2021/01/13：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import org.apache.commons.collections4.IterableUtils;

/**
 * <p>
 * CommonIterableUtil
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class CommonIterableUtil extends IterableUtils {

    /**
     * Answers true if the provided iterable is not empty.
     * <p>
     * A <code>null</code> iterable returns false.
     *
     * @param iterable  the {@link Iterable to use}, may be null
     * @return false if the iterable is null or empty, true otherwise
     */
    public static boolean isNotEmpty(final Iterable<?> iterable) {
        return !isEmpty(iterable);
    }
}
