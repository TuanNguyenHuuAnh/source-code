package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceContractExportEnum {
	PROPOSALCODE("0"),
	INSURANCEBUYER("1"),
	EFFECTIVEDATE("2");

	private String value;

	private GroupInsuranceContractExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
