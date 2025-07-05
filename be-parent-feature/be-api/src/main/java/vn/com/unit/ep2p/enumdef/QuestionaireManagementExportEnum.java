package vn.com.unit.ep2p.enumdef;

public enum QuestionaireManagementExportEnum {
	no("0"),
	typeQuestion("1"),
	applyForPosition("2"),
	content("3"),
	orderOnForm("4"),
	statusItem("5"),
	updatedBy("6"),
	updatedDate("7"),
	;
	
	private String value;

	private QuestionaireManagementExportEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}
