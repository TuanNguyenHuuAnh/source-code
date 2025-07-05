/*******************************************************************************

 * Class        ：ProcessType
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NhanNV
 * Change log   ：2020/11/11：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.core.workflow.enumdef;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ProcessType
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProcessType {

    FREE(1, "Free Process"), 
    INTEGRATE(2, "Integrate Process"), 
    BPMN(3, "BPMN Process");

    private int value;
    private String text;

    private static final Map<Integer, ProcessType> mappings = new HashMap<>(ProcessType.values().length);
    static {
        for (ProcessType type : values()) {
            mappings.put(type.getValue(), type);
        }
    }

    public static ProcessType resolveByValue(int value) {
        return mappings.get(value);
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
