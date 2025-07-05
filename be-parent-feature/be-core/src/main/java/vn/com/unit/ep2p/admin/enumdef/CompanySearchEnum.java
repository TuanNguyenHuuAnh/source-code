/*******************************************************************************
 * Class        CompanySearchEnum
 * Created date 2019/04/17
 * Lasted date  2019/04/17
 * Author       KhoaNA
 * Change log   2019/04/17 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

/**
 * CompanySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum CompanySearchEnum {

    NAME("company.name"),    
    DESCRIPTION("company.description"),
    SYSTEM_CODE("company.system.code"),
    SYSTEM_NAME("company.system.name")
    ;
    
    private String value;

    private CompanySearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
