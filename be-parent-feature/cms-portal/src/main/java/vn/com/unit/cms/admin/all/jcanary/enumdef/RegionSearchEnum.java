/*******************************************************************************
 * Class        RegionSearchEnum
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       TranLTH
 * Change log   2017/03/2201-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.enumdef;

/**
 * RegionSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum RegionSearchEnum {
    //region code
    CODE("region.code"),
    //region name
    NAME("region.name"),
    //country
    COUNTRY("country.name"),
    //region note
//    NOTE("job.note"),
    DESCRIPTION("searchfield.disp.description"),
    ;
    
    private String value;
    
    private RegionSearchEnum(String value){
        this.value = value;        
    }
    
    public String toString(){
        return value;
    }
}
