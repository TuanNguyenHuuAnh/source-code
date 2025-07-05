/*******************************************************************************
 * Class        ：JcaRepositorySearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaRepositorySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaRepositorySearchEnum {
    CODE("code"),
    NAME("name"),
    PHYSICAL_PATH("physicalPath"),
    SUB_FOLDER_PATH("subFolderPath"),
    ;
    
    private String value;
    
    private JcaRepositorySearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
