package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("deprecation")
public class CurrencyLanguageDto {

	/** id */
    private Long id;

    /** currency id */
    private Long currencyId;

    /** languageCode */
    @NotEmpty
    @Size(min = 1, max = 255)
    private String languageCode;

    /** name */
    @NotEmpty
    @Size(min = 1, max = 255)
    private String title;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
