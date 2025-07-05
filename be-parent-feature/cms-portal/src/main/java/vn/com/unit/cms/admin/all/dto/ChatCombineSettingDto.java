/*******************************************************************************
 * Class        ：ChatCombineSettingDto
 * Created date ：2017/08/21
 * Lasted date  ：2017/08/21
 * Author       ：phunghn
 * Change log   ：2017/08/21：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * ChatCombineSettingDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class ChatCombineSettingDto {

	private String settingJson;
	
	private String valueJson;

	/**
	 * Get settingJson
	 * @return String
	 * @author phunghn
	 */
	public String getSettingJson() {
		return settingJson;
	}

	/**
	 * Set settingJson
	 * @param   settingJson
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setSettingJson(String settingJson) {
		this.settingJson = settingJson;
	}

	/**
	 * Get valueJson
	 * @return String
	 * @author phunghn
	 */
	public String getValueJson() {
		return valueJson;
	}

	/**
	 * Set valueJson
	 * @param   valueJson
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setValueJson(String valueJson) {
		this.valueJson = valueJson;
	}

}
