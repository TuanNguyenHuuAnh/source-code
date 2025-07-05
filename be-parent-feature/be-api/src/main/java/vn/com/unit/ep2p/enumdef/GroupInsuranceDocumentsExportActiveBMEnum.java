package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceDocumentsExportActiveBMEnum {
    NO("0"),
    MANAGER("1"),
    AGENT("2"),
    TOTALPROPOSAL("3");

	private String value;

	private GroupInsuranceDocumentsExportActiveBMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
