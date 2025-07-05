package vn.com.unit.ep2p.enumdef;

public enum CustomerPotentialExportEnum {
	NO("0"),
	CUSTOMERTYPE("1"),
	PHONENUMBER("2"),
	CUSTOMERNAME("3"),
	NICKNAME("4"),
	GENDER("5"),
	BIRTHDATE("6"),
	CREATEDDATE("7"),
	REACHDATE("8"),
	CLIENTSTATUS("9"),
	MARITALSTATUS("10"),
	NATIONALITY("11"),
	EMAIL("12"),
	ADDRESS("13"),
	NOTES("14"),
	TOTALPROPOSALSUBMITED("15"),
	TOTALPROPOSALREJECT("16"),
	TOTALPOLICYACTIVE("17"),
	TOTALPOLICYINACTIVE("18");

	private String value;
	
	private CustomerPotentialExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
