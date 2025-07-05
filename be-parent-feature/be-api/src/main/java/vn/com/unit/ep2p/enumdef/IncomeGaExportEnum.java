package vn.com.unit.ep2p.enumdef;

public enum IncomeGaExportEnum {
		MAINNAME("0")
	, SUBNAME("1")
	, ITEMNAME("2")
	, AMOUNT("3");

	private String value;

	private IncomeGaExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
