/*******************************************************************************
 * Class        JpmProcessSearchEnum
 * Created date 2019/04/17
 * Lasted date  2019/04/17
 * Author       KhoaNA
 * Change log   2019/04/17 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.enumdef;

/**
 * JpmProcessSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum JpmProcessSearchEnum {

    CODE("jpm.process.code"),
    
    NAME("jpm.process.name")
    ;
    
    private String value;
    
    private JpmProcessSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
