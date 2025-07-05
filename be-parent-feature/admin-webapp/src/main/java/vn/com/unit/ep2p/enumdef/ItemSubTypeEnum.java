/*******************************************************************************
 * Class        ：ItemSubTypeEnum
 * Created date ：2019/11/28
 * Lasted date  ：2019/11/28
 * Author       ：trieuvd
 * Change log   ：2019/11/28：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;


/**
 * ItemSubTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public enum ItemSubTypeEnum {
    LINK("LINK"), FUNC("FUNC"), PERM("PERM"), FIELD_PERM("FIELD_PERM");
    
    private String value;

    private ItemSubTypeEnum(String value) {
        this.value = value;
    }

    /**
     * Get value
     * @return String
     * @author trieuvd
     */
    public String getValue() {
        return value;
    }
    
    
    
}
