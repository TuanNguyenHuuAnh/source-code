package vn.com.unit.ep2p.enumdef;

public enum ListAgentUmEnum{
	NO("0")
    , AGENTALL("1")
    , GENDER("2")
    , BIRTHDAY("3")
    , AGENTSTATUS("4")
    , MOBILE("5")
    , APPOINTEDDATE("6")
    , TERMINATEDDATE("7");
    
	private String value;
	
	private ListAgentUmEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
