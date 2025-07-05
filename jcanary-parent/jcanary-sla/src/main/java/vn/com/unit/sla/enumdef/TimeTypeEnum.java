/*******************************************************************************
 * Class        ：TimeTypeSlaEnum
 * Created date ：2020/11/17
 * Lasted date  ：2020/11/17
 * Author       ：TrieuVD
 * Change log   ：2020/11/17：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * TimeTypeSlaEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum TimeTypeEnum {
    
    NO_VALUE(0),
    WORKING_MINUTE(1),
    CALENDAR_MINUTE(2),
    WORKING_HOUR(3),
    CALENDAR_HOUR(4),
    WORKING_DAY(5),
    CALENDAR_DAY(6);

    private int value;
    
    private TimeTypeEnum(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    private static final Map<Integer, TimeTypeEnum> mappings = new HashMap<>(TimeTypeEnum.values().length);

    static {
        for (TimeTypeEnum timeType : values()) {
            mappings.put(timeType.getValue(), timeType);
        }
    }

    public static TimeTypeEnum resolveByValue(int value) {
        return (mappings.get(value) != null ? mappings.get(value) : NO_VALUE);
    }
}
