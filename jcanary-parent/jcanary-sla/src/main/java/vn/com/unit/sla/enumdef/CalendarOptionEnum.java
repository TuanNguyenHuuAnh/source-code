/*******************************************************************************
 * Class        ：CalendarOptionEnum
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：TrieuVD
 * Change log   ：2020/11/18：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * CalendarOptionEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum CalendarOptionEnum {

    CALENDAR("CALENDAR"),
    USER("USER"),
    DEPARTMENT("DEPARTMENT");

    private String value;

    private CalendarOptionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    private static final Map<String, CalendarOptionEnum> mappings = new HashMap<>(CalendarOptionEnum.values().length);

    static {
        for (CalendarOptionEnum type : values()) {
            mappings.put(type.getValue(), type);
        }
    }

    public static CalendarOptionEnum resolveByValue(String value) {
        return (mappings.get(value) != null ? mappings.get(value) : null);
    }
}
