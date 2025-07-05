/*******************************************************************************
 * Class        ：SettingChatLanguageDto
 * Created date ：2017/07/12
 * Lasted date  ：2017/07/12
 * Author       ：phunghn
 * Change log   ：2017/07/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * SettingChatLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class SettingChatLanguageDto {

	private String mLanguageCode;
	
	private List<SettingChatDto> listControls;

	/**
	 * Get mLanguageCode
	 * @return String
	 * @author phunghn
	 */
	public String getmLanguageCode() {
		return mLanguageCode;
	}

	/**
	 * Set mLanguageCode
	 * @param   mLanguageCode
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setmLanguageCode(String mLanguageCode) {
		this.mLanguageCode = mLanguageCode;
	}

	/**
	 * Get listControls
	 * @return List<SettingChatDto>
	 * @author phunghn
	 */
	public List<SettingChatDto> getListControls() {
		return listControls;
	}

	/**
	 * Set listControls
	 * @param   listControls
	 *          type List<SettingChatDto>
	 * @return
	 * @author  phunghn
	 */
	public void setListControls(List<SettingChatDto> listControls) {
		this.listControls = listControls;
	}

	

}
