/*******************************************************************************
 * Class        ：ClassImportEnum
 * Created date ：2021/04/06
 * Lasted date  ：2021/04/06
 * Author       ：TuyenNX
 * Change log   ：2021/04/06：01-00 TuyenNX create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.ers.enumdef;

/**
 * ClassImportEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenNX
 */
public enum ErsClassInfoImportEnum {
    NO("0"),
    CHANNEL("1"),
    CLASSCODE("2"),
    ONLINEOFFLINE("3"),
    PROVINCE("4"),
    STARTDATE("5"),
    ENDDATE("6"),
    EXAMDATE("7"),
    MESSAGEERROR("8");
	
	private String value;

    private ErsClassInfoImportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
