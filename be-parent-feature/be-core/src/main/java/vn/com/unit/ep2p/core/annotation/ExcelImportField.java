// 2021-04-06 LocLT Task #40894

package vn.com.unit.ep2p.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import vn.com.unit.ep2p.core.enumdef.DataTypeEnum;;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ExcelImportField {
    /**
     * The column name which is mapped to the annotated property.
     */
    String colName();

    /**
     * The column type which is mapped to the annotated property.
     */
    DataTypeEnum colType();
}
