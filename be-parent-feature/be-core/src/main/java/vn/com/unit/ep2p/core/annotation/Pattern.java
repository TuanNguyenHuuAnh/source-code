/********************************************************************************
* Class        : Pattern
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

import vn.com.unit.ep2p.core.annotation.impl.PatternImpl;
import vn.com.unit.ep2p.core.constant.AppCoreExceptionCodeConstant;

/**
 * Pattern default will check number.
 *
 * @version 01-00
 * @since 01-00
 * @author TaiTT
 */
@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = PatternImpl.class)
public @interface Pattern {
    
    /**
     * <p>
     * Message.
     * </p>
     *
     * @return {@link String}
     * @author taitt
     */
    String message() default AppCoreExceptionCodeConstant.E4024804_APPAPI_ANNOTATION_NOT_MATH_PATTERN;
    
    /**
     * <p>
     * Regex.
     * </p>
     *
     * @return {@link String}
     * @author taitt
     */
    String regex() default "^(0|[1-9][0-9]*)$";
    
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
