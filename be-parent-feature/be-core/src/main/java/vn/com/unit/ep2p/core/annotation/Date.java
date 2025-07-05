/********************************************************************************
* Class        : Date
* Created date : 2021/01/27
* Lasted date  : 2021/01/27
* Author       : TaiTT
******************************************************************************/

package vn.com.unit.ep2p.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import vn.com.unit.ep2p.core.annotation.impl.DateImpl;
import vn.com.unit.ep2p.core.constant.AppCoreExceptionCodeConstant;



/**
 * <p>
 * Date
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = DateImpl.class)
public @interface Date {
    
    /**
     * <p>
     * Format.
     * </p>
     *
     * @return {@link String}
     * @author taitt
     */
    String format() default "yyyyMMdd";
    
    /**
     * <p>
     * Message.
     * </p>
     *
     * @return {@link String}
     * @author taitt
     */
    String message() default AppCoreExceptionCodeConstant.E4024801_APPAPI_ANNOTATION_FORMAT_DATE;
    
    /**
     * <p>
     * Groups.
     * </p>
     *
     * @return {@link Class<?>[]}
     * @author taitt
     */
    Class<?>[] groups() default {};
    
    /**
     * <p>
     * Payload.
     * </p>
     *
     * @return the class<? extends payload>[]
     * @author taitt
     */
    Class<? extends Payload>[] payload() default {};
}
