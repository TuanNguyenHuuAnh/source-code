/*******************************************************************************
 * Class        ：SendEmailTypeEnum
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * SendEmailTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum SendEmailTypeEnum {
    NO_VALUE("0"),
    SEND_DIRECT_NO_SAVE("1"),
    SEND_DIRECT_SAVE("2"),
    SEND_BY_BATCH("3");
    
    private String value;

    private SendEmailTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    private static final Map<String, SendEmailTypeEnum> mappings = new HashMap<>(SendEmailTypeEnum.values().length);

    static {
        for (SendEmailTypeEnum emailTypeEnum : values()) {
            mappings.put(emailTypeEnum.getValue(), emailTypeEnum);
        }
    }

    public static SendEmailTypeEnum resolveByValue(String value) {
        return (mappings.get(value) != null ? mappings.get(value) : NO_VALUE);
    }
}
