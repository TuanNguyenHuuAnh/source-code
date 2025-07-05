/*******************************************************************************
 * Class        ：NewsCategorySearchEnum
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：hand
 * Change log   ：2017/03/19：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * NewsCategorySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum NewsCategorySearchEnum {
    /** code */
    CODE("news.category.code"),

    /** label */
    LABEL("news.category.label"),

    /** news type name */
    TYPE_NAME("news.category.type"),
    
    /** customer type name */
    CUSTOMER_TYPE_NAME("customer.type"),
    
    /** news description */
    DESCRIPTION("news.category.description")

    ;

    private String value;

    private NewsCategorySearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
