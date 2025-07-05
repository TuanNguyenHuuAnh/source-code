/*******************************************************************************
 * Class        ：SlaAccountTypeEnum
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：TrieuVD
 * Change log   ：2020/11/18：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * SlaAccountTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum ReceiverTypeEnum {

    ACCOUNT_TO(1),
    ACCOUNT_CC(2),
    ACCOUNT_BCC(3);

    private Integer value;

    private ReceiverTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    private static final Map<Integer, ReceiverTypeEnum> mappings = new HashMap<>(ReceiverTypeEnum.values().length);

    static {
        for (ReceiverTypeEnum userType : values()) {
            mappings.put(userType.getValue(), userType);
        }
    }

    public static ReceiverTypeEnum resolveByValue(Integer value) {
        return (mappings.get(value) != null ? mappings.get(value) : null);
    }
}
