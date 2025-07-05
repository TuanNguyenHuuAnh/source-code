/*******************************************************************************
 * Class        ：StepCodeCommon
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：Tan Tai
 * Change log   ：2020/11/18：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>StepCodeCommon</p>.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public enum StepCodeCommon {
    
    /** The reject. */
    REJECT("rejected_998", "REJECT"),
    
    /** The finished. */
    FINISHED("finished_999", "FINISHED");
	
    /** The value. */
    private String value;
    
    /** The value mapping. */
    private String valueMapping;
    
    /**
     * <p>Instantiates a new step code common.</p>
     *
     * @author Tan Tai
     * @param value type {@link String}
     * @param valueMapping type {@link String}
     */
    private StepCodeCommon(String value, String valueMapping){
        this.value = value;
        this.valueMapping = valueMapping;
    }
    
    /** The Constant mappings. */
    private static final Map<String, StepCodeCommon> mappings = new HashMap<>(StepCodeCommon.values().length);

    static {
        for (StepCodeCommon docState : values()) {
            mappings.put(docState.getValueMapping(), docState);
        }
    }

    /**
     * <p>Resolve by value mapping.</p>
     *
     * @author Tan Tai
     * @param valueMapping type {@link String}
     * @return {@link StepCodeCommon}
     */
    public static StepCodeCommon resolveByValueMapping(String valueMapping) {
        return (valueMapping != null ? mappings.get(valueMapping) : null);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString(){
        return value;
    }
    
    /**
     * <p>Get value mapping.</p>
     *
     * @author Tan Tai
     * @return {@link String}
     */
    public String getValueMapping(){
        return valueMapping;
    }
}