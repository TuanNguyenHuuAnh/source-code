/*******************************************************************************
 * Class        ：NewsProcessEnum
 * Created date ：2017/05/09
 * Lasted date  ：2017/05/09
 * Author       ：thuydtn
 * Change log   ：2017/05/09：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * NewsProcessEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public enum ShareHolderProcessEnum {

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
    
   /** business_code */
    BUSINESS_CODE("AS1")
    ;

    private String value;
    
    private String name;

    private ShareHolderProcessEnum(String value) {
        this.value = value;
        
    }
    private ShareHolderProcessEnum(String value, String name) {
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
