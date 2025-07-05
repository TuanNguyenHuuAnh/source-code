
package vn.com.unit.cms.admin.all.dto;

/**
 * InterestRateLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
public class InterestRateLanguageDto {

    /** id */
    private Long id;

    /** interestRateId */
    private Long interestRateId;

    /** languageCode */
    private String languageCode;

    /** description */
    private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInterestRateId() {
		return interestRateId;
	}

	public void setInterestRateId(Long interestRateId) {
		this.interestRateId = interestRateId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
