/*******************************************************************************
 * Class        ：RoomClientEnum
 * Created date ：2017/09/21
 * Lasted date  ：2017/09/21
 * Author       ：phunghn
 * Change log   ：2017/09/21：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * RoomClientEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public enum RoomClientOfflineEnum {

	STT("0"),
	FULLNAME("1"),
	EMAIL("2"),
	PHONE("3"),
	NICKNAME("4"),
	CREATEDDATE("5"),
	MESSAGE("6");
	
	private String value;

    private RoomClientOfflineEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
