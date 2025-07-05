/*******************************************************************************
 * Class        ：ChatControlerDto
 * Created date ：2017/08/21
 * Lasted date  ：2017/08/21
 * Author       ：phunghn
 * Change log   ：2017/08/21：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * ChatControlerDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class ChatControlDto {

	private String code;
	
	private String controlName;
	
	private int isSetData;
	
	private List<ChatControlValueLanguageDto> controlValues;

	/**
	 * Get code
	 * @return String
	 * @author phunghn
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * @param   code
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get controlName
	 * @return String
	 * @author phunghn
	 */
	public String getControlName() {
		return controlName;
	}

	/**
	 * Set controlName
	 * @param   controlName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	/**
	 * Get isSetData
	 * @return int
	 * @author phunghn
	 */
	public int getIsSetData() {
		return isSetData;
	}

	/**
	 * Set isSetData
	 * @param   isSetData
	 *          type int
	 * @return
	 * @author  phunghn
	 */
	public void setIsSetData(int isSetData) {
		this.isSetData = isSetData;
	}

	/**
	 * Get controlValues
	 * @return List<ChatControlValueLanguageDto>
	 * @author phunghn
	 */
	public List<ChatControlValueLanguageDto> getControlValues() {
		return controlValues;
	}

	/**
	 * Set controlValues
	 * @param   controlValues
	 *          type List<ChatControlValueLanguageDto>
	 * @return
	 * @author  phunghn
	 */
	public void setControlValues(List<ChatControlValueLanguageDto> controlValues) {
		this.controlValues = controlValues;
	}
	
	
}
