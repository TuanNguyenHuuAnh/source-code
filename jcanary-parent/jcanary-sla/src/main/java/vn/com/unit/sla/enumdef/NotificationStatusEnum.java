/*******************************************************************************
 * Class        ：NotiStatusEnum
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：TrieuVD
 * Change log   ：2020/11/18：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * NotiStatusEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum NotificationStatusEnum {

    SUCCESS("SUCCESS"),
    FAIL("FAIL"),
    ERROR("ERROR"),
    NO_VALUE("NO_VALUE");

    private String value;

    private NotificationStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    private static final Map<String, NotificationStatusEnum> mappings = new HashMap<>(NotificationStatusEnum.values().length);

    static {
        for (NotificationStatusEnum notiStatus : values()) {
            mappings.put(notiStatus.getValue(), notiStatus);
        }
    }

    public static NotificationStatusEnum resolveByValue(String value) {
        return (mappings.get(value) != null ? mappings.get(value) : NO_VALUE);
    }
}
