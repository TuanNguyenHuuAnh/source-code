/*******************************************************************************
 * Class        MenuStatusEnum
 * Created date 2020/01/06
 * Lasted date  2020/01/06
 * Author       SonND
 * Change log   2020/01/06 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.enumdef;

/**
 * MenuStatusEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public enum MenuStatusEnum {

    NEW(1), 
    NORMAL(2);

    private int value;

    private MenuStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}