/*******************************************************************************
 * Class        ：RecruitmentSearchEnum
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;
/**
* RecruitmentSearchEnum
* 
* @version 01-00
* @since 01-00
* @author phunghn
*/
public enum RecruitmentSearchEnum {
	/** code */
    POSITION("recruitment.position")
    ;
    
    private String value;

    private RecruitmentSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
