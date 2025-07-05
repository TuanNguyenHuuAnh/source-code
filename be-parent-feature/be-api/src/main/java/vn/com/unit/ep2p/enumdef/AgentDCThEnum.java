package vn.com.unit.ep2p.enumdef;

public enum AgentDCThEnum {
	NO("0")
	, AREA("1")
	, BDAH("2")
	, REGION("3")
	, BDRH("4")
	, OFFICE("5")
	, BDOH("6")
	, GAD("7")
	, BRANCH("8")
	, UNIT("9")
	, TVTC("10")
	, disciplinaryreasons("11")
	, EFFECTIVEDATE("12")
	, EXPIRATIONDATE("13")
	, USERSUGGESTVIOLATION("14")
	, DECISIONNUMBER("15");
	private String value;
	
	private AgentDCThEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
