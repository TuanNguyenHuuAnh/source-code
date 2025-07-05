/*******************************************************************************
 * Class        ：FaqsTypeSearchEnum
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：hand
 * Change log   ：2017/03/19：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * FaqsTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum FaqsTypeSearchEnum {
    /** code */
//    CODE("faqs.type.code"),

    /** label */
    NAME("faqs.category.title"),
    
    /** description */
//    DESCRIPTION("faqs.type.description")
    ;

    private String value;

    private FaqsTypeSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
