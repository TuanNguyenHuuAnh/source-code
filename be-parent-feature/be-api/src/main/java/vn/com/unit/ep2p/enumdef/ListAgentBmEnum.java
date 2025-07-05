package vn.com.unit.ep2p.enumdef;

public enum ListAgentBmEnum{
	NO("0")
    , MANAGERAGENTNAME("1")
    , AGENTALL("2")
    , GENDER("3")
    , BIRTHDAY("4")
    , AGENTSTATUS("5")
    , MOBILE("6")
    , APPOINTEDDATE("7")
    , TERMINATEDDATE("8");

	private String value;
	
	private ListAgentBmEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
