package vn.com.unit.ep2p.enumdef;

public enum CustomerExportEnum {
	NO("0"),
	CUSTOMERNO("1"),
	CUSTOMERNAME("2"),
	NICKNAME("3"),
	PHONENUMBER("4"),
	CUSTOMERTYPE("5"),
	TOTALPOLICYACTIVE("6"),
	TOTALPOLICYINACTIVE("7"),
	DATEOFBIRTH("8"),
	REWARDPOINTS("9"),
	GENDER("10"),
	maritalstatus("11"),
	email("12"),
	homephone("13"),
	companyphone("14"),
	ADDRESS("15"),
	permanentaddress("16"),
	dcActivate("17");
	private String value;
	
	private CustomerExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
