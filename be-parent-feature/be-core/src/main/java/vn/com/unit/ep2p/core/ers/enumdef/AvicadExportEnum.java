/*******************************************************************************
 * Class        ：AaCheckDebtReceiptExportEnum
 * Created date ：2020/06/17
 * Lasted date  ：2020/06/17
 * Author       ：TuyenNX
 * Change log   ：2020/06/17：01-00 TuyenNX create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.ers.enumdef;

/**
 * 
 */
public enum AvicadExportEnum {
    MESSAGEERROR("0"),
	IDNO("1"),
	IDCARD("2"),
	TAXCODE("3"),
	ORGANIZATIONNAME("4"),
	AGENTNAME("5"),
	DOB("6"),
	GENDER("7"),
	AGENTCODE("8"),
	LIFECER("9"),
	INSURER("10"),
	AGTSTS("11"),
	RECENTCONT("12"),
	RECENTTER("13"),
	MISDAT("14"),
	BLKCODE("15"),
	BLACKLIST("16"),
	ISCOMPANY("17"),
	ISOTHERCOMPANY("18"),
	RESULT("19");
	
	private String value;

    private AvicadExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
