/*******************************************************************************
 * Class        ：JcaAccountOrgSearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaAccountOrgSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaAccountOrgSearchEnum {
    ORG_NAME("orgName"),
    POSITION_NAME("positionName"),
    ;

    private String value;
    
    private JcaAccountOrgSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
