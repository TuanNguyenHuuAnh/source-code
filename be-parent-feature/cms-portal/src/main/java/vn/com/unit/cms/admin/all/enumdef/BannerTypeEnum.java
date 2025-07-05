/*******************************************************************************
 * Class        ：BannerTypeEnum
 * Created date ：2017/03/18
 * Lasted date  ：2017/03/18
 * Author       ：hand
 * Change log   ：2017/03/18：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * BannerTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum BannerTypeEnum {
    
    BANNER_TOP("1"),
	
	BANNER_MIDDLE("2")

    ;

    private String value;

    private BannerTypeEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
