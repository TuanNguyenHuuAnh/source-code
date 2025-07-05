/*******************************************************************************
 * Class        ：DocumentTypeSearchEnum
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * DocumentTypeSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public enum GenderEnum {
	
    GENDER_MR(1, "gender.mr", "gender.male"),

    GENDER_MS(2, "gender.ms", "gender.female");

    private String genderTitle;
    private Integer genderValue;
    private String genderName;

    private GenderEnum(Integer genderValue, String genderTitle, String genderName) {
        this.genderTitle = genderTitle;
        this.genderValue = genderValue;
        this.genderName = genderName;
    }

    public String genderTitle() {
        return genderTitle;
    }
    
    public Integer genderValue(){
        return genderValue;
    }
    
    public String genderName(){
        return genderName;
    }
}
