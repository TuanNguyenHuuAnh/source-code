/*******************************************************************************
 * Class        ：APIStatus
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.api.enumdef;


/**
 * APIStatus
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum APIStatus {
    
    SUCCESS("SUCCESS"), FAIL("FAIL"),
    
    CREATE_SUCCESS("CREATE_SUCCESS"),CREATE_FAIL("CREATE_FAIL"),
    EDIT_SUCCESS("EDIT_SUCCESS"),EDIT_FAIL("EDIT_FAIL"),
    DELETE_SUCCESS("DELETE_SUCCESS"),DELETE_FAIL("DELETE_FAIL");
    
    private String value;
    
    private APIStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
