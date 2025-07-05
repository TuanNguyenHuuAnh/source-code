/*******************************************************************************
 * Class        ：JcaCalendarTypeSearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：SonND
 * Change log   ：2021/02/03：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;


/**
 * JcaCalendarTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum JcaCalendarTypeSearchEnum {
    CALENDAR_TYPE_CODE("calendarTypeCode"),
    CALENDAR_TYPE_NAME("calendarTypeName"),
    DESCRIPTION("description"),
    ;
    
    private String value;
    
    private JcaCalendarTypeSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
