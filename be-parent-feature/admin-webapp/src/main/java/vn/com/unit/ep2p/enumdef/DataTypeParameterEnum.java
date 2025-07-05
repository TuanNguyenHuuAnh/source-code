/*******************************************************************************
 * Class        ：DataTypeParameterEnum
 * Created date ：2020/02/03
 * Lasted date  ：2020/02/03
 * Author       ：trieuvd
 * Change log   ：2020/02/03：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;


/**
 * DataTypeParameterEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public enum DataTypeParameterEnum {
    CHAR("C")
    ,DATE("D");
    
    private String value;
    
    private DataTypeParameterEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
