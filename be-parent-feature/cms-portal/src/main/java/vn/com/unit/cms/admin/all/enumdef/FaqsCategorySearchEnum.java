/*******************************************************************************
 * Class        ：FaqsCategorySearchEnum
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：hand
 * Change log   ：2017/03/19：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * FaqsCategorySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum FaqsCategorySearchEnum {
    /** code */
    CODE("faqs.category.code"),

    /** title */
    TITLE("faqs.category.title"),

    /** faqs type name */
    TYPE_NAME("faqs.category.type"),
    
    /** faqs description */
    DESCRIPTION("faqs.category.description")

    ;

    private String value;

    private FaqsCategorySearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
