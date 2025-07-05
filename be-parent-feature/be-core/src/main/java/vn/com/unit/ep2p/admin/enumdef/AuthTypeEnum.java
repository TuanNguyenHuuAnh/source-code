/*******************************************************************************
 * Class        ：AuthTypeEnum
 * Created date ：2019/07/01
 * Lasted date  ：2019/07/01
 * Author       ：HungHT
 * Change log   ：2019/07/01：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public enum AuthTypeEnum {

    BASIC_AUTH("1"),
    BASIC_AUTH_JWT("2"),
	BASIC_AUTH_BP("3");

    /** value */
    private String value;

    /** mappings */
    private static final Map<String, AuthTypeEnum> mappings = new HashMap<>(AuthTypeEnum.values().length);

    static {
        for (AuthTypeEnum authTypeEnum : values()) {
            mappings.put(authTypeEnum.toString(), authTypeEnum);
        }
    }

    /**
     * resolveByValue
     * 
     * @param value
     * @return
     * @author HungHT
     */
    public static AuthTypeEnum resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }

    /**
     * @param value
     * @author HungHT
     */
    private AuthTypeEnum(String value) {
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return value;
    }
}
