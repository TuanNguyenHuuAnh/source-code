/*******************************************************************************
 * Class        ：EnabledStatusEnum
 * Created date ：2018/12/21
 * Lasted date  ：2018/12/21
 * Author       ：taitm
 * Change log   ：2018/12/21：01-00 taitm create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;


/**
 * EnabledStatusEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author taitm
 */
public enum EnabledStatusEnum {
    
    YES("1", "yes.title"),
    
    NO("0", "no.title");
    
    private String value;
    
    private String name;
 
    private EnabledStatusEnum(String value, String name) {
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
