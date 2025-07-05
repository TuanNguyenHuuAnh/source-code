/*******************************************************************************
 * Class        JpmBusinessSearchEnum
 * Created date 2019/04/17
 * Lasted date  2019/04/17
 * Author       KhoaNA
 * Change log   2019/04/17 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.enumdef;

/**
 * JpmBusinessSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum JpmBusinessSearchEnum {

    CODE("jpm.business.code"),
    
    NAME("jpm.business.name")
    ;
    
    private String value;
    
    private JpmBusinessSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
