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
public enum RoomClientSearchEnum {
    /** STATUS */
	/* STATUS("chat.list.status"), */
    AGENT("chat.list.agent"),
    SERVICE("chat.list.service"),
    FULLNAME("chat.list.fullname"),
    PHONE("chat.list.phone")
    ;
    
    private String value;

    private RoomClientSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
