package vn.com.unit.ep2p.dto;


public class WarehouseDto {

	
	private Long id;
	
	
	private String code;
	private String name;
	private String type;
	private Long warehousesParentId;
	private String assetLocationCode;
	private String assetLocationName;
	
	private String address;
	private String keeperUsername;
	private String keeperFullname;
	

	private String phone;
	private String note;
	private int activeFlag =1;
	private String status;
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	private String companyCode;

	private int no;
	
	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}





	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Long getWarehousesParentId() {
		return warehousesParentId;
	}


	public void setWarehousesParentId(Long warehousesParentId) {
		this.warehousesParentId = warehousesParentId;
	}


	public String getAssetLocationCode() {
		return assetLocationCode;
	}


	public void setAssetLocationCode(String assetLocationCode) {
		this.assetLocationCode = assetLocationCode;
	}


	public String getAssetLocationName() {
		return assetLocationName;
	}


	public void setAssetLocationName(String assetLocationName) {
		this.assetLocationName = assetLocationName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getKeeperUsername() {
		return keeperUsername;
	}


	public void setKeeperUsername(String keeperUsername) {
		this.keeperUsername = keeperUsername;
	}


	public String getKeeperFullname() {
		return keeperFullname;
	}


	public void setKeeperFullname(String keeperFullname) {
		this.keeperFullname = keeperFullname;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public int getActiveFlag() {
		return activeFlag;
	}


	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}


	public String getCompanyCode() {
		return companyCode;
	}


	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
	
}
