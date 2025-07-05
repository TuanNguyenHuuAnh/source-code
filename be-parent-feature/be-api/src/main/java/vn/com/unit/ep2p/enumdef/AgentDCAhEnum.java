package vn.com.unit.ep2p.enumdef;

public enum AgentDCAhEnum {
	NO("0")
	, REGION("1")
	, BDRH("2")
	, OFFICE("3")
	, BDOH("4")
	, GAD("5")
	, BRANCH("6")
	, UNIT("7")
	, TVTC("8")
	, disciplinaryreasons("9")
	, EFFECTIVEDATE("10")
	, EXPIRATIONDATE("11")
	, USERSUGGESTVIOLATION("12")
	, DECISIONNUMBER("13");
	private String value;
	
	private AgentDCAhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
