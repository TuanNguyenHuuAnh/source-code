/*******************************************************************************
 * Class        ：RoomClientEnum
 * Created date ：2023/10/08
 * Lasted date  ：2023/10/08
 * Author       ：kk.quan
 * Change log   ：2023/10/08：01-00 kk.quan create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * RoomClientEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author kk.quan
 */
public enum OrderDetailExportEnum {
	NO("0"),
	CODE("1"),
	AGENTCODE("2"),
	AGENTNAME("3"),
	CHANNEL("4"),
	OFFICECODE("5"),
	PHONE("6"),
	CREATEDATE("7"),
	STATUSNAME("8"),
	CANCELDATE("9"),
	CANCELBY("10"),
	INVOICECOMPANYNAME("11"),
	INVOICETAXCODE("12"),
	INVOICECOMPANYADDRESS("13"),
	PRODUCTNAME("14"),
	UNITPRICE("15"),
	QUANTITY("16"),
	AMOUNT("17");
	
	private String value;

    OrderDetailExportEnum(String value) {
    	this.value = value;
	}

    public String toString() {
        return value;
    }
}
