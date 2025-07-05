/*******************************************************************************
 * Class        ：ExchangeRateImportEnum
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * ExchangeRateImportEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public enum ExchangeRateImportEnum {
    /** mCurrencyId */
    mCurrencyId("0"),
    /** mCurrencyName */
    mCurrencyName("1"),
    /** Buying */
    Buying("2"),
    /** Transfer */
    Transfer("3"),
    /** Selling */
    Selling("4");

    private String value;

    private ExchangeRateImportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
