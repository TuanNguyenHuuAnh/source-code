/*******************************************************************************
 * Class        ：IntroduceInternetBankingSearchEnum
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * IntroduceInternetBankingSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public enum IntroduceInternetBankingSearchEnum {
	 /** code */
    CODE("introduce.internet.banking.code"),
    
    /** name */
    NAME("introduce.internet.banking.name"),

    /** title */
    TITLE("introduce.internet.banking.title"),
    
    /** title detail */
   // TITLE_DETAIL("introduce.internet.banking.titleDetail"),

    /** description */
    DESCRIPTION("introduce.internet.banking.description")

    ;
      /** value*/
	  private String value;
	  
	  /**
	   * @param value
	   * @author hoangnp
	   */
	  private IntroduceInternetBankingSearchEnum(String value){
		  this.value = value;
	  }
	 
	  public String toString(){
		  return value;
	  }
}
