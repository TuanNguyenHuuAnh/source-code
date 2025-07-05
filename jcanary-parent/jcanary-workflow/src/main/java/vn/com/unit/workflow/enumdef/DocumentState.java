/*******************************************************************************
 * Class        ：DocumentState
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：tantm
 * Change log   ：2020/11/18：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * DocumentState
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public enum DocumentState {
    DRAFT("000", "DRAFT"),
    APPROVED("994", "APPROVED"),
    PENDING("995", "PENDING"),
    IN_PROGRESS("996", "IN_PROGRESS"),
    RETURN("997", "RETURN"),
    REJECT("998", "REJECT"),
    COMPLETE("999", "COMPLETE");
	
    private String value;
    private String valueMapping;
    
    private DocumentState(String value, String valueMapping){
        this.value = value;
        this.valueMapping = valueMapping;
    }
    
    private static final Map<String, DocumentState> mappings = new HashMap<>(DocumentState.values().length);

    static {
        for (DocumentState docState : values()) {
            mappings.put(docState.getValueMapping(), docState);
        }
    }

    public static DocumentState resolveByValueMapping(String valueMapping) {
        return (valueMapping != null ? mappings.get(valueMapping) : null);
    }
    
    public String toString(){
        return value;
    }
    
    public String getValueMapping(){
        return valueMapping;
    }
}