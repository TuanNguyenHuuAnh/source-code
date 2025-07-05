package vn.com.unit.ep2p.enumdef;

public enum ListAgentOhEnum{
    GANAME("0")
    , BRANCHNAME("1")
    , TOTAL("2")
    , TOTALHEADOFDEPARTMENT("3")
    , TOTALMANAGER("4")
    , TOTALTVTC("5")
    , TOTALTVTCSA("6");

	private String value;
	
	private ListAgentOhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
