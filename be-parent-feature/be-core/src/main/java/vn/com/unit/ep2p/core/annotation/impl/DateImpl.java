/********************************************************************************
* Class        : DateImpl
* Created date : 2021/01/27
* Lasted date  : 2021/01/27
* Author       : TaiTT
******************************************************************************/

package vn.com.unit.ep2p.core.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.core.annotation.Date;

/**
 * <p>
 * DateImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class DateImpl implements ConstraintValidator<Date, String> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(DateImpl.class);

    /** The format. */
    private String format;

    /*
     * (non-Javadoc)
     * 
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(Date constraintAnnotation) {
        format = constraintAnnotation.format();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (CommonStringUtil.isNotBlank(value)) {
                CommonDateUtil.formatStringToDate(value, format);
            }
            return true;
        } catch (Exception e) {
            logger.error("[validate Date] value: " + value + ", format: " + format);
            return false;
        }
    }

}
