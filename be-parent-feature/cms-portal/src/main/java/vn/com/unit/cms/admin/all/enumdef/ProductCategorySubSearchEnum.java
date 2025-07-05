/*******************************************************************************
 * Class        ：ProductCategorySearchEnum
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * ProductCategorySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum ProductCategorySubSearchEnum {

    /** category */
    CATEGORY("product.category"),
    
    /** code */
//    CODE("product.category.code"),

    /** label */
    LABEL("product.category.sub.title"),

    /** product type name */
//    TYPE_NAME("product.category.type"),
    
    /** product description */
//    DESCRIPTION("product.category.description")

    ;

    private String value;

    private ProductCategorySubSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
