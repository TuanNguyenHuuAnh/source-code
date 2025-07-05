/*******************************************************************************
 * Class        ：AccountTypeEnum
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：vinhlt
 * Change log   ：2021/02/24：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;


/**
 * AccountTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
public enum AccountTypeEnum {

	TYPE_GUEST("5"), TYPE_STAFF("1"), TYPE_AGENT("2"), TYPE_CANDIDATE("3"), TYPE_OTHER("4");

    private String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
