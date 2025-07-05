package vn.com.unit.cms.admin.all.enumdef.exports;

public enum EmulateExportDetailEnum {
	     NO("0")
		, MEMONO("1")
		, AGENTCODE("2")
		, REPORTINGTOCODE("3")
		, BMCODE("4")
		, GADCODE("5")
		, GACODE("6")
		, BDOHCODE("7")
		, BDRHCODE("8")
		, BDAHCODE("9")
		, BDTHCDE("10")
		, POLICYNO("11")
		, APPLIEDDATE("12")
		, ISSUEDDATE("13")
		, RESULT("14")
		, ADVANCE("15")
		, BONUS("16")
		, CLAWBACK("17");
    private String value;

    private EmulateExportDetailEnum(String value){
        this.value = value;
    }

    public String toString (){
        return value;
    }
    }
