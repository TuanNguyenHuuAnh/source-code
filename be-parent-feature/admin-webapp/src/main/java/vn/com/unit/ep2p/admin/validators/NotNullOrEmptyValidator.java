/**
 * 
 */
package vn.com.unit.ep2p.admin.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author longpnt
 *
 */
public class NotNullOrEmptyValidator implements ConstraintValidator<NotNullOrEmpty, String> {

    @Override
    public void initialize(NotNullOrEmpty constraintAnnotation) {
        //Empty
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !(value == null || value.isEmpty());
    }

}
