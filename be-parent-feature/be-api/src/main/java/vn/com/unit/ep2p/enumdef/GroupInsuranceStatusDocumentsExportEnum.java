package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceStatusDocumentsExportEnum {
	NO("0"),
	MANAGER("1"),
	TOTALPROPOSAL("2");

	private String value;

	private GroupInsuranceStatusDocumentsExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
