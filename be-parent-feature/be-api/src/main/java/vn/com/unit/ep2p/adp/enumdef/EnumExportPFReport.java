package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportPFReport {
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
	tp("11"),
	ep("12"),
	tpep("13"),
	tpep2("14"),
	polStatus("15"),
	polBillMode("16"),
	referralCode("17"),	
	referralBankCode("18"),
	referralName("19"),
	packageName("20"),
	region("21"),
	branchCode("22"),
	branchName("23"),
	uoCode("24"),
	uoName("25"),
	agentCode("26"),
	agentName("27"),
	agentType("28"), 
	ilAgentCode("29"),
	ilAgentName("30"),
	ilAgentType("31"),
	smAgentCode("32"),
	smAgentName("33"),
	smAgentType("34"),
	branchDlvn("35"),
	zdName("36"),
	regionDlvn("37"),
	rdName("38"),
	areaDlvn("39"),
	adName("40");

    private String value;

    private EnumExportPFReport(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
