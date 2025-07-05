/*******************************************************************************
 * Class        AccountEnum
 * Created date 2020/12/08
 * Lasted date  2020/12/08
 * Author       SonND
 * Change log   2020/12/08 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.enumdef;

/**
 * AccountEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum AccountEnum {
	SUBMIT_NAME("Submit"),
    ASSIGN_NAME("Assign"),
    STAFF_NAME("Staff"),;
	
    private String value;
    
    private AccountEnum(String value){
        this.value = value;
    }
    
   
    public String getValue(){
        return value;
    }
}