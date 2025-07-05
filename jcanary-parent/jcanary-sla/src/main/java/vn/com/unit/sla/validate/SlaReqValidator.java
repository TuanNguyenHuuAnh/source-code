/*******************************************************************************
 * Class        ：SlaReqValidator
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：TrieuVD
 * Change log   ：2020/11/12：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import vn.com.unit.dts.exception.DetailException;

/**
 * SlaReqValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class SlaReqValidator {
    /**
     * validate
     * @param obj class
     * @param obj class
     * @throws Exception error
     * @author TrieuVD
     */
    public static <T> void validate(T obj) throws Exception {
        String errorCode = null;
        //validate
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        for (ConstraintViolation<T> violation : violations) {
            String[] args = new String[1];
            String fileError = violation.getPropertyPath().toString();
            args[0] = fileError;
            errorCode = violation.getMessage();
            throw new DetailException(errorCode, args, true);
        }
    }
}
