/***************************************************************
 * @author vunt					
 * @date Apr 12, 2021	
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.enumdef;

import java.util.HashMap;
import java.util.Map;

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
     * @author vunt
     */
    public static AuthTypeEnum resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }

    /**
     * @param value
     * @author vunt
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

