/*******************************************************************************
 * Class        ：UnitTimeTypeSlaEnum
 * Created date ：2020/11/17
 * Lasted date  ：2020/11/17
 * Author       ：TrieuVD
 * Change log   ：2020/11/17：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * UnitTimeTypeSlaEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum UnitTimeTypeSlaEnum {

    MINUTE(1),
    HOUR(2),
    DAY(3);

    private int value;

    private UnitTimeTypeSlaEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static final Map<Integer, UnitTimeTypeSlaEnum> mappings = new HashMap<>(UnitTimeTypeSlaEnum.values().length);

    static {
        for (UnitTimeTypeSlaEnum slaUnitTime : values()) {
            mappings.put(slaUnitTime.getValue(), slaUnitTime);
        }
    }

    public static UnitTimeTypeSlaEnum resolveByValue(int value) {
        return (value != 0 ? mappings.get(value) : null);
    }

}
