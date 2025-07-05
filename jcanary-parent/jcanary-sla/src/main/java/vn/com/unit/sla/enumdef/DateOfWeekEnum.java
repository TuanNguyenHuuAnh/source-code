/*******************************************************************************
 * Class        ：DateOfWeekEnum
 * Created date ：2021/03/09
 * Lasted date  ：2021/03/09
 * Author       ：ngannh
 * Change log   ：2021/03/09：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * DateOfWeekEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public enum DateOfWeekEnum {
    SUNDAY(1),
    MONDAY(2),
    TUEDAY(3),
    WEDNESDAY(4),
    THURDAY(5),
    FRIDAY(6),
    SATURDAY(7);


    private Integer value;

    private DateOfWeekEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    private static final Map<Integer, DateOfWeekEnum> mappings = new HashMap<>(DateOfWeekEnum.values().length);

    static {
        for (DateOfWeekEnum alertStatus : values()) {
            mappings.put(alertStatus.getValue(), alertStatus);
        }
    }

    public static DateOfWeekEnum resolveByValue(Integer value) {
        return (mappings.get(value) != null ? mappings.get(value) : null);
    }
}
