package vn.com.unit.cms.admin.all.dto;

public class InvendorCategoryLanguageDto {
	private Long id;
	private String label;
    private Long categoryId;
    private String languageCode;
    private String languageDispName;
    private String description;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getLanguageDispName() {
		return languageDispName;
	}
	public void setLanguageDispName(String languageDispName) {
		this.languageDispName = languageDispName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
