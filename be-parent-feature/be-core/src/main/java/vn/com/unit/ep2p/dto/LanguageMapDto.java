package vn.com.unit.ep2p.dto;

/**
 * 
 *
 * @version 01-00
 * @since 01-00
 * @author hiennt
 */
public class LanguageMapDto {
	
	private String langCode;
	
	private String nameValue;
	
	private String passiveNameValue;

	/**
	 * @return the langCode
	 */
	public String getLangCode() {
		return langCode;
	}

	/**
	 * @param langCode the langCode to set
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * @return the nameValue
	 */
	public String getNameValue() {
		return nameValue;
	}

	/**
	 * @param nameValue the nameValue to set
	 */
	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	/**
	 * @return the passiveNameValue
	 */
	public String getPassiveNameValue() {
		return passiveNameValue;
	}

	/**
	 * @param passiveNameValue the passiveNameValue to set
	 */
	public void setPassiveNameValue(String passiveNameValue) {
		this.passiveNameValue = passiveNameValue;
	}

}
