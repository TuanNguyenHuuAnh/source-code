/*******************************************************************************
 * Class        ：CoreReadOnlyTx
 * Created date ：2020/04/16
 * Lasted date  ：2020/04/16
 * Author       ：TaiTM
 * Change log   ：2020/04/16：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

/**
 * CoreReadOnlyTx
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = true)
public @interface CoreReadOnlyTx {

}
