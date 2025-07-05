/*******************************************************************************
 * Class        ：AlertTypeEnum
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：TrieuVD
 * Change log   ：2020/11/18：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * AlertTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum AlertTypeEnum {

    NOTIFICATION(1),
    REMINDER(2),
    ESCALATE(3),
    TRANSFER(4);

    private int value;

    private AlertTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private static final Map<Integer, AlertTypeEnum> mappings = new HashMap<>(AlertTypeEnum.values().length);

    static {
        for (AlertTypeEnum alertType : values()) {
            mappings.put(alertType.getValue(), alertType);
        }
    }

    public static AlertTypeEnum resolveByValue(int value) {
        return (mappings.get(value) != null ? mappings.get(value) : null);
    }
}
