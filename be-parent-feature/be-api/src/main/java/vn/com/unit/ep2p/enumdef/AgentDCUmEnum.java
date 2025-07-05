package vn.com.unit.ep2p.enumdef;

public enum AgentDCUmEnum {
	NO("0")
	, TVTC("1")
	, disciplinaryreasons("2")
	, EFFECTIVEDATE("3")
	, EXPIRATIONDATE("4")
	, USERSUGGESTVIOLATION("5")
	, DECISIONNUMBER("6");
	private String value;
	
	private AgentDCUmEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
