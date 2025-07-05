/*******************************************************************************
 * Class        ：AlertStatusEnum
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：TrieuVD
 * Change log   ：2020/11/18：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * AlertStatusEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum AlertStatusEnum {

    WAITING(0),
    IN_PROGRESS(1),
    SUCCESS(2),
    FAIL(3),
    ERROR(4);

    private Integer value;

    private AlertStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    private static final Map<Integer, AlertStatusEnum> mappings = new HashMap<>(AlertStatusEnum.values().length);

    static {
        for (AlertStatusEnum alertStatus : values()) {
            mappings.put(alertStatus.getValue(), alertStatus);
        }
    }

    public static AlertStatusEnum resolveByValue(Integer value) {
        return (mappings.get(value) != null ? mappings.get(value) : null);
    }
}
