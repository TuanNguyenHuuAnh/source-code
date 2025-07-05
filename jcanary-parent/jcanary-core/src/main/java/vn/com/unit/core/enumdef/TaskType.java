/*******************************************************************************
 * Class        ：TaskType
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * TaskType
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum TaskType {
    TASK_TYPE_CANDIDATE("CANDIDATE", "TASK_TYPE_CANDIDATE"),
    TASK_TYPE_PARTICIPANT("PARTICIPANT", "TASK_TYPE_PARTICIPANT"),
    TASK_TYPE_ASSIGNEE("ASSIGNEE", "TASK_TYPE_ASSIGNEE");
    
    private String values;
    private String valueMapping;
    
    private TaskType(String value, String valueMapping){
        this.values = value;
        this.valueMapping = valueMapping;
    }
    
    private static final Map<String, TaskType> mappings = new HashMap<>(TaskType.values().length);

    static {
        for (TaskType taskType : values()) {
            mappings.put(taskType.getValueMapping(), taskType);
        }
    }

    public static TaskType resolveByValueMapping(String valueMapping) {
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
