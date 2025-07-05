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
public enum ContactEmailExportEnum {
    STT("0"),
	EMAILSUBJECT("1"),
	FULLNAME("2"),
	EMAIL("3"),
	CUSTOMERNAME("4"),
	CREATEDATESTRING("5"),
	MOTIVENAME("6"),
	SERVICENAME("7"),
	COMMENT("8"),
	STATUSTITLE("9"),
	PROCESSEDUSER("10"),
	UPDATEDATE("11");
	
	private String value;

    private ContactEmailExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
