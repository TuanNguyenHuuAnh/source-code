package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListPolicyDelivery {
    no("0"),
    partnerCode("1"),
    policyNo("2"),
    po("3"),
    issuedDate("4"),
    sentDate("5"),
    packageNo("6"),
    emsDeliveryDate("7"),
    note("8"),
    region("9"),
	branchCode("10"),
	branchName("11"),
	uoCode("12"),
	uoName("13"),
	agentCode("14"),
	agentName("15"),
	agentType("16"),
	ilAgentCode("17"),
	ilAgentName("18"),
	ilAgentType("19"),
	smAgentCode("20"),
	smAgentName("21"),
	smAgentType("22"),
	branchDlvn("23"),
	zdName("24"),
	regionDlvn("25"),
	rdName("26"),
	areaDlvn("27"),
	adName("28");

    private String value;

    private EnumExportListPolicyDelivery(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    }
