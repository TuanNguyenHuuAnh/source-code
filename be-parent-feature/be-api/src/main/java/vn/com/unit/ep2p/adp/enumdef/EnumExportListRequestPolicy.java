package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListRequestPolicy {
	NO("0"),
	partner("1"),
	policyNo("2"),
	insuranceBuyer("3"),
	polStatus("4"),
	issuedDate("5"),
	requestAdjustment("6"),
	requestDate("7"),
	status("8"),
	addInformation("9"),
	addExpirationDate("10"),
	startDate("11"),
	initalAmount("12"),
	adjustmentAmount("13"),
	region("14"),
	branchCode("15"),
	branchName("16"),
	uoCode("17"),
	uoName("18"),
	agentCode("19"),
	agentName("20"),
	agentType("21"),
	ilAgentCode("22"),
	ilAgentName("23"),
	ilAgentType("24"),
	smAgentCode("25"),
	smAgentName("26"),
	smAgentType("27"),
	branchDlvn("28"),
	zdName("29"),
	regionDlvn("30"),
	rdName("31"),
	areaDlvn("32"),
	adName("33");
	;

	private String value;
	
	private EnumExportListRequestPolicy(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
