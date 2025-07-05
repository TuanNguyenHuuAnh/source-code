package vn.com.unit.ep2p.enumdef;

public enum EmulateChallengePersonalExportREnum {
	  NO("0")
	, MEMONO("1")
	, AGENTCODE("2")
	, AGENTNAME("3")
	, AGENTTYPE("4")
	, REPORTINGTOCODE("5")
	, REPORTINGTONAME("6")
	, REPORTINGTOTYPE("7")
	, BMCODE("8")
	, BMNAME("9")
	, BMTYPE("10")
	, GADCODE("11")
	, GADNAME("12")
	, GACODE("13")
	, OFFICE("14")
	, BDOHCODE("15")
	, TERRYTORY("16")
	, BDRHCODE("17")
	, AREA("18")
	, BDAHCODE("19")
	, REGION("20")
	, BDTHCDE("21")
	, POLICYNO("22")
	, APPLIEDDATE("23")
	, ISSUEDDATE("24")
	, RESULT("25")
	, ADVANCE("26")
	, BONUS("27")
	, CLAWBACK("28")
	, NOTE("29");

	private String value;

	private EmulateChallengePersonalExportREnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
