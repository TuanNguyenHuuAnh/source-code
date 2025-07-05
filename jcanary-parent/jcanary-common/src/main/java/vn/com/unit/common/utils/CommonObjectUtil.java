/*******************************************************************************
 * Class        ：CommonObjectUtil
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：TrieuVD
 * Change log   ：2020/12/16：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import jp.sf.amateras.mirage.annotation.Column;

/**
 * <p>CommonObjectUtil</p>.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public class CommonObjectUtil {
    
    /** The column default. */
    private static String COLUMN_DEFAULT = "ID";

    /**
     * <p>Instantiates a new common object util.</p>
     *
     * @author Tan Tai
     */
    private CommonObjectUtil() {

    }

    /**
     * <p>Get column list from entity.</p>
     *
     * @author Tan Tai
     * @param entityClass type {@link Class<?>}
     * @return {@link List<String>}
     */
    public static List<String> getColumnListFromEntity(Class<?> entityClass) {
        List<String> result = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields(); // private fields
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                result.add(column.name());
            }
        }
        return result;
    }
    
    /**
     * <p>Get column list from entity.</p>
     *
     * @author Tan Tai
     * @param entityClass type {@link Class<?>}
     * @param name type {@link String}
     * @return {@link String}
     * @throws Exception the exception
     */
    public static String getColumnListFromEntity(Class<?> entityClass, String name) throws Exception{
        String result = COLUMN_DEFAULT;
        Field field;
        Class<?> current = entityClass;
        while(current.getSuperclass()!=null){ // we don't want to process Object.class
            // do something with current's fields
            try {
                field = current.getDeclaredField(name);
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    result = column.name();
                	current = current.getSuperclass();
                }
            } catch (NoSuchFieldException e) {
            	current = current.getSuperclass();
                continue;
            }
        }
        return result;
    }

    /**
     * <p>Copy properties non null.</p>
     *
     * @author Tan Tai
     * @param src type {@link Object}
     * @param target type {@link Object}
     */
    public static void copyPropertiesNonNull(Object src, Object target) {
        try {
            BeanUtils.copyProperties(src, target, getArrNullPropertyName(src));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Get arr null property name.</p>
     *
     * @author Tan Tai
     * @param source type {@link Object}
     * @return {@link String[]}
     * @throws Exception the exception
     */
    private static String[] getArrNullPropertyName(Object source) throws Exception {
        Set<String> emptyNames = new HashSet<>();
        String[] result;
        try {
            final BeanWrapper src = new BeanWrapperImpl(source);
            java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

            for (java.beans.PropertyDescriptor pd : pds) {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null)
                    emptyNames.add(pd.getName());
            }
            result = new String[emptyNames.size()];
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return emptyNames.toArray(result);
    }
}
