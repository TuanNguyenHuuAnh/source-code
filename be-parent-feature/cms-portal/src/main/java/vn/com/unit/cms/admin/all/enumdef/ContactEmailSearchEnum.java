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
public enum ContactEmailSearchEnum {
    FULL_NAME("contact.email.fullname"),

    EMAIL("contact.email.email"),
    
    EMAIL_SUBJECT("contact.email.email-title"),
    
    EMAIL_CONTENT("contact.email.email-content");
    
//	MOTIVE("contact.email.motive");

    private String value;

    private ContactEmailSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
