/*******************************************************************************
 * Class        ：SettingChatEnumType
 * Created date ：2017/07/12
 * Lasted date  ：2017/07/12
 * Author       ：phunghn
 * Change log   ：2017/07/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.enumdef;

/**
 * SettingChatEnumType
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public enum SettingChatEnumType {
	ONLINE("0"),
	OFFLINE("1"),
	MESSAGE("2"),;
	
	private String value;

    private SettingChatEnumType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
	
}
