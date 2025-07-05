/*******************************************************************************
 * Class        ：JcaEmailTemplateSearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaEmailTemplateSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaEmailTemplateSearchEnum {
    CODE("name"),
    TEMPLATE_NAME("templateName"),
    CREATED_BY("createdBy"),
    ;
    
    private String value;
    
    private JcaEmailTemplateSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
