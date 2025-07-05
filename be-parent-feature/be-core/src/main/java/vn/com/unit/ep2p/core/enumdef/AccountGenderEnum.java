/*******************************************************************************
 * Class        AccountGenderEnum
 * Created date 2020/12/08
 * Lasted date  2020/12/08
 * Author       SonND
 * Change log   2020/12/08 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * DocumentState
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum AccountGenderEnum {
	MALE("M", "Male"),
    FEMALE("F", "Female");
	
    private String value;
    private String valueMapping;
    
    private AccountGenderEnum(String value, String valueMapping){
        this.value = value;
        this.valueMapping = valueMapping;
    }
    
    private static final Map<String, AccountGenderEnum> mappings = new HashMap<>(AccountGenderEnum.values().length);

    static {
        for (AccountGenderEnum docState : values()) {
            mappings.put(docState.getValueMapping(), docState);
        }
    }

    public static AccountGenderEnum resolveByValueMapping(String valueMapping) {
        return (valueMapping != null ? mappings.get(valueMapping) : null);
    }
    
    @Override
    public String toString(){
        return value;
    }
    
    public String getValueMapping(){
        return valueMapping;
    }
}