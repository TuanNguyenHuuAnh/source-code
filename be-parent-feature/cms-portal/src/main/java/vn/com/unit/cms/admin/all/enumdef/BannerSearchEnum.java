/*******************************************************************************
 * Class        ：BannerSearchEnum
 * Created date ：2017/03/18
 * Lasted date  ：2017/03/18
 * Author       ：hand
 * Change log   ：2017/03/18：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * BannerSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum BannerSearchEnum {
    /** banner code */
    CODE("banner.code"),

    /** banner name */
    NAME("banner.name"),

    /** banner description */
//    DESCRIPTION("banner.description"),
    
    /** banner mobile */
//    MOBILE("banner.mobile"),
    
    /** banner pc */
//    BANNER_PC("banner.PC"),
    
/*    BANNER_TOP("banner.type.slide.show"),
	
	BANNER_MIDDLE("banner.type.static")*/

    ;

    private String value;

    private BannerSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
