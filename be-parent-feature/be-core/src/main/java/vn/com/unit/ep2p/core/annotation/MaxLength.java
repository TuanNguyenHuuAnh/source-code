/*******************************************************************************
 * Class        ：MaxLength
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import vn.com.unit.ep2p.core.annotation.impl.MaxLengthImpl;
import vn.com.unit.ep2p.core.constant.AppCoreExceptionCodeConstant;

/**
 * MaxLength
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = MaxLengthImpl.class)
public @interface MaxLength {
    int length() default 30;
    String message() default AppCoreExceptionCodeConstant.E4024806_APPAPI_ANNOTATION_MAXLENGTH_ARRAY;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
