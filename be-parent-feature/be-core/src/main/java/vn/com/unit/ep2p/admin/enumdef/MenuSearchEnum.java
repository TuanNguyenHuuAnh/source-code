/*******************************************************************************
 * Class        MenuSearchEnum
 * Created date 2017/04/14
 * Lasted date  2017/04/14
 * Author       TranLTH
 * Change log   2017/04/1401-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

/**
 * MenuSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum MenuSearchEnum {
    // Menu code
    CODE("menu.left.code"),
    // Menu name
    NAME("menu.left.name"),
    // Url
    URL("menu.left.url"),
    //Menu type
    TYPE("menu.left.type")
    ;
    
    private String value;

    private MenuSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
