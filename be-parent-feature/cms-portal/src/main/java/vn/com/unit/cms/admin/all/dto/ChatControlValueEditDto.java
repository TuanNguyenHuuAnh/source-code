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
public class ChatControlValueEditDto {

	private String field;
	
	private String controlCode;
	
	private  List<ChatControlValueLanguageDto> controlValues;

	/**
	 * Get field
	 * @return String
	 * @author phunghn
	 */
	public String getField() {
		return field;
	}

	/**
	 * Set field
	 * @param   field
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setField(String field) {
		this.field = field;
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

	/**
	 * Get controlCode
	 * @return String
	 * @author phunghn
	 */
	public String getControlCode() {
		return controlCode;
	}

	/**
	 * Set controlCode
	 * @param   controlCode
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setControlCode(String controlCode) {
		this.controlCode = controlCode;
	}

}
