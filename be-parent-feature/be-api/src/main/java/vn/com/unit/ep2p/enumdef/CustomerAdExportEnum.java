package vn.com.unit.ep2p.enumdef;

public enum CustomerAdExportEnum {
	NO("0"),
	CUSTOMERNO("1"),
	CUSTOMERNAME("2"),
	PHONENUMBER("3"),
	CUSTOMERTYPE("4"),
	TOTALPOLICYACTIVE("5"),
	TOTALPOLICYINACTIVE("6"),
	DATEOFBIRTH("7"),
	REWARDPOINTS("8"),
	GENDER("9"),
	maritalstatus("10"),
	email("11"),
	homephone("12"),
	companyphone("13"),
	ADDRESS("14"),
	permanentaddress("15"),
	dcActivate("16"),
	autoDebit("17");

	private String value;
	
	private CustomerAdExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
