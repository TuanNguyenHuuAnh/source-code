/*******************************************************************************
 * Class        ：Message
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：KhoaNA
 * Change log   ：2017/02/14：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.constant;

/**
 * Message
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class Message {
    // type status
    public static final String ERROR = "error";
    public static final String INFO = "info";
    public static final String SUCCESS = "success";
    public static final String WARNING = "warning";

    /** status: danger, info, success, warning */
    private String status;
    /** content */
    private String content;
    /** field */
    private String field;

    public Message(String status, String content) {
        super();
        this.status = status;
        this.content = content;
    }

    public Message(String status, String field, String content) {
        super();
        this.status = status;
        this.content = content;
        this.field = field;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
