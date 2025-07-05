package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportUpDownAgentInfo {
    no("0"),
    agentCode("1"),
    agentName("2"),
    agentType("3"),
    agentStatus("4"),
    partnerCode("5");
	
    private String value;

    private EnumExportUpDownAgentInfo(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    }
