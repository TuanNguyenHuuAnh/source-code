/*******************************************************************************
 * Class        ：ChatControlValueDto
 * Created date ：2017/08/21
 * Lasted date  ：2017/08/21
 * Author       ：phunghn
 * Change log   ：2017/08/21：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * ChatControlValueDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class ChatControlValueLanguageDto {

	private String mLanguageCode;
	
	private List<ChatControlValueDto> listControlValue;

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
	 * Get listControlValue
	 * @return List<ChatControlValueDto>
	 * @author phunghn
	 */
	public List<ChatControlValueDto> getListControlValue() {
		return listControlValue;
	}

	/**
	 * Set listControlValue
	 * @param   listControlValue
	 *          type List<ChatControlValueDto>
	 * @return
	 * @author  phunghn
	 */
	public void setListControlValue(List<ChatControlValueDto> listControlValue) {
		this.listControlValue = listControlValue;
	}

}
