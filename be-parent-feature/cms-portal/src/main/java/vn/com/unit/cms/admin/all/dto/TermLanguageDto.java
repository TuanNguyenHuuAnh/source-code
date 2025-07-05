package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("deprecation")
public class TermLanguageDto {

	/** id */
	private Long id;

	/** termId */
	private Long termId;

	/** languageCode */
	@Size(min = 1, max = 30)
	@NotEmpty
	private String languageCode;

	/** title */
	@NotEmpty
	@Size(min = 1, max = 255)
	private String title;

	/** get Id */
	public Long getId() {
		return id;
	}

	/** set ID */
	public void setId(Long id) {
		this.id = id;
	}

	/** get term ID */
	public Long getTermId() {
		return termId;
	}

	/** set term ID */
	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/** get title */
	public String getTitle() {
		return title;
	}

	/** set title */
	public void setTitle(String title) {
		this.title = title;
	}

}
