package vn.com.unit.ep2p.enumdef;

public enum TemporaryCandidateEnum {
	NO("0")
	, OFFICENAMEREG("1")
	, FULLNAME("2")
	, EMAIL("3")
	, PHONE("4")
	, REGISTERDATE("5")
	, CONTACTSTRING("6");
	private String value;
	
	private TemporaryCandidateEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
