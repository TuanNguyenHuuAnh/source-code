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
public enum RoomClientEnum {

	STT("0"),
	AGENTNAME("1"),
	FULLNAME("2"),
	EMAIL("3"),
	PHONE("4"),
	SERVICE("5"),
	CREATEDDATE("6"),
	DISCONNECTEDDATE("7"),
	TOTALSUPPORT("8");
	
	private String value;

    private RoomClientEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
