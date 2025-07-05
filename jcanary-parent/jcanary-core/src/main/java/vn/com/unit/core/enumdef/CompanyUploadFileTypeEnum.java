/*******************************************************************************
 * Class        ：CompanyUploadFileType
 * Created date ：2021/01/07
 * Lasted date  ：2021/01/07
 * Author       ：ngannh
 * Change log   ：2021/01/07：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;


/**
 * CompanyUploadFileType
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public enum CompanyUploadFileTypeEnum {
    LOGIN_BACKGROUND("1"),
    
    SHORTCUT_ICON("2"),
    
    LOGO_LARGE("3"),
    
    LOGO_MINI("4"),
    ;

    private String value;
    
    /** @param string
     * @author ngannh
     */
    CompanyUploadFileTypeEnum(String value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        return this.value;
    }
}
