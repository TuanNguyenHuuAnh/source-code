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
public enum ProductCategorySearchEnum {

    /** code */
    CODE("product.category.code"),

    /** label */
    LABEL("product.category.title"),
    
    NAME("product.category.name"),
    
    STATUS("product.status"),
    
    /** product description */
    DESCRIPTION("product.category.description")

    ;

    private String value;

    private ProductCategorySearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
