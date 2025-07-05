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
public enum ContactEmailStatusEnum {
    BOOKING_STATUS_WAITING("WAITING", "contact-email.waiting"),

    BOOKING_STATUS_PROCESSING("PROCESSING", "contact-email.processing"),
    
    BOOKING_STATUS_DONE("DONE", "contact-email.done"),

    BOOKING_STATUS_REJECTED("REJECTED", "contact-email.rejected");

    private String statusName;
    private String statusAlias;

    private ContactEmailStatusEnum(String name, String alias) {
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
