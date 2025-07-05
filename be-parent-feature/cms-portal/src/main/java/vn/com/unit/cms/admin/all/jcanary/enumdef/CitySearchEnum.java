/*******************************************************************************
 * Class        CitySearchEnum
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       TranLTH
 * Change log   2017/03/2201-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.enumdef;

/**
 * CitySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum CitySearchEnum {
    // city code
    CODE("city.code"),
    // city name
    NAME("city.name"),
    //region
    REGION("region.name"),
    // city note
    /*NOTE("job.note"),*/
    
    DESCRIPTION("searchfield.disp.description"),
    //country
//    COUNTRY("country.name"),
    ;
    
    private String value;
    
    private CitySearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
