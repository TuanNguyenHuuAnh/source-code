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
public enum ContactBookingExportEnum {
	STT("0"),
	FULLNAME("1"),
	EMAIL("2"),
	PHONENUMBER("3"),
	DATEBOOKINGEXPORT("4"),
	TIMEBOOKINGEXPORT("5"),
	BOOKINGSUBJECT("6"),
	PLACEBOOKING("7"),
	BOOKINGCONTENT("8"),
	CREATEDATEEXPORT("9"),
	COMMENT("10"),
	PROCESSEDUSER("11"),
	UPDATEDATE("12"),
	STATUSTITLE("13");
	
	private String value;

    private ContactBookingExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
