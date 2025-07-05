package vn.com.unit.ep2p.enumdef;

public enum IntroduceLetterExportEnum {
	NO("0")
	, SOHDBH("1")
	, INSURANCEBYNAME("2")
	, FORMOFDISTRIBUTION("3")
	, EFFECTIVEDATE("4");

	private String value;
	
	private IntroduceLetterExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
