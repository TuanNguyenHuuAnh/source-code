
package vn.com.unit.ep2p.core.ers.enumdef;


public enum AvicadImportEnum {
	IDNO("0"),
	IDCARD("1"),
	TAXCODE("2"),
	ORGANIZATIONNAME("3"),
	AGENTNAME("4"),
	DOB("5"),
	GENDER("6"),
	AGENTCODE("7"),
	LIFECER("8"),
	INSURER("9"),
	AGTSTS("10"),
	RECENTCONT("11"),
	RECENTTER("12"),
	MISDAT("13"),
	BLKCODE("14"),
	BLACKLIST("15"),
	ISCOMPANY("16"),
	ISOTHERCOMPANY("17"),
	RESULT("18");
	
	private String value;

    private AvicadImportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
