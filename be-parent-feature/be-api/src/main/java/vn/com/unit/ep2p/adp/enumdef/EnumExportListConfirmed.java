package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListConfirmed {    
    no("0"),
    partnerCode("1"),
    policyNo("2"),
    po("3"),
    issuedDate("4"),
    sentDate("5"),
    packageNo("6"),
    emsDeliveryDate("7"),
    unitConfirmedDate("8"),
    note("9"),
    region("10"),
	branchCode("11"),
	branchName("12"),
	uoCode("13"),
	uoName("14"),
	agentCode("15"),
	agentName("16"),
	agentType("17"),
	ilAgentCode("18"),
	ilAgentName("19"),
	ilAgentType("20"),
	smAgentCode("21"),
	smAgentName("22"),
	smAgentType("23"),
	branchDlvn("24"),
	zdName("25"),
	regionDlvn("26"),
	rdName("27"),
	areaDlvn("28"),
	adName("29");

    private String value;

    private EnumExportListConfirmed(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    }
