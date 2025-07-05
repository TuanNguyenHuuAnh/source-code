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
public enum ErsCcAgencyExportEnum {
    NO("0"),
    CCCODE("1"),
    EFFECTIVEDATE("2"),
    BPCODE("3"),
    POSITION("4"),
    FULLNAME("5"),
    IDNUMBER("6"),
    MESSAGEERROR("7");

	private String value;

    private ErsCcAgencyExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
