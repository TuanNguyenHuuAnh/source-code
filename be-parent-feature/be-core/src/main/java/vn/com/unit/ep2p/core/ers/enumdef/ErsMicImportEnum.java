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
public enum ErsMicImportEnum {
    NO("0"),
    UNITCODE("1"),
    UNITNAME("2"),
    AREANAME("3"),
    REGIONNAME("4"),
    MESSAGEERROR("5");

	private String value;

    private ErsMicImportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
