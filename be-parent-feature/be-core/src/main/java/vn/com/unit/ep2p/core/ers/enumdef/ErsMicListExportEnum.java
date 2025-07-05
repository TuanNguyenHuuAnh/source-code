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
public enum ErsMicListExportEnum {
    NO("0"),
    UNITCODE("1"),
    UNITNAME("2"),
    AREANAME("3"),
    REGIONNAME("4"),
    CREATEDBY("5"),
    CREATEDDATE("6");

	private String value;

    private ErsMicListExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
