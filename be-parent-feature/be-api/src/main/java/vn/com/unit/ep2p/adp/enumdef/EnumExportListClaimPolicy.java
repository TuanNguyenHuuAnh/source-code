package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListClaimPolicy {
	NO("0"),
	partner("1"),
	policyNo("2"),
	policyOwner("3"),
	insuredPerson("4"),
	claimNo("5"),
	scanDate("6"),
    claimtype("7"),
    statusvn("8"),
    expiredDate("9"),
    approveDate("10"),
    approveAmt("11"),
    remark("12"),
	region("13"),
	branchCode("14"),
	branchName("15"),
	uoCode("16"),
	uoName("17"),
	agentCode("18"),
	agentName("19"),
	agentType("20"),
	ilAgentCode("21"),
	ilAgentName("22"),
	ilAgentType("23"),
	smAgentCode("24"),
	smAgentName("25"),
	smAgentType("26"),
	branchDlvn("27"),
	zdName("28"),
	regionDlvn("29"),
	rdName("30"),
	areaDlvn("31"),
	adName("32");
	;

	private String value;
	
	private EnumExportListClaimPolicy(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
