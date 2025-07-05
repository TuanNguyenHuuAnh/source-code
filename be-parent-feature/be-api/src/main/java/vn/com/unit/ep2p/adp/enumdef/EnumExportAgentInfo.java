package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportAgentInfo {
    no("0"),
    agentCode("1"),
    agentName("2"),
    agentType("3"),
    partnerCode("4"),
    operationalModel("5"),
    unitCode("6"),
    unitName("7");
	
    private String value;

    private EnumExportAgentInfo(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    }
