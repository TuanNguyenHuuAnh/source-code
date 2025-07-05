/*******************************************************************************
 * Class        ：AaCheckDebtReceiptExportEnum
 * Created date ：2020/06/17
 * Lasted date  ：2020/06/17
 * Author       ：TuyenNX
 * Change log   ：2020/06/17：01-00 TuyenNX create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.ers.enumdef;

/**
 * AaCheckDebtReceiptExportEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenNX
 */
public enum ErsClassInfoExportEnum {
    NO("0"),
    MESSAGEERROR("1"),
    CHANNEL("2"),
    CLASSCODE("3"),
    ONLINEOFFLINE("4"),
    PROVINCE("5"),
    STARTDATE("6"),
    ENDDATE("7"),
    EXAMDATE("8"),;
	
	private String value;

    private ErsClassInfoExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
