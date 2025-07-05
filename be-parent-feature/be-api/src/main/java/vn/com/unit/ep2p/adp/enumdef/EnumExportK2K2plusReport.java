package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportK2K2plusReport {
	NO("0"),
	partner("1"),
	policyNo("2"),
	poName("3"),
	issueDate("4"),
	effDate("5"),
	epp("6"),
	app("7"),
	k2Rate("8"),
	eppModif("9"),
	appModif("10"),
	polStatus("11"),
	polBillMode("12"),
	referralCode("13"),	
	referralBankCode("14"),
	referralName("15"),
	packageName("16"),
	region("17"),
	branchCode("18"),
	branchName("19"),
	uoCode("20"),
	uoName("21"),
	agentCode("22"),
	agentName("23"),
	agentType("24"), 
	ilAgentCode("25"),
	ilAgentName("26"),
	ilAgentType("27"),
	smAgentCode("28"),
	smAgentName("29"),
	smAgentType("30"),
	branchDlvn("31"),	
	zdName("32"),
	regionDlvn("33"),
	rdName("34"),
	areaDlvn("35"),
	adName("36");

    private String value;

    private EnumExportK2K2plusReport(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
