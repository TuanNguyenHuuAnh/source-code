package vn.com.unit.ep2p.enumdef;

public enum ApplicantAgencyListExportEnum {
	no("0"),
	applyForPosition("1"),
	idNo("2"),
	candidateName("3"),
	dob("4"),
	recruiterCodeOrIdCard("5"),
	recruiterName("6"),
	recruiterPosition("7"),
	adCode("8"),
	adName("9"),
	adPosition("10"),
	classCode("11"),
	statusProcess("12"),
	formStatus("13"),
	avicadStatus("14"),
//	checkAvicadStatus("15"),
//	createdBy("15"),
	fullnameCreatedBy("15"),
	createdDate("16"),
	;
	
	private String value;

	private ApplicantAgencyListExportEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}
