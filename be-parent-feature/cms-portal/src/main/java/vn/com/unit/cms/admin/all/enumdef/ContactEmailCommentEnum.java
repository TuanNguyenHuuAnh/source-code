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
public enum ContactEmailCommentEnum {
	
    WAITING("contact.email.comment.waiting", "contact.email.comment.waiting"),

    PROCESSING("contact.email.comment.processing", "contact.email.comment.processing"),

    DONE("contact.email.comment.done", "contact.email.comment.done"),
	
	ORTHERS(null, "contact.booking.comment.orthers");

    private String commentValue;
    private String commentTitle;

    private ContactEmailCommentEnum(String value, String title) {
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
