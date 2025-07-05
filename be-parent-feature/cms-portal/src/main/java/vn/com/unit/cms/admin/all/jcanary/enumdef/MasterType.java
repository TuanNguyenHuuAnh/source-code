/*******************************************************************************
 * Class        ：MasterType
 * Created date ：2017/03/22
 * Lasted date  ：2017/03/22
 * Author       ：hand
 * Change log   ：2017/03/22：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.enumdef;

/**
 * MasterType
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum MasterType {
	/** AN1 business code of News */
	AN1("AN1"),

	/** AI1 */
	AI1("AI1"),

	AD1("AD1"),

	/** AF1 business code of Faqs */
	AF1("AF1"),

	/** AP1 business code of Product */
	AP1("AP1"),

	/** AP2 business code of ProductCategory */
	AP2("AP2"),
	// TODO change value
	SHAREHOLDER("AS1"),

	/** AJ1 business code of Job */
	AJ1("AJ1"),

	/** AH1 business code of home page */
	AH1("AH1"),

	/** SM1 business code for Menu */
	SM1("SM1")

	;

	private String value;

	private MasterType(String value) {
		this.value = value;
	}

	// LocLT
	// IMPORTANT
	// DON'T REMOVE @Override HERE
	// FIX ERROR ENUM WHEN DEPLOY JBOSS
	@Override
	public String toString() {
		return value;
	}

}
