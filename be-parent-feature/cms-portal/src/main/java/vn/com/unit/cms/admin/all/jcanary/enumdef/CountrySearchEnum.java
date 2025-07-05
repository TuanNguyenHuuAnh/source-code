/*******************************************************************************
 * Class        CountrySearchEnum
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       TranLTH
 * Change log   2017/03/2201-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.enumdef;

/**
 * CountrySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum CountrySearchEnum {
    //Country code
    CODE("country.code"),
    //Country name
    NAME("country.name"),
    //web code
    WEBCODE("country.webcode"),
    //phone code
    PHONECODE("country.phonecode"),
    
    DESCRIPTION("searchfield.disp.description"),
    ;
    
    private String value;

    private CountrySearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
