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
public enum ErsMicExportEnum {
    NO("0"),
    MESSAGEERROR("1"),
    UNITCODE("2"),
    UNITNAME("3"),
    AREANAME("4"),
    REGIONNAME("5");

	private String value;

    private ErsMicExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
