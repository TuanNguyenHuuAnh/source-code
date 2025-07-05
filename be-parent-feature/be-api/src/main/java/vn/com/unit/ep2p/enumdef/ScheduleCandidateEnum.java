package vn.com.unit.ep2p.enumdef;

public enum ScheduleCandidateEnum {
	NO("0")
	, AREANAME("1")
	, OFFICENAME("2")
	, COURSECODE("3")
	, COURSENAME("4")
	, ORGANIZATIONDATE("5")
	//, EXAMDATE("7")
	, ngayKetThuc("6")
	, STUDYPLACE("7");//studyPlace
	private String value;
	
	private ScheduleCandidateEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
