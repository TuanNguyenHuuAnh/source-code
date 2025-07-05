/*******************************************************************************
 * Class        ：JcaRoleSearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaRoleSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaRoleSearchEnum {
    CODE("code"),
    NAME("name"),
    DESCRIPTION("description"),
    ;
    
    private String value;
    
    private JcaRoleSearchEnum(String value){
        this.value = value;
    }
    
    @Override
    public String toString (){
        return value;
    }
}
