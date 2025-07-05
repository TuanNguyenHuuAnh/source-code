/*******************************************************************************
 * Class        CategorySearchEnum
 * Created date 2019/04/17
 * Lasted date  2019/04/17
 * Author       KhoaNA
 * Change log   2019/04/17 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

public enum CaManagementSearchEnum {
	CA_NAME("ca.management.ca.name"),
	ACCOUNT_NAME("ca.management.account"),
	CA_SLOT("ca.management.ca.slot")
    ;
    
    private String value;
    
    private CaManagementSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
