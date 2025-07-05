package vn.com.unit.ep2p.enumdef;

public enum GroupInsuranceDocumentsExportEnum {
	PAREN("0"),
	CHILD("1"),
	SUMOFDOCUMENTSUBMITTED("2"),
	SUMOFDOCUMENTADDITION("3"),
	SUMOFDOCUMENTRELEASED("4"),
	SUMOFDOCUMENTREJECTED("5"),
	SUMOFPOLICYRELEASED("6"),
	SUMOFPOLICYCANCELED("7");



	private String value;

	private GroupInsuranceDocumentsExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
