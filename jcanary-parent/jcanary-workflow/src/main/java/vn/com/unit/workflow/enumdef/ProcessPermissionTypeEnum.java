/*******************************************************************************
 * Class        ProcessPermissionTypeEnum
 * Created date 2020/01/15
 * Lasted date  2020/01/15
 * Author       SonND
 * Change log   2020/01/15 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.workflow.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * ProcessPermissionTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum ProcessPermissionTypeEnum {
    USER("User"),
    GROUP("Group"),
    ;
	
    private String value;
    
    private ProcessPermissionTypeEnum(String value){
        this.value = value;
    }
    
    @Override
    public String toString(){
        return value;
    }
    
private static final Map<String, ProcessPermissionTypeEnum> mappings = new HashMap<>(ProcessPermissionTypeEnum.values().length);
    
    static {
        for (ProcessPermissionTypeEnum validFlagEnum : values()) {
            mappings.put(validFlagEnum.toString(), validFlagEnum);
        }
    }
    public static ProcessPermissionTypeEnum resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }
    
}