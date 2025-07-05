package vn.com.unit.ep2p.enumdef;

public enum CustomerInteractionHistoryExportEnum {
	NO("0"),
	CUSTOMERTYPE("1"),
	INSUREDBUYER("2"),
	INSUREDNAME("3"),
	GENDER("4"),
	PRODUCTNAME("5"),
	CREATEDDT("6"),
	PROPOSALNO("7"),
	POLICYNO("8"),
	STATUS("9"),
	POLINFCDT("10"),
	POLSTATCHNGDT("11"),
	PLANNAME("12"),
	INSUREDSUMASSURED("13"),
	PAYMENTFREQ("14"),
	PREMIUMAMOUNT("15"),
	POLICYTERM("16"),
	LASTVISITEDSTEP("17");
	
	private String value;
	
	private CustomerInteractionHistoryExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
