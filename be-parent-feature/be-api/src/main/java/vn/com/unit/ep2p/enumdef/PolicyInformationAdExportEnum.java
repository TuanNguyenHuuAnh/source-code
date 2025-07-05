package vn.com.unit.ep2p.enumdef;

public enum PolicyInformationAdExportEnum {
	NO("0"),
	customerno("1"),
	insurancecontract("2"),
	insuranceofcustomermain("3"),
	productmainname("4"),
	policystatus("5"),
	autodebit("6"),
	effectivedate("7"),
	datedue("8"),
	expirationdate("9"),
	totalPremiumPaid("10");

	private String value;
	
	private PolicyInformationAdExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
