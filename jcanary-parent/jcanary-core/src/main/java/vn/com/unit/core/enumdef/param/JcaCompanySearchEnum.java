/*******************************************************************************
 * Class        ：JcaCompanySearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaCompanySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaCompanySearchEnum {
    NAME("name"),
    DESCRIPTION("description"),
    SYSTEM_CODE("systemCode"),
    SYSTEM_NAME("systemName"),
    ;
    
    private String value;
    
    private JcaCompanySearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
