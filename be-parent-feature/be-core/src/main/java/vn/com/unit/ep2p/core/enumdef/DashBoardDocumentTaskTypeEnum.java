/*******************************************************************************
 * Class        ：DashBoardDocumentTaskTypeEnum
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：taitt
 * Change log   ：2021/01/21：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.enumdef;


/**
 * DashBoardDocumentTaskTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum DashBoardDocumentTaskTypeEnum {
    IN("TODO"),
    OUT("Pending");
    
    private String value;
    
    private DashBoardDocumentTaskTypeEnum(String value){
        this.value = value;
    }

    
    public String getValue() {
        return value;
    }

    
    public void setValue(String value) {
        this.value = value;
    }
}
