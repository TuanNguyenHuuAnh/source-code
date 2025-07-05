/*******************************************************************************
 * Class        ：SlaCollectionUtils
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：TrieuVD
 * Change log   ：2021/01/20：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import vn.com.unit.common.utils.CommonCollectionUtil;

/**
 * SlaCollectionUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class SlaCollectionUtils extends CommonCollectionUtil{
    
    /**
     * <p>
     * Distinct by key.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param keyExtractor
     *            type {@link Function<? super T,?>}
     * @return {@link Predicate<T>}
     * @author TrieuVD
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
