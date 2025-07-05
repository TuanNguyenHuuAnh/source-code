package vn.com.unit.ep2p.core.ers.enumdef;

public enum ErsCcBancasExportEnum {

	NO("0"), 
	BPCODE("1"),
	CCCODE("2"),
//	COLUMN1("3"),
	EFFECTIVEDATE("4"),
	FULLNAME("5"),
//	COLUMN2("6"),
//	COLUMN3("7"),
	POSITION("8"),	
//	COLUMN4("9"),
	IDNUMBER("10"),
//	COLUMN5("11"),
//	COLUMN6("12"),
//	COLUMN7("13"),
//	COLUMN8("14"),
//	COLUMN9("15"),
//	COLUMN10("16"),
//	COLUMN11("17"),
//	COLUMN12("18"),
//	COLUMN13("19"),
//	COLUMN14("20"),
//	COLUMN15("21"),
//	COLUMN16("22"),
//	COLUMN17("23"),
//	COLUMN18("24"),
//	COLUMN19("25"),
//	COLUMN20("26"),
    MESSAGEERROR("27"),
    ;
	
	private String value;

    private ErsCcBancasExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
