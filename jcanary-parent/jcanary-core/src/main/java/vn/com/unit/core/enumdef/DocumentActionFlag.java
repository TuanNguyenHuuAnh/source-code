/*******************************************************************************
 * Class        ：DocumentActionFlag
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：tantm
 * Change log   ：2020/12/02：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;


/**
 * DocumentActionFlag
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public enum DocumentActionFlag {
    CREATE_DATA("1"),
    
    UPDATE_DATA("2"),
    
    REVERT_DATA("3"),
    
    SUBMIT_DATA("4"),
    
    DELETE_DATA("5"),
    
    REPLACE_DATA("6")
    ;

    private String value;
    
    private DocumentActionFlag(String value){
        this.value = value;
    }
    
    @Override
    public String toString (){
        return value;
    }
}
