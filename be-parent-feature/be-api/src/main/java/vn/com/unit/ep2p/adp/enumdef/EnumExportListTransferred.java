package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListTransferred {
	no("0"),
    partnerCode("1"),
    policyNo("2"),
    po("3"),
    issuedDate("4"),
    sentDate("5"),
    packageNo("6"),
    emsDeliveryDate("7"),
    unitConfirmedDate("8"),
    fCReceivedDate("9"),
    ackReceivedDate("10"),
    policyReceiveDate("11"),
    status("12"),
    ackComment("13"),
    ackNote("14"),
    region("15"),
	branchCode("16"),
	branchName("17"),
	uoCode("18"),
	uoName("19"),
	agentCode("20"),
	agentName("21"),
	agentType("22"),
	ilAgentCode("23"),
	ilAgentName("24"),
	ilAgentType("25"),
	smAgentCode("26"),
	smAgentName("27"),
	smAgentType("28"),
	branchDlvn("29"),
	zdName("30"),
	regionDlvn("31"),
	rdName("32"),
	areaDlvn("33"),
	adName("34");

    private String value;

    private EnumExportListTransferred(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    }
