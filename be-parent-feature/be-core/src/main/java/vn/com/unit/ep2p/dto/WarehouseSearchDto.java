package vn.com.unit.ep2p.dto;

public class WarehouseSearchDto {

	
	private Long id;
	
	private String name;
	
	private String officeLocation;
	
	private String warehouseKeeper;

	private String inputSearch;
	
	private String languageCode;
	
	private String propertyOf;
	
	private String url;
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

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getWarehouseKeeper() {
		return warehouseKeeper;
	}

	public void setWarehouseKeeper(String warehouseKeeper) {
		this.warehouseKeeper = warehouseKeeper;
	}

	

	public String getInputSearch() {
		return inputSearch;
	}

	public void setInputSearch(String inputSearch) {
		this.inputSearch = inputSearch;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getPropertyOf() {
		return propertyOf;
	}

	public void setPropertyOf(String propertyOf) {
		this.propertyOf = propertyOf;
	}


	
}
