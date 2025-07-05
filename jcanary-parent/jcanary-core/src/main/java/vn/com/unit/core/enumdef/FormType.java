/*******************************************************************************
 * Class        ：FormType
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * FormType
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum FormType {
    FREE_FORM_TYPE("1", "FREE_FORM"),
    FIXED_FORM_TYPE("2", "FIXED_FORM");
    
    private String values;
    private String valueMapping;
    
    private FormType(String value, String valueMapping){
        this.values = value;
        this.valueMapping = valueMapping;
    }
    
    private static final Map<String, FormType> mappings = new HashMap<>(FormType.values().length);

    static {
        for (FormType formType : values()) {
            mappings.put(formType.getValueMapping(), formType);
        }
    }

    public static FormType resolveByValueMapping(String valueMapping) {
        return (valueMapping != null ? mappings.get(valueMapping) : null);
    }
    
    @Override
    public String toString(){
        return values;
    }
    
    public String getValueMapping(){
        return valueMapping;
    }
}
