/*******************************************************************************
 * Class        ：RuleType
 * Created date ：2021/03/11
 * Lasted date  ：2021/03/11
 * Author       ：tantm
 * Change log   ：2021/03/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

/**
 * RuleType
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public enum RuleTypeEnum {

    GREATER_THAN(1, "rule.type.greater.than"), 
    EQUAL(2, "rule.type.equal"), 
    LESS_THAN(3, "rule.type.less.than");

    private int value;
    private String code;

    RuleTypeEnum(int value, String code) {
        this.value = value;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static List<Select2Dto> getSelect2Dtos() {
        List<Select2Dto> res = new ArrayList<>();
        for (RuleTypeEnum value : values()) {
            Select2Dto select2Dto = new Select2Dto(value.toString(), value.getCode(), value.getCode());
            res.add(select2Dto);
        }
        return res;
    }
}
