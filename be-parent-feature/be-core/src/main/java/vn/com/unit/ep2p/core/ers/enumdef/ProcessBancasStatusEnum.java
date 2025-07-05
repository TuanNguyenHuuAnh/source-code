/**
 * @author TaiTM
 */
package vn.com.unit.ep2p.core.ers.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public enum ProcessBancasStatusEnum {
    DRAFT("000", "DRAFT"),
    REJECT("998", "REJECT"),
    FINISHED("999", "FINISHED"),
    PENDING_APPORVE("pendingApprove", "pendingApprove"),
    PENDING_EDUCATE("pendingEducate", "pendingEducate"),
    PENDING_INTERVIEW("pendingInterview", "pendingInterview"),
    PENDING_TRAINING("pendingTraining", "pendingTraining"),
    WAITING_APPORVE("waitingApprove", "waitingApprove"),
    WAITING_EDUCATE("waitingEducate", "waitingEducate"),
    WAITING_INTERVIEW("waitingInterview", "waitingInterview"),
    WAITING_TRAINING("waitingTraining", "waitingTraining"),
    WAITING_FINISHED("waitingFinished", "waitingFinished");
    
    private String value;
    private String valueMapping;

    private ProcessBancasStatusEnum(String value, String valueMapping) {
        this.value = value;
        this.valueMapping = valueMapping;
    }

    private static final Map<String, ProcessBancasStatusEnum> mappings = new HashMap<>(ProcessBancasStatusEnum.values().length);

    static {
        for (ProcessBancasStatusEnum docState : values()) {
            mappings.put(docState.getValueMapping(), docState);
        }
    }

    public static ProcessBancasStatusEnum resolveByValueMapping(String valueMapping) {
        return (valueMapping != null ? mappings.get(valueMapping) : null);
    }

    public String toString() {
        return value;
    }

    public String getValueMapping() {
        return valueMapping;
    }
}
