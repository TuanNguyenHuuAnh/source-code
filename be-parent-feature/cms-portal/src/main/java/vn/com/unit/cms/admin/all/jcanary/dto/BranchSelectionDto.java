package vn.com.unit.cms.admin.all.jcanary.dto;

public class BranchSelectionDto {
	private Long id;
	private String branchName;
	private String branchAddress;
	private String cityName;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBranchAddress() {
		return branchAddress;
	}
	
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
