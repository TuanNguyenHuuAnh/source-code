/*******************************************************************************
 * Class        ：JcaItemSearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaItemSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaItemSearchEnum {
    FUNCTION_CODE("functionCode"),
    FUNCTION_NAME("functionName"),
    DESCRIPTION("description"),
    ;
    
    private String value;
    
    private JcaItemSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
