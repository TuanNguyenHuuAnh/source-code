/*******************************************************************************
 * Class        ：DocumentSearchEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：TrieuVD
 * Change log   ：2021/02/03：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;

/**
 * DocumentSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public enum DocumentSearchEnum {

    DOC_TITLE("docTitle"), DOC_CODE("docCode"), SUBMITTED_NAME("submittedName");

    private String value;

    private DocumentSearchEnum(String value){
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
