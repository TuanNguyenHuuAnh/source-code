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
public enum DocumentTypeSearchEnum {
    /** code */
    CODE("document.type.code"),

//    /** label */
//    TITLE("document.type.title"),
    
    /** name */
    NAME("document.type.name"),
    
    /** description */
    DESCRIPTION("document.type.description")
    
    ;

    private String value;

    private DocumentTypeSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
