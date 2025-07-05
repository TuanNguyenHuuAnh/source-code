/*******************************************************************************
 * Class        ：DocumentTypeSearchEnum
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * DocumentTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public enum ProductCategorySubStatusEnum {
    SAVEDRAFF("master.status.savedraff"),

    SUBMIT("master.status.submit"),
    
    APPROVE("master.status.approve"),
    
    REJECT("master.status.reject");
    

    private String value;

    private ProductCategorySubStatusEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
