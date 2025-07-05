/*******************************************************************************
 * Class        ：MapWithEnumImpl
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.annotation.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.com.unit.ep2p.core.annotation.MapWithEnum;

/**
 * MapWithEnumImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class MapWithEnumImpl implements ConstraintValidator<MapWithEnum, String> {
    private List<String> acceptedValues;

    /**
     * @author KhuongTH
     */
    @Override
    public void initialize(MapWithEnum constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    /**
     * @author KhuongTH
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        return acceptedValues.contains(value);
    }

}
