/*******************************************************************************
 * Class        ：JcaAccountSearchEnum
 * Created date ：2021/02/02
 * Lasted date  ：2021/02/02
 * Author       ：taitt
 * Change log   ：2021/02/02：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaAccountSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum JcaAccountSearchEnum {
    USERNAME("username"),
    
    FULLNAME("fullName"),
    
    EMAIL("email"),
    
    PHONE("phone")
    ;

    private String value;
    
    private JcaAccountSearchEnum(String value){
        this.value = value;
    }
    
    @Override
    public String toString (){
        return value;
    }
}
