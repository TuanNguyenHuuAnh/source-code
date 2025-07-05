/*******************************************************************************
 * Class        ：BannerSearchEnum
 * Created date ：2017/03/18
 * Lasted date  ：2017/03/18
 * Author       ：hand
 * Change log   ：2017/03/18：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * BannerSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum ContactBookingSearchEnum {
    FULL_NAME("contact.booking.fullname"),

    EMAIL("contact.booking.email"),
    
    ID_NUMBER("contact.booking.id-number"),
    
    BOOKING_SUBJECT("contact.booking.booking-title"),
    
    PLACE_BOOKING("contact.booking.booking-place"),
    
    BOOKING_CONTENT("contact.booking.booking-content"),
    
    PHONE_NUMBER("contact.booking.phone-number");

    private String value;

    private ContactBookingSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
