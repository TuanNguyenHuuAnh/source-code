/*******************************************************************************
 * Class        MenuProcessEnum
 * Created date 2017/04/21
 * Lasted date  2017/04/21
 * Author       TranLTH
 * Change log   2017/04/2101-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

/**
 * MenuProcessEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum MenuProcessEnum {
    /** create */
    CREATE("saved", "master.status.savedraff"),

    /** submit */
    SUBMIT("submitted", "master.status.submit"),

    /** approval */
    APPROVAL("approved", "master.status.approve"),

    /** reject */
    REJECT("rejected", "master.status.reject"),

    /** return */
    ASSIGN("assigned", "master.status.assign"),

    /** destroy */
    DESTROY("destroyed", "master.status.destroy"),
    
    /** business code */
    BUSINESS_CODE("SM1", "")
    ;

    private String value;
    
    private String name;

    private MenuProcessEnum(String value, String name) {
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
