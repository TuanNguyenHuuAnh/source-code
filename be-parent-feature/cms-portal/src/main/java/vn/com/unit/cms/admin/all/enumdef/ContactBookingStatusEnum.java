/*******************************************************************************
 * Class        ：DocumentTypeSearchEnum
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * DocumentTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public enum ContactBookingStatusEnum {
	
    BOOKING_STATUS_PROCESSING("WAITING", "contact-booking.processing"),

    BOOKING_STATUS_PROCESSED("PROCESSED", "contact-booking.processed"),
    
    BOOKING_STATUS_DONE("DONE", "contact-booking.done"),
    
    BOOKING_STATUS_REJECTED("REJECTED", "contact-booking.rejected");

    private String statusName;
    private String statusAlias;

    private ContactBookingStatusEnum(String name, String alias) {
        this.statusName = name;
        this.statusAlias = alias;
    }

	public String getStatusName() {
		return statusName;
	}

	public String getStatusAlias() {
		return statusAlias;
	}

}
