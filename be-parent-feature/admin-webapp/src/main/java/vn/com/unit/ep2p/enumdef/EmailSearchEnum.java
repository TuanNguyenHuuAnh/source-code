/*******************************************************************************
 * Class        EmailSearchEnum
 * Created date 2018/08/22
 * Lasted date  2018/08/22
 * Author       phatvt
 * Change log   2018/08/2201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * EmailSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public enum EmailSearchEnum {

    RECEIVE("email.receive.address"),
    /*IMMEDIATELY("email.immediately"),*/
    SENDER("email.sender.address"),
  /*  STATUS("email.status"),*/
    SUBJECT("email.subject"),
    CONTENT("email.content");
    
    private String value;
    
    private EmailSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
