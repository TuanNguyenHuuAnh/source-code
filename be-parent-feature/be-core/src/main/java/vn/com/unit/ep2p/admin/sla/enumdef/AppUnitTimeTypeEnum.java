/*******************************************************************************
 * Class        ：UnitTimeTypeSlaEnum
 * Created date ：2020/11/17
 * Lasted date  ：2020/11/17
 * Author       ：TrieuVD
 * Change log   ：2020/11/17：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.enumdef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.com.unit.common.dto.Select2Dto;

/**
 * UnitTimeTypeSlaEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum AppUnitTimeTypeEnum {

    MINUTE("1", "unit.time.type.minute"),
    HOUR("2", "unit.time.type.hour"),
    DAY("3", "unit.time.type.day");

    private String value;
    private String text;

    private AppUnitTimeTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
    
    private static final Map<String, AppUnitTimeTypeEnum> mappings = new HashMap<>(AppUnitTimeTypeEnum.values().length);

    static {
        for (AppUnitTimeTypeEnum slaUnitTime : values()) {
            mappings.put(slaUnitTime.getValue(), slaUnitTime);
        }
    }

    public static AppUnitTimeTypeEnum resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }
    
    public static List<Select2Dto> getSelect2DtoList() {
        List<Select2Dto> res = new ArrayList<>();
         for(AppUnitTimeTypeEnum value : values()) {
            Select2Dto select2Dto = new Select2Dto(value.getValue(), value.getText(), value.getText());
            res.add(select2Dto);
        }
        return res;
    }

}
