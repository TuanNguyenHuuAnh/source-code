package vn.com.unit.ep2p.enumdef;

public enum LetterResultGAEnum {
	NO("0")
	, MEMOCODE("1")
	, CONTRACTNO("2")
	, RESULT("3")
	, ADVANCE("4")
	, BONUS("5")
	, CLAWBACK("6")
	, EXPIREDDATE("7");

	private String value;
	
	private LetterResultGAEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
