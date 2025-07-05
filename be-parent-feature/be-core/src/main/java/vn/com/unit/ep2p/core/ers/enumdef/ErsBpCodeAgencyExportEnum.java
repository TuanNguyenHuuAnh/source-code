package vn.com.unit.ep2p.core.ers.enumdef;

public enum ErsBpCodeAgencyExportEnum {
	NO("0"),
	
	BPCODE("1"), // bpCode
	
	APPLYFORPOSITION("2"),// chức vụ dự kiến 
	
	CLASSCODE("3"), // lớp học
	CANDIDATENAME("4"),
	GENDERNAME("5"),
	MARITALSTATUS("6"),
	DOB("7"),
	NATIONALITY("8"),
	OTHERIDNO("9"),
	IDTYPE("10"),
	IDNO("11"),

    MESSAGEERROR("45")
    ;

	private String value;

    private ErsBpCodeAgencyExportEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
