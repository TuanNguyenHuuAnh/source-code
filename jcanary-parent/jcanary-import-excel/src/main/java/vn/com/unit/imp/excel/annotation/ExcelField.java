/*******************************************************************************
 * Class        ExcelField
 * Created date 2019/02/15
 * Lasted date  2019/02/15
 * Author       VinhLT
 * Change log   2019/02/1501-00 VinhLT create a new
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
 * @author VinhLT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
public @interface ExcelField {
	/**
	 * The column number which is mapped to the annotated property.
	 */
	int colNum();
}
