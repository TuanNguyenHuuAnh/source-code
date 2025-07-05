package vn.com.unit.ep2p.enumdef;

public enum ApplicantAgencyListExportListCheckAvicadEnum {
	idNo("0"),
//	idCard("1"), => cccd => using all idnum
	taxCode("2"),
	candidateName("3"),
	dob("4"),
	genderName("5"),
	;
	
	private String value;

	private ApplicantAgencyListExportListCheckAvicadEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}
