/*******************************************************************************
 * Class        ProcessParamTypeEnum
 * Created date 2020/01/15
 * Lasted date  2020/01/15
 * Author       SonND
 * Change log   2020/01/15 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.workflow.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * ProcessParamTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum ProcessParamTypeEnum {
	INTEGER("Integer"),
	DOUBLE("Double"),
	FLOAT("Float"),
	STRING("String"),
	BOOLEAN("Boolean"),
	DATE("Date"),
	LONG("Long"),
    ;
	
    private String value;
    
    private ProcessParamTypeEnum(String value){
        this.value = value;
    }
    
    @Override
    public String toString(){
        return value;
    }
    
    private static final Map<String, ProcessParamTypeEnum> mappings = new HashMap<>(ProcessParamTypeEnum.values().length);
    
    static {
        for (ProcessParamTypeEnum validFlagEnum : values()) {
            mappings.put(validFlagEnum.toString(), validFlagEnum);
        }
    }
    public static ProcessParamTypeEnum resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }

    
}