package vn.com.unit.cms.admin.all.dto;

public class NewsTypeCategoryDto {
	private Long id;
	private String name;
	private int typeOfLibrary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeOfLibrary() {
		return typeOfLibrary;
	}

	public void setTypeOfLibrary(int typeOfLibrary) {
		this.typeOfLibrary = typeOfLibrary;
	}
}
