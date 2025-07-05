/*******************************************************************************
 * Class        ：IntroduceInternetBankingTypeEnum
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hand
 * Change log   ：2017/08/23：01-00 ：hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * IntroduceInternetBankingTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum IntroduceInternetBankingTypeEnum {
	/** BUSINESS_CUSTOMERS */
	BUSINESS_CUSTOMERS("business-customers", "introduce.internet.banking.business.customers"),

	/** LEGAL_ENTITY */
	LEGAL_ENTITY("personal-customers", "introduce.internet.banking.personal.customers"),

	;
	/** value */
	private String value;
	
	/** value */
	private String name;

	/**
	 * @param value
	 * @author hand
	 */
	private IntroduceInternetBankingTypeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
