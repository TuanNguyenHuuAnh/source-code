/*******************************************************************************
 * Class        ：JcaCategorySearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaCategorySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaCategorySearchEnum {
    NAME("category.name"),
    //ACCOUNT("account"),
    DESCRIPTION("category.description"),
    ;
    private String value;
    
    private JcaCategorySearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
