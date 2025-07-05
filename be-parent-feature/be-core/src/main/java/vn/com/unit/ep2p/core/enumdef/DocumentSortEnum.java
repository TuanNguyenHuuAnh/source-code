/*******************************************************************************
 * Class        ：DocumentSortEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：taitt
 * Change log   ：2021/02/03：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * DocumentSortEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum DocumentSortEnum {
    ASSIGNED_NAME("assignedName","ASSIGNED_NAME"),
    ASSIGNED_ID("assignedId","ASSIGNED_ID"),
    
    SUBMITTED_NAME("submittedName","SUBMITTED_NAME"),
    SUBMITTED_ID("submittedId","SUBMITTED_ID"),
    SUBMITTED_DATE("submittedDate","SUBMITTED_DATE"),
    
    STAFF_NAME("staffName","STAFF_NAME"),
    STAFF_ID("staffId","STAFF_ID"),
    
    PRIORITY_NAME("priorityName","PRIORITY_NAME"),
    STATUS_NAME("statusName","STATUS_NAME"),
    PROCESS_STATUS_NAME("processStatusName","PROCESS_STATUS_NAME"),


    
    ORG_NAME("orgName","ORG_NAME")
    ;

    private String value;
    private String valueMapping;
    
    
    private DocumentSortEnum(String value, String valueMapping){
        this.value = value;
        this.valueMapping = valueMapping;
    }
    
    private static final Map<String, DocumentSortEnum> mappings = new HashMap<>(DocumentSortEnum.values().length);

    static {
        for (DocumentSortEnum docSort : values()) {
            mappings.put(docSort.getValueMapping(), docSort);
        }
    }

    public static DocumentSortEnum resolveByValueMapping(String valueMapping) {
        return (valueMapping != null ? mappings.get(valueMapping) : null);
    }
    
    public String toString(){
        return value;
    }
    
    public String getValueMapping(){
        return valueMapping;
    }
}
