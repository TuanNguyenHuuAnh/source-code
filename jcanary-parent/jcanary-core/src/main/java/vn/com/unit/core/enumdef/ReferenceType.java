/*******************************************************************************
 * Class        ：ReferenceType
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * ReferenceType
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum ReferenceType {
    REF_CC("1", "REF_CC"),
    REF_TRANFER("2", "REF_TRANFER"),
    REF_JOIN("3", "REF_JOIN");
    
    private String values;
    private String valueMapping;
    
    private ReferenceType(String value, String valueMapping){
        this.values = value;
        this.valueMapping = valueMapping;
    }
    
    private static final Map<String, ReferenceType> mappings = new HashMap<>(ReferenceType.values().length);

    static {
        for (ReferenceType referenceType : values()) {
            mappings.put(referenceType.getValueMapping(), referenceType);
        }
    }

    public static ReferenceType resolveByValueMapping(String valueMapping) {
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
