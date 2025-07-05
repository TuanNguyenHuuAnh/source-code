/*******************************************************************************
 * Class        ：EmailStatusEnum
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * EmailStatusEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum EmailStatusEnum {
    EMAIL_SAVED("1", "SAVED"),
    EMAIL_SENDING("2", "SENDING"),
    EMAIL_SEND_SUCCESS("3", "SUCCESS"),
    EMAIL_SEND_FAIL("4", "FAIL"),
    UNKNOWN("0", "UNKNOWN"),
    ERROR("99", "ERROR");
    
    private String value;
    private String text;

    private EmailStatusEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return this.value;
    }

    public String getText() {
        return text;
    }

    private static final Map<String, EmailStatusEnum> mappings = new HashMap<>(EmailStatusEnum.values().length);

    static {
        for (EmailStatusEnum emailStatusEnum : values()) {
            mappings.put(emailStatusEnum.getValue(), emailStatusEnum);
        }
    }

    public static EmailStatusEnum resolveByValue(String value) {
        return (mappings.get(value) != null ? mappings.get(value) : UNKNOWN);
    }
}
