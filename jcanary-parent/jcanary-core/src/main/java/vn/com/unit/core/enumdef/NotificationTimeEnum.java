/*******************************************************************************
 * Class        ：NotificationTimeEnum
 * Created date ：2019/08/16
 * Lasted date  ：2019/08/16
 * Author       ：taitt
 * Change log   ：2019/08/16：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

/**
 * NotificationTimeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum NotificationTimeEnum {

	// Notification doc
		NOTIFI_TIME_DAY("NOTIFI_TIME_DAY"),
	    // Reminder doc
		NOTIFI_TIME_HOURS("NOTIFI_TIME_HOURS"),
	    // Escate doc
	    NOTIFI_TIME_MINUTES("NOTIFI_TIME_MINUTES");
	    
	    private String value;
	    
	    private NotificationTimeEnum(String value){
	        this.value = value;
	    }
	    
	    public String toString(){
	        return this.value;
	    }
}
