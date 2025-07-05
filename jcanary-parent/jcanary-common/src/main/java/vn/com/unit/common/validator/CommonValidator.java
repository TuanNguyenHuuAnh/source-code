/*******************************************************************************
 * Class        ：CommonValidator
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import vn.com.unit.dts.exception.DetailException;

/**
 * CommonValidator.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class CommonValidator {

    /**
     * <p>
     * Validate.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param obj
     *            type {@link T}
     * @return {@link String}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    public static <T> void validate(T obj) throws DetailException {
        // validate
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        for (ConstraintViolation<T> violation : violations) {
            String[] args = new String[1];
            String fileError = violation.getPropertyPath().toString();
            args[0] = fileError;
            String errorCode = violation.getMessage();
            throw new DetailException(errorCode, args);
        }
    }
}
