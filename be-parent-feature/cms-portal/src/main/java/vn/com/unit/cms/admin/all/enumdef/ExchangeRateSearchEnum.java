/*******************************************************************************
 * Class        ：ExchangeRateSearchEnum
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * ExchangeRateSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public enum ExchangeRateSearchEnum {
	/** DISPLAYDATE */
    DISPLAYDATE("exchangerate.displaydate")
    ;

    private String value;

    private ExchangeRateSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
