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
public enum ErsCcAgencyImportEnum {
    NO("0"),
    CCCODE("1"),
    EFFECTIVEDATE("2"),
    BPCODE("3"),
    POSITION("4"),
    FULLNAME("5"),
    IDNUMBER("6"),
    MESSAGEERROR("7");

	private String value;

    private ErsCcAgencyImportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
