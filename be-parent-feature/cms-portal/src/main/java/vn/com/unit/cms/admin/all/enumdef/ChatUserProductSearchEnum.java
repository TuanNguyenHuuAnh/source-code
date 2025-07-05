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
public enum ChatUserProductSearchEnum {
    /** STATUS */
//    STATUS("chat.list.status"),
    USER("chat.user.product.user") ,
    ROLE("chat.user.product.role");
    
    private String value;

    private ChatUserProductSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
