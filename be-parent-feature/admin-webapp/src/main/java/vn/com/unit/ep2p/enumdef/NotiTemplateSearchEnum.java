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
public enum NotiTemplateSearchEnum {

    NAME("noti.template.name"),
    CODE("noti.template.code");
    
    private String value;
    
    private NotiTemplateSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
