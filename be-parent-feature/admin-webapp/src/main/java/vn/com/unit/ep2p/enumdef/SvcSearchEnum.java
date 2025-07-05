/*******************************************************************************
 * Class        SvcSearchEnum
 * Created date 2019/04/17
 * Lasted date  2019/04/17
 * Author       KhoaNA
 * Change log   2019/04/17 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * SvcSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum SvcSearchEnum {

    NAME("svc.management.name"),    
    DESCRIPTION("svc.management.description"),
    FORM_FILE("svc.management.file.name")
    ;
    
    private String value;

    private SvcSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
