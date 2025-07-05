package vn.com.unit.ep2p.enumdef;

public enum AgentDCBmEnum {
	NO("0")
	, UNIT("1")
	, TVTC("2")
	, disciplinaryreasons("3")
	, EFFECTIVEDATE("4")
	, EXPIRATIONDATE("5")
	, USERSUGGESTVIOLATION("6")
	, DECISIONNUMBER("7");
	private String value;
	
	private AgentDCBmEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
