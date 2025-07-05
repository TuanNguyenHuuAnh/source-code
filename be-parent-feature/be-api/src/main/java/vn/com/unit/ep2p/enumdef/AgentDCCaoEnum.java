package vn.com.unit.ep2p.enumdef;

public enum AgentDCCaoEnum {
	NO("0")
	, TERRITORY("1")
	, BDTH("2")
	, AREA("3")
	, BDAH("4")
	, REGION("5")
	, BDRH("6")
	, OFFICE("7")
	, BDOH("8")
	, GAD("9")
	, BRANCH("10")
	, UNIT("11")
	, TVTC("12")
	, disciplinaryreasons("13")
	, EFFECTIVEDATE("14")
	, EXPIRATIONDATE("15")
	, USERSUGGESTVIOLATION("16")
	, DECISIONNUMBER("17");
	private String value;
	
	private AgentDCCaoEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
