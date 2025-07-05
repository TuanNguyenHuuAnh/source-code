package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListPrersonalPolicy {
	NO("0"),
	partner("1"),
	uoCode("2"),
	uoName("3"),
	agentCode("4"),
	agentName("5"),
	agentType("6"),
	policyNo("7"),
	docNo("8"),
	policyOwner("9"),
	docReceivedDate("10"),
	polIssueEff("11"),
	issueDate("12"),
	inActiveDate("13"),
	premiumDueDate("14"),
	polCeasDate("15"),
	faceAmount("16"),
	tp("17"),
	ep("18"),
	ipCs("19"),
	ip("20"),
	polStatus("21"),
	billingMethod("22"),
	pca("23"),
	modalPremium("24"),
	apl("25"),
	amount("26"),
	paymentPeriodDate("27"),
	feeNextPeriod("28"),
	hangingFee("29"),
	note("30"),
	referralCode("31"),
	referralBankCode("32"),
	referralName("33"),
	PackageName("34"),
	region("35"),
	branchCode("36"),
	branchName("37"),
	ilAgentCode("38"),
	ilAgentName("39"),
	ilAgentType("40"),
	smAgentCode("41"),
	smAgentName("42"),
	smAgentType("43"),
	branchDlvn("44"),
	zdName("45"),
	regionDlvn("46"),
	rdName("47"),
	areaDlvn("48"),
	adName("49");
	;

	private String value;
	
	private EnumExportListPrersonalPolicy(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
