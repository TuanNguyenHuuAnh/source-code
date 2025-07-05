package vn.com.unit.ep2p.enumdef;

public enum PrersonalInsuranceRefuseExportEnum {
	NO("0"),
	docno("1"),
	policyno("2"),
	insurancebuyer("3"),
	docreceiveddate("4");

	private String value;
	
	private PrersonalInsuranceRefuseExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
