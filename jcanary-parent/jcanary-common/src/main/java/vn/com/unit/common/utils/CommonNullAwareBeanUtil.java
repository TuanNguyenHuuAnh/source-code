package vn.com.unit.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by NghiaPV on 7/31/2018.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class CommonNullAwareBeanUtil {

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(CommonNullAwareBeanUtil.class);

    /**
     * <p>
     * Copy properties WO null.
     * </p>
     *
     * @param src
     *            type {@link Object}
     * @param target
     *            type {@link Object}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    public static void copyPropertiesWONull(Object src, Object target) throws Exception {
        try {
            BeanUtils.copyProperties(src, target, getArrNullPropertyName(src));
        } catch (Exception e) {
            logger.error("##copyPropertiesWONull##", e);
            throw e;
        }
    }

    /**
     * <p>
     * Get arr null property name.
     * </p>
     *
     * @param source
     *            type {@link Object}
     * @return {@link String[]}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    private static String[] getArrNullPropertyName (Object source) throws Exception {
        Set<String> emptyNames = new HashSet<String>();
        String[] result;
        try {
            final BeanWrapper src = new BeanWrapperImpl(source);
            java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

            for (java.beans.PropertyDescriptor pd : pds) {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null) emptyNames.add(pd.getName());
            }
            result = new String[emptyNames.size()];
        } catch (Exception e) {
            logger.error("##getNullPropertyNames##", e);
            throw e;
        }
        return emptyNames.toArray(result);
    }
}
