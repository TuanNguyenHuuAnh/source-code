package vn.com.unit.ep2p.core.ers.enumdef;

public enum CheckAvicadExportEnum {
	IDNO("0"),
//	idCard("1"), => cccd => using all idnum
	TAXCODE("2"),
	CANDIDATENAME("3"),
	DOB("4"),
	GENDER("5"),
//	genderName("5"),
	;
	
	private String value;

	private CheckAvicadExportEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}
