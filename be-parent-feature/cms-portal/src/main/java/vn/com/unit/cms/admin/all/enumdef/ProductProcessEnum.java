/*******************************************************************************
 * Class        ：ProductCategoryProcessEnum
 * Created date ：2017/03/22
 * Lasted date  ：2017/03/22
 * Author       ：hand
 * Change log   ：2017/03/22：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * ProductProcessEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum ProductProcessEnum {

    /** create */
    CREATE("saved", "master.status.savedraff"),

    /** submit */
    SUBMIT("submitted", "master.status.submit"),

    /** approval */
    APPROVAL("approved", "master.status.approve"),

    /** reject */
    REJECT("rejected", "master.status.reject"),

    /** return */
//    ASSIGN("assigned", "master.status.assign"),

    /** destroy */
//    DESTROY("destroyed", "master.status.destroy")
    ;

    private String value;
    
    private String name;

    private ProductProcessEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String toString() {
        return value;
    }
    
    public String getName() {
        return name;
    }

}
