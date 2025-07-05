/*******************************************************************************
 * Class        ComplexityEnum
 * Created date 2018/08/13
 * Lasted date  2018/08/13
 * Author       phatvt
 * Change log   2018/08/1301-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * ComplexityEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public enum ComplexityEnum {
    UPPER("complexity.upper"),
    LOWER("complexity.lower"),
    NUMBER("complexity.number"),
    SPECIAL("complexity.special")
    ;
    
    private String value;
    
    private ComplexityEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
