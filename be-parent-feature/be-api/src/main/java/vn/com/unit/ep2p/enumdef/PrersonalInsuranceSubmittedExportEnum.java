package vn.com.unit.ep2p.enumdef;

public enum PrersonalInsuranceSubmittedExportEnum {
	NO("0"),
	docno("1"),
	policyno("2"),
	insurancebuyer("3"),
	docreceiveddate("4"),
	status("5")
	;

	private String value;
	
	private PrersonalInsuranceSubmittedExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
