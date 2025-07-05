package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceDocumentsExportActivesUMEnum {
	NO("0"),
	AGENT("1"),
	TOTALPROPOSAL("2");
	;

	private String value;

	private GroupInsuranceDocumentsExportActivesUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
