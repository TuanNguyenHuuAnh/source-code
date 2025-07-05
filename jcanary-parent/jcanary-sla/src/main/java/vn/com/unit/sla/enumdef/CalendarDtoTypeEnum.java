/*******************************************************************************
 * Class        ：DayOffTypeEnum
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：TrieuVD
 * Change log   ：2020/11/18：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * DayOffTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum CalendarDtoTypeEnum {

    NO_VALUE("0"),
    MORNING("1"),
    AFTERNOON("2"),
    FULL_DAY("3");

    private String value;

    private CalendarDtoTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    private static final Map<String, CalendarDtoTypeEnum> mappings = new HashMap<>(CalendarDtoTypeEnum.values().length);

    static {
        for (CalendarDtoTypeEnum dayOff : values()) {
            mappings.put(dayOff.getValue(), dayOff);
        }
    }

    public static CalendarDtoTypeEnum resolveByValue(String value) {
        return (mappings.get(value) != null ? mappings.get(value) : NO_VALUE);
    }
}
