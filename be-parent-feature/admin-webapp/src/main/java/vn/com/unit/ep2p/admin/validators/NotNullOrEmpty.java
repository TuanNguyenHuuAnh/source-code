/**
 * 
 */
package vn.com.unit.ep2p.admin.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author longpnt
 *
 */
@Documented
@Constraint(validatedBy = { NotNullOrEmptyValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullOrEmpty {

	String message() default "{javax.validation.constraints.NotNull.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
