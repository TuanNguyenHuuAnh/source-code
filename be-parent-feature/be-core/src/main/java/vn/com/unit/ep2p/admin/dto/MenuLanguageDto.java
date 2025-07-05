package vn.com.unit.ep2p.admin.dto;

public class MenuLanguageDto {

	/**menuLanguageId*/
	private Long menuLanguageId;
	
	/**languageId*/
	private Long languageId;
	
	/**languageCode*/
	private String languageCode;
	
	/**languageName*/
	private String languageName;
	
	/**alias*/
	private String alias;

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Long getMenuLanguageId() {
		return menuLanguageId;
	}

	public void setMenuLanguageId(Long menuLanguageId) {
		this.menuLanguageId = menuLanguageId;
	}
}
