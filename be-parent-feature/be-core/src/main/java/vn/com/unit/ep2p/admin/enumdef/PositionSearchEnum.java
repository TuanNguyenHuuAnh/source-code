/*******************************************************************************
 * Class        PositionSearchEnum
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/0801-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

/**
 * PositionSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum PositionSearchEnum {
	//position code
    CODE("position.code"),
    //position name
    NAME("position.name"),
    //position name abv
    NAME_ABV("position.name.abv"),
    //position description
    DESCRIPTION("position.description"),
    ;
    
    private String value;
    
    private PositionSearchEnum(String value){
        this.value = value;        
    }
    
    public String toString(){
        return value;
    }
}
