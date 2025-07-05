/*******************************************************************************
 * Class        ：MaxLengthImpl
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.core.annotation.MaxLength;

/**
 * MaxLengthImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class MaxLengthImpl implements ConstraintValidator<MaxLength, String>{
    private int length;

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CommonStringUtil.isNotBlank(value) && value.length()>length ? Boolean.FALSE : Boolean.TRUE;
    }
}
