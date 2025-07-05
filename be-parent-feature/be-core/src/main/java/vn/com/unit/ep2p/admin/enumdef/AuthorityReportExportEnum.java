/*******************************************************************************
 * Class        AuthorityReportExportEnum
 * Created date 2018/01/08
 * Lasted date  2018/01/08
 * Author       HUNGHT
 * Change log   2018/01/0801-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

/**
 * AuthorityReportExportEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
public enum AuthorityReportExportEnum {

	USERNAME("0"), FULLNAME("1"), EMAIL("2"), ACTIVED("3"), GROUPCODE("4"), ROLENAME("5"), ROLEACTIVED(
			"6"), FUNCTIONNAME("7"), ACCESSRIGHT("8");

	private String value;

	private AuthorityReportExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
