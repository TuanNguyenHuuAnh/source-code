/*******************************************************************************
 * Class        ：ResultStatus
 * Created date ：2019/03/01
 * Lasted date  ：2019/03/01
 * Author       ：HungHT
 * Change log   ：2019/03/01：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;


/**
 * ResultStatus
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public enum ResultStatus {

    SUCCESS(1), FAIL(0);

    private int value;

    private ResultStatus(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }
}
