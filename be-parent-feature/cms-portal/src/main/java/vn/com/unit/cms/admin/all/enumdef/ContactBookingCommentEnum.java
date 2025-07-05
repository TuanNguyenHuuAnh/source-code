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
public enum ContactBookingCommentEnum {

    /** banner code */
    REDIRECT("contact.booking.comment.redirected-to-branch", "contact.booking.comment.redirected-to-branch"),

    /** banner name */
    FINISH_APPOINTMENT("contact.booking.comment.branch_finish_appointment", "contact.booking.comment.branch_finish_appointment"),

    /** banner description */
    CUSTOMER_MISS_APPOINTMENT("contact.booking.comment.customer-miss-appointment", "contact.booking.comment.customer-miss-appointment"),
    
    CUSTOMER_CANCEL_APPOINTMENT("contact.booking.comment.customer-cancel-appointment", "contact.booking.comment.customer-cancel-appointment"),
    
    ILLLEGAL_REQUEST("contact.booking.comment.illegal-request", "contact.booking.comment.illegal-request"),
	
	ORTHERS(null, "contact.booking.comment.orthers")
    ;

    private String commentValue;
    private String commentTitle;

    private ContactBookingCommentEnum(String value, String title) {
        this.commentValue = value;
        this.commentTitle = title;
    }

	public String getCommentValue() {
		return commentValue;
	}

	public String getCommentTitle() {
		return commentTitle;
	}
}
