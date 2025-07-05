package vn.com.unit.ep2p.enumdef;

public enum ExamScheduleCandidateEnum {
	NO("0")
	, COURSECODE("1")
	, VIDICODE("2")
	, EXAMDATE("3")
	, EXAMFORMAT("4")
	, EXAMSTATUS("5")
	, ORGANIZATIONPLACE("6");
	private String value;
	
	private ExamScheduleCandidateEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
