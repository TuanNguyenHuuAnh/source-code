/*******************************************************************************
 * Class        ：UpdateAppVersionTypeEnum
 * Created date ：2020/04/28
 * Lasted date  ：2020/04/28
 * Author       ：KhuongTH
 * Change log   ：2020/04/28：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * UpdateAppVersionTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public enum UpdateAppVersionTypeEnum {
    MANDATORY("1", "update.app.version.type.mandatory"),
    OPTIONAL("2", "update.app.version.type.optional"),
    WITHOUT_NOTICE("3", "update.app.version.type.without.notice");
    
    private String value;
    private String text;
    
    UpdateAppVersionTypeEnum(String value, String text){
        this.value = value;
        this.text = text;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getText() {
        return this.text;
    }
}
