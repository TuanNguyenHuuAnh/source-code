package vn.com.unit.ep2p.enumdef;

public enum AgentDisciplinaryEnum {
	NO("0")
	, BDOH("1")
	, TVTC("2")
	//, DISCIPLINETYPE("3")//disciplineType 
	, disciplinaryreasons("3")
	, EFFECTIVEDATE("4")
	, EXPIRATIONDATE("5")
	, USERSUGGESTVIOLATION("6")
	, DECISIONNUMBER("7");
	private String value;
	
	private AgentDisciplinaryEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
