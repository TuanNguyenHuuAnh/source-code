/*******************************************************************************
 * Class        ：CollectionUtil
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：KhoaNA
 * Change log   ：2020/11/08：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.util.Collection;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

/**
 * CollectionUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class CommonCollectionUtil extends CollectionUtils {
    
    /**
     * Return {@code true} if the supplied Collection not {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param collection the Collection to check
     * @return whether the given Collection is not empty
     */
    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }
}
