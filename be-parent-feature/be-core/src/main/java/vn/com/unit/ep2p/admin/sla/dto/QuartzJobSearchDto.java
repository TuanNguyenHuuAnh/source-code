/*******************************************************************************
 * Class        QuartzJobSearchDto
 * Created date 2018/08/24
 * Lasted date  2018/08/24
 * Author       hangnkm
 * Change log   2018/08/2401-00 hangnkm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.dto;


/**
 * QuartzJobSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hangnkm
 */
public class QuartzJobSearchDto {
    
    private String schedName;
    
    private String triggerName;
    
    private String triggerState;

    
    public String getSchedName() {
        return schedName;
    }

    
    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    
    public String getTriggerName() {
        return triggerName;
    }

    
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    
    public String getTriggerState() {
        return triggerState;
    }

    
    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    
}
