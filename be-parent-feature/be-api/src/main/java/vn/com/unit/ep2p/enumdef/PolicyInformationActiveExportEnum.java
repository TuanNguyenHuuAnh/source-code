package vn.com.unit.ep2p.enumdef;

public enum PolicyInformationActiveExportEnum {
	NO("0"),
	customerno("1"),
	customerName("2"),//BMBH
	insurancecontract("3"),
	insuranceofcustomermain("4"),
	polagtshrpct("5"),
	periodicfeepayment("6"),
	effectivedate("7"),
	datedue("8"),
	ASSIGNMENTFORMNAME("9"),
	assignmentdate("10");
	
	private String value;
	
	private PolicyInformationActiveExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
