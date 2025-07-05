package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListPrersonalInsurance {
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
	status("21"),
	polStatus("22"),
	billingMethod("23"),
	pca("24"),
	modalPremium("25"),
	apl("26"),
	amount("27"),
	paymentPeriodDate("28"),
	feeNextPeriod("29"),
	hangingFee("30"),
	note("31"),
	referralCode("32"),
	referralBankCode("33"),
	referralName("34"),
	packageName("35"),
	region("36"),
	branchCode("37"),
	branchName("38"),
	ilAgentCode("39"),
	ilAgentName("40"),
	ilAgentType("41"),
	smAgentCode("42"),
	smAgentName("43"),
	smAgentType("44"),
	branchDlvn("45"),
	zdName("46"),
	regionDlvn("47"),
	rdName("48"),
	areaDlvn("49"),
	adName("50");
	;

	private String value;
	
	private EnumExportListPrersonalInsurance(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
