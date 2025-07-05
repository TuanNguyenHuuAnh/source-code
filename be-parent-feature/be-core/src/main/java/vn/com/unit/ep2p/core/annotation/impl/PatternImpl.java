/********************************************************************************
* Class        : PatternImpl
* Created date : 2021/01/27
* Lasted date  : 2021/01/27
* Author       : TaiTT
******************************************************************************/

package vn.com.unit.ep2p.core.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.core.annotation.Pattern;

/**
 * <p>
 * PatternImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class PatternImpl implements ConstraintValidator<Pattern, String>{

    /** The regex. */
    private String regex;
    
    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(Pattern constraintAnnotation) {
        regex = constraintAnnotation.regex();
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(CommonStringUtil.isNotBlank(value) && CommonStringUtil.isNotBlank(regex)) {
            return value.matches(regex);
        }
        return true;
    }

}
