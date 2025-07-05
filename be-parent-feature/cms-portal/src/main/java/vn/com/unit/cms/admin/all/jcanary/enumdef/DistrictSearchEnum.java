/*******************************************************************************
 * Class        DistrictSearchEnum
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       TranLTH
 * Change log   2017/03/2201-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.enumdef;

/**
 * DistrictSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum DistrictSearchEnum {
    // district code
    CODE("district.code"),
    // district name
    NAME("district.name"),
    // district note
    CITY("city.name"),
    //region
    NOTE("job.note"),
    //city
    REGION("region.name"),
    //country
    COUNTRY("country.name"),
    ;
    
    private String value;
    
    private DistrictSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
