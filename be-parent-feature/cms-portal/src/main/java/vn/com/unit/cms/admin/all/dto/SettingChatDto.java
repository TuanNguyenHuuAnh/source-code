/*******************************************************************************
 * Class        ：SettingChatDto
 * Created date ：2017/07/12
 * Lasted date  ：2017/07/12
 * Author       ：phunghn
 * Change log   ：2017/07/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * SettingChatDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class SettingChatDto {
	private Long id;
	
	private String fieldCode;
	
	private String fieldName;
	
	private int sort;
	
	private int type;
	
	private Boolean isDisplay;
	
	private Boolean isVisible;
	
	private String labelName;
	
	private String placeholderName;
	
	private String mLanguageCode;
	
	private List<ChatControlDto> controls;
	
	private String fieldType;
	
	private int isSetData;
	
	private List<ChatControlValueLanguageDto> controlValues;
	
	private Boolean isDefault;
	

	/**
	 * Get id
	 * @return Long
	 * @author phunghn
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * @param   id
	 *          type Long
	 * @return
	 * @author  phunghn
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get fieldCode
	 * @return String
	 * @author phunghn
	 */
	public String getFieldCode() {
		return fieldCode;
	}

	/**
	 * Set fieldCode
	 * @param   fieldCode
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	/**
	 * Get fieldName
	 * @return String
	 * @author phunghn
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Set fieldName
	 * @param   fieldName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Get sort
	 * @return int
	 * @author phunghn
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * Set sort
	 * @param   sort
	 *          type int
	 * @return
	 * @author  phunghn
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * Get type
	 * @return int
	 * @author phunghn
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set type
	 * @param   type
	 *          type int
	 * @return
	 * @author  phunghn
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Get isDisplay
	 * @return Boolean
	 * @author phunghn
	 */
	public Boolean getIsDisplay() {
		return isDisplay;
	}

	/**
	 * Set isDisplay
	 * @param   isDisplay
	 *          type Boolean
	 * @return
	 * @author  phunghn
	 */
	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * Get labelName
	 * @return String
	 * @author phunghn
	 */
	public String getLabelName() {
		return labelName;
	}

	/**
	 * Set labelName
	 * @param   labelName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	/**
	 * Get placeholderName
	 * @return String
	 * @author phunghn
	 */
	public String getPlaceholderName() {
		return placeholderName;
	}

	/**
	 * Set placeholderName
	 * @param   placeholderName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setPlaceholderName(String placeholderName) {
		this.placeholderName = placeholderName;
	}

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
	 * Get controls
	 * @return List<ChatControlDto>
	 * @author phunghn
	 */
	public List<ChatControlDto> getControls() {
		return controls;
	}

	/**
	 * Set controls
	 * @param   controls
	 *          type List<ChatControlDto>
	 * @return
	 * @author  phunghn
	 */
	public void setControls(List<ChatControlDto> controls) {
		this.controls = controls;
	}

	/**
	 * Get fieldType
	 * @return String
	 * @author phunghn
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * Set fieldType
	 * @param   fieldType
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
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

	/**
	 * Get isVisible
	 * @return Boolean
	 * @author phunghn
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * Set isVisible
	 * @param   isVisible
	 *          type Boolean
	 * @return
	 * @author  phunghn
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Get isDefault
	 * @return Boolean
	 * @author phunghn
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * Set isDefault
	 * @param   isDefault
	 *          type Boolean
	 * @return
	 * @author  phunghn
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	
	
}
