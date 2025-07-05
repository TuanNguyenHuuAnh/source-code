package vn.com.unit.ep2p.core.ers.enumdef;

public enum CheckAvicadBancasExportEnum {
	idNo("0"),
	idCard("1"),
	taxCode("2"),
	candidateName("3"),
	dob("4"),
	genderName("5"),
	;
	
	private String value;

	private CheckAvicadBancasExportEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}
