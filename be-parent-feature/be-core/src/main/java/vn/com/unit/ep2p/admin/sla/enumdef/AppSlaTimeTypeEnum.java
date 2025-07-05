/*******************************************************************************
 * Class        ：AppSlaTimeTypeEnum
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
 * AppSlaTimeTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum AppSlaTimeTypeEnum {
    
    WORKING_MINUTE("1", "sla.time.type.working.minute"),
    CALENDAR_MINUTE("2", "sla.time.type.calendar.minute"),
    WORKING_HOUR("3", "sla.time.type.working.hour"),
    CALENDAR_HOUR("4", "sla.time.type.calendar.hour"),
    WORKING_DAY("5", "sla.time.type.working.day"),
    CALENDAR_DAY("6", "sla.time.type.calendar.day");

    private String value;
    private String text;
    
    private AppSlaTimeTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getText() {
        return this.text;
    }
    
    private static final Map<String, AppSlaTimeTypeEnum> mappings = new HashMap<>(AppSlaTimeTypeEnum.values().length);

    static {
        for (AppSlaTimeTypeEnum timeType : values()) {
            mappings.put(timeType.getValue(), timeType);
        }
    }

    public static AppSlaTimeTypeEnum resolveByValue(String value) {
        return (mappings.get(value) != null ? mappings.get(value) : null);
    }
    
    public static List<Select2Dto> getSelect2DtoList() {
        List<Select2Dto> res = new ArrayList<>();
         for(AppSlaTimeTypeEnum value : values()) {
            Select2Dto select2Dto = new Select2Dto(value.getValue(), value.getText(), value.getText());
            res.add(select2Dto);
        }
        return res;
    }
}
