package vn.com.unit.ep2p.enumdef;

public enum ListAgentAhEnum{
	BDOHNAME("0")
    , GANAME("1")
    , GADNAME("2")
    , TOTAL("3")
    , TOTALHEADOFDEPARTMENT("4")
    , TOTALMANAGER("5")
    , TOTALTVTC("6")
    , TOTALTVTCSA("7");


	private String value;
	
	private ListAgentAhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
