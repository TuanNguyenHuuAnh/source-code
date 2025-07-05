/*******************************************************************************
 * Class        ：RoomClientSearchEnum
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * RoomClientSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public enum ChatSupportStatusSearchEnum {
    /** STATUS */
//    STATUS("chat.list.status"),
    CANCLE("-1", "chat.list.status.cancel") ,
    CLOSE("0", "chat.list.status.close") ,
    CHATTING("1", "chat.list.status.chating") ,
    WAITING("2", "chat.list.status.waiting");
    
	private String code;
    private String name;

    private ChatSupportStatusSearchEnum(String code, String value) {
        this.name = value;
        this.code = code;
    }

    public String toString() {
        return name;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
