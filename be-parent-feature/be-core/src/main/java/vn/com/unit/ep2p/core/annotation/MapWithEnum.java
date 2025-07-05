/*******************************************************************************
 * Class        ：MapWithEnum
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

import vn.com.unit.ep2p.core.annotation.impl.MapWithEnumImpl;
import vn.com.unit.ep2p.core.constant.AppCoreExceptionCodeConstant;

/**
 * MapWithEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = MapWithEnumImpl.class)
public @interface MapWithEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default AppCoreExceptionCodeConstant.E4024804_APPAPI_ANNOTATION_NOT_MATH_PATTERN;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
