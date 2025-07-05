package vn.com.unit.cms.admin.all.enumdef.exports;

public enum EmulateResultExportEnum {

	  NO("0")
	, MESSAGEERROR("1")
	, MEMONO("2")
	, AGENTCODE("3")
	, REPORTINGTOCODE("4")
	, BMCODE("5")
	, GADCODE("6")
	, GACODE("7")
	, BDOHCODE("8")
	, BDRHCODE("9")
	, BDAHCODE("10")
	, BDTHCDE("11")
	, POLICYNO("12")
	, APPLIEDDATE("13")
	, ISSUEDDATE("14")
	, RESULT("15")
	, ADVANCE("16")
	, BONUS("17")
	, CLAWBACK("18")
	, NOTE("19");
    
	private String value;

	private EmulateResultExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
