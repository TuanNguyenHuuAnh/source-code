/*******************************************************************************
 * Class        ：NoLogging
 * Created date ：2020/06/04
 * Lasted date  ：2020/06/04
 * Author       ：KhoaNA
 * Change log   ：2020/06/04：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NoLogging
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogging {
}