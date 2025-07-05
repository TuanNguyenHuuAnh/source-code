/*******************************************************************************
 * Class        EmailSearchEnum
 * Created date 2018/08/22
 * Lasted date  2018/08/22
 * Author       phatvt
 * Change log   2018/08/2201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * EmailSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public enum TemplateSearchEnum {

    NAME("email.template.name"),
//    FILENAME("email.template.name.file"),
    CREATE("email.template.create");
    
    private String value;
    
    private TemplateSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
