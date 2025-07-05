/*******************************************************************************
 * Class        ExcelField
 * Created date 2019/02/15
 * Lasted date  2019/02/15
 * Author       TaiTM
 * Change log   2019/02/1501-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ExcelField
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ExcelImportTable {
    /**
     * The table name which is mapped to the annotated property.
     */
    String tableName();
}
