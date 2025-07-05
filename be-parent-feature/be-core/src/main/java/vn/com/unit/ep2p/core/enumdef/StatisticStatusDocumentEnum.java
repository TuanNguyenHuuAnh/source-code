/*******************************************************************************
 * Class        ：StatisticStatusDocumentEnum
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：taitt
 * Change log   ：2021/02/03：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.enumdef;


/**
 * StatisticStatusDocumentEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public enum StatisticStatusDocumentEnum {
    ALL_DOC("0"),
    UNAPPROVED_DOC("1"),
    APPROVED_DOC("2"),
    REJECTED_DOC("3"),
    OVERDUE_TASK("4");
    
    private String value;
    
    private StatisticStatusDocumentEnum(String value){
        this.value = value;
    }

    
    public String getValue() {
        return value;
    }

    
    public void setValue(String value) {
        this.value = value;
    }
}
