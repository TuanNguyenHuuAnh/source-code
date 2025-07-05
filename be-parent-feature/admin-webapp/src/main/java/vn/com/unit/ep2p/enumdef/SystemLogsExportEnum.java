/*******************************************************************************
 * Class        SystemLogsExportEnum
 * Created date 2018/01/08
 * Lasted date  2018/01/08
 * Author       HUNGHT
 * Change log   2018/01/0801-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * SystemLogsExportEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
public enum SystemLogsExportEnum {

	FUNCTIONCODE("0"), LOGSUMMARY("1"), LOGTYPETEXT("2"), LOGDATE("3"), LOGDETAIL("4"), IP("5"), USERNAME("6");

	private String value;

	private SystemLogsExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
