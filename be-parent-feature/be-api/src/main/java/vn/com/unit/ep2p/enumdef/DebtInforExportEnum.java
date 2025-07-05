package vn.com.unit.ep2p.enumdef;

public enum DebtInforExportEnum {
	NO("0")
	, allowancetype("1")
	, ARISINGDATE("2")
	, DEBTMONEYDTO("3")
	, EXPLAIN("4");

	private String value;
	
	private DebtInforExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
