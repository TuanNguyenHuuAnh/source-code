package vn.com.unit.ep2p.enumdef;

public enum PolicyInformationExportEnum {
	NO("0"),
	customerno("1"),
	insurancecontract("2"),
	insuranceofcustomermain("3"),
	productmainname("4"),
	policystatus("5"),
	polagtshrpct("6"),
	effectivedate("7"),
	datedue("8"),
	expirationdate("9");

	private String value;
	
	private PolicyInformationExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
