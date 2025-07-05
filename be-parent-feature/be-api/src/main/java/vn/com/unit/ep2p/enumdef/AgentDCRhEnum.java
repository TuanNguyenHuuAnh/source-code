package vn.com.unit.ep2p.enumdef;

public enum AgentDCRhEnum {
	NO("0")
	, OFFICE("1")
	, BDOH("2")
	, GAD("3")
	, BRANCH("4")
	, UNIT("5")
	, TVTC("6")
	, disciplinaryreasons("7")
	, EFFECTIVEDATE("8")
	, EXPIRATIONDATE("9")
	, USERSUGGESTVIOLATION("10")
	, DECISIONNUMBER("11");
	private String value;
	
	private AgentDCRhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
