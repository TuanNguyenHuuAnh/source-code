package vn.com.unit.ep2p.enumdef;

public enum PrersonalInsuranceReleaseExportEnum {
	NO("0"),
	docno("1"),
	policyno("2"),
	insurancebuyer("3"),
	signDate("4"),
	docreceiveddate("5"),
	status("6"),
	ackSendDate("7"),
	releaseDate("8"),
	ackStatus("9"),
	ackCusSignDate("10"),
	ackReceivedDate("11"),
	ackComment("12");

	private String value;
	
	private PrersonalInsuranceReleaseExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
