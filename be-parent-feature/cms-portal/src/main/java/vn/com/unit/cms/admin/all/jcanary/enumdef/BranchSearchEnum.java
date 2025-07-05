/*******************************************************************************
 * Class        BranchSearchEnum
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       TranLTH
 * Change log   2017/03/2201-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.enumdef;

/**
 * BranchSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public enum BranchSearchEnum {

	//Branch code
    CODE("branch.code"),
    //Branch name
    NAME("branch.name"),
    //Branch address
    ADDRESS("branch.address"),
    //branch phone
    PHONE("branch.phone"),
    ;
    private String value;

    private BranchSearchEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
