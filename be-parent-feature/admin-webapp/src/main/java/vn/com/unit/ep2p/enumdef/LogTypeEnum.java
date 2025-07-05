/*******************************************************************************
 * Class        LogTypeEnum
 * Created date 2018/01/08
 * Lasted date  2018/01/08
 * Author       HUNGHT
 * Change log   2018/01/0801-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * LogTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
public enum LogTypeEnum {
    FATAL(100), ERROR(200), WARN(300), INFO(400), DEBUG(500), TRACE(600);

    private int value;

    private LogTypeEnum(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
