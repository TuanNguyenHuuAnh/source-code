/*******************************************************************************
 * Class        ：ContainArrayImpl
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.annotation.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.core.annotation.ContainArray;

/**
 * ContainArrayImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class ContainArrayImpl implements ConstraintValidator<ContainArray, Object> {

    List<Object> list;

    @Override
    public void initialize(ContainArray constraintAnnotation) {
        if(null!=constraintAnnotation.strArray()) {
            list = Arrays.stream(constraintAnnotation.strArray()).collect(Collectors.toList());
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(null!= value && CommonStringUtil.isNotBlank(value.toString())) {
            return list.contains(value);
        }
        return true;
    }

}
