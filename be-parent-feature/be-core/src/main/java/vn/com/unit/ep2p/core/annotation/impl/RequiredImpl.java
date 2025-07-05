/********************************************************************************
* Class        : RequiredImpl
* Created date : 2021/01/27
* Lasted date  : 2021/01/27
* Author       : TaiTT
******************************************************************************/

package vn.com.unit.ep2p.core.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.core.annotation.Required;

/**
 * <p>
 * RequiredImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class RequiredImpl implements ConstraintValidator<Required, String> {

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(Required constraintAnnotation) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CommonStringUtil.isNotBlank(value) ? true : false;
    }

}
