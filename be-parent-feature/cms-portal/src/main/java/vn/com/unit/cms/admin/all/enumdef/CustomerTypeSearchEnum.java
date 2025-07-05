/*******************************************************************************
 * Class        ：CustomerTypeSearchEnum
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * CustomerTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum CustomerTypeSearchEnum {
    
    /** code */
    CODE("customer.type.code"),

    /** title */
    TITLE("customer.type.title"),
    
    /** description */
    DESCRIPTION("customer.type.description")
    ;

    private String value;

    private CustomerTypeSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
