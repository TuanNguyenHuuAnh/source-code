/*******************************************************************************
 * Class        ：JobProcessEnum
 * Created date ：2017/04/21
 * Lasted date  ：2017/04/21
 * Author       ：TranLTH
 * Change log   ：2017/04/21：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * JobProcessEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum JobProcessEnum {
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
    BUSINESS_CODE("AJ1", ""),
    
    /** business code home page */
    BUSINESS_CODE_AH1("AH1", "")
    ;

    private String value;
    
    private String name;

    private JobProcessEnum(String value, String name) {
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
