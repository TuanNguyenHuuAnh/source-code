package vn.com.unit.ep2p.enumdef;

public enum PolicyInformationInactiveExportEnum {
	NO("0"),
	customerno("1"),
    customerName("2"),//BMBH
	insurancecontract("3"),
	insuranceofcustomermain("4"),
	polagtshrpct("5"),
	periodicfeepayment("6"),
	effectivedate("7"),
	expirationdate("8"),
	estimatedrecurringfee("9"),
	recurringbasicfee("10"),
	hangingfee("11"),
	ASSIGNMENTFORMNAME("12"),
	assignmentdate("13");
	
	private String value;
	
	private PolicyInformationInactiveExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
