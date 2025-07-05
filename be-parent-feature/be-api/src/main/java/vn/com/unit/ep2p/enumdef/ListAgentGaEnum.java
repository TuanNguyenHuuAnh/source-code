package vn.com.unit.ep2p.enumdef;

public enum ListAgentGaEnum{
    BRANCHNAME("0")
    , UNITNAME("1")
    , TOTAL("2")
    , TOTALTVTC("3")
    , TOTALTVTCSA("4");

	private String value;
	
	private ListAgentGaEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
