/*******************************************************************************
 * Class        ：NewsTypeSearchEnum
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：hand
 * Change log   ：2017/03/19：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * NewsTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum NewsTypeSearchEnum {
    /** code */
    CODE("news.type.code"),

    /** label */
    LABEL("news.type.label"),
    
    /** customer type name */
    TYPE_NAME("customer.type"),
    
    /** description */
    DESCRIPTION("news.type.description")
    
    ;

    private String value;

    private NewsTypeSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
